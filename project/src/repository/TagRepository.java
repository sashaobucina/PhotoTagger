package repository;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.FileInfo;

/**
 * Representation of tag repository. Tags are read from configuration file
 * tag.properties
 *
 */
public class TagRepository implements Serializable {

	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default file for serializing and deserializing files mapped out to
	 * previous names
	 */
	private String nameMapConfigFile = "name_history.ser";

	/** Default file for reading and saving tags */
	private String tagConfigFile = "tags.txt";

	/** A list of all available tags created */
	private List<String> tags = new ArrayList<String>();

	/**
	 * All the files that have been renamed, mapped out to their previous names
	 */
	private Map<FileInfo, List<String>> nameMap = new HashMap<>();

	/**
	 * Loads a new tag repository
	 */
	public TagRepository() {
		// load tags from configuration file
		load();
	}

	/**
	 * Loads a new tag repository from the file whose name is fileName
	 * 
	 * @param fileName
	 *            default file to add/remove tags
	 */
	public TagRepository(String fileName) {

		// override default file
		this.tagConfigFile = fileName;

		// load tags from file and load map of previous names from serialized
		// file
		load();
	}

	/**
	 * Get tag list
	 *
	 * @return
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * Get map of previous names for image files
	 * 
	 * @return the name map
	 */
	public Map<FileInfo, List<String>> getNameMap() {
		return nameMap;
	}

	/**
	 * Sets the map of previous names to a new map
	 * 
	 * @param newNameMap
	 *            new map of previous names
	 */
	public void setNameMap(Map<FileInfo, List<String>> newNameMap) {
		this.nameMap = newNameMap;
	}

	/**
	 * Changes the map of previous names for an image whenever that image file
	 * name is modified
	 * 
	 * @param newFile
	 *            the new file representation of this image
	 * @param oldFile
	 *            the old representation of this image
	 */
	public void changeNameMap(FileInfo newFile, FileInfo prevFile) {

		// replace old image representation with new image representation, if
		// map already contains the old image
		if (nameMap.containsKey(prevFile)) {
			// add old image name to list of previous names if not already
			// present
			if (!nameMap.get(prevFile).contains(prevFile.getName())) {
				nameMap.get(prevFile).add(prevFile.getName());
			}
			nameMap.put(newFile, nameMap.remove(prevFile));
			// create new key for this representation of an image that has an
			// empty list of previous names
		} else {
			ArrayList<String> previousNameList = new ArrayList<>();
			previousNameList.add(prevFile.getName());
			nameMap.put(newFile, previousNameList);
		}
	}

	/**
	 * Save tags to configuration file
	 * 
	 * @throws IOException
	 */
	public void saveAllToFile() throws IOException {

		FileWriter writer = new FileWriter(tagConfigFile);
		for (String str : tags) {
			writer.write(str + System.lineSeparator());
		}
		writer.close();

		// creating new file to store serialized map of names
		FileWriter nameWriter = new FileWriter(nameMapConfigFile);
		nameWriter.close();

		// serializing the map of previous names for an image to file
		File f = new File(nameMapConfigFile);
		OutputStream fileOut = new FileOutputStream(f);
		OutputStream buffer = new BufferedOutputStream(fileOut);
		ObjectOutput output = new ObjectOutputStream(buffer);
		output.writeObject(nameMap);
		output.close();

	}

	/**
	 * Add new tag. If this tag already exist it will not be inserted
	 *
	 * @param tag
	 */
	public int add(String tag) {
		// tags cannot be duplicated
		if (!tags.contains(tag)) {
			tags.add(tag);

			// sort tags alphabetically
			tags.sort(new TagComparator());

			return tags.indexOf(tag);
		} else {
			// not inserted index
			return -1;
		}

	}

	/**
	 * Delete tag by name
	 *
	 * @param tag
	 *            tag to be deleted
	 */
	public void delete(String tag) {
		tags.remove(tag);
	}

	/**
	 * Delete tag at specified index
	 * 
	 * @param index
	 *            index at which tag is be deleted
	 */
	public void deleteAtIndex(int index) {
		tags.remove(index);
	}

	/**
	 * Remove all tags
	 */
	public void clear() {
		tags.clear();

	}

	/**
	 * Tags are sorted alphabetically using tag comparator
	 *
	 */
	protected class TagComparator implements Comparator<String> {
		public int compare(String obj1, String obj2) {
			if (obj1 == obj2) {
				return 0;
			}
			if (obj1 == null) {
				return -1;
			}
			if (obj2 == null) {
				return 1;
			}
			return obj1.compareTo(obj2);
		}
	}

	/**
	 * Load tags from configuration file
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private void load() {

		// Checking if there is a tag config file, creating one if not
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(tagConfigFile));

		} catch (FileNotFoundException e) {
			try {
				FileWriter writer = new FileWriter(tagConfigFile);
				writer.close();
				br = new BufferedReader(new FileReader(tagConfigFile));
			} catch (Exception ex) {
				// TODO:
				ex.printStackTrace();
			}
		}

		// Writing each tag from config file to a tag list
		try {

			String line = br.readLine();
			while (line != null) {
				tags.add(line);
				line = br.readLine();
			}

		} catch (IOException e) {
			// TODO:
			e.printStackTrace();
		}

		// creates new file from default serialization file, if file exists
		File f = new File(nameMapConfigFile);

		// write out map of previous names for images from the serialized file
		if (f.exists()) {
			try {
				InputStream file = new FileInputStream(f);
				InputStream buffer = new BufferedInputStream(file);
				ObjectInput input = new ObjectInputStream(buffer);

				nameMap = (Map<FileInfo, List<String>>) input.readObject();
				input.close();

			} catch (IOException i) {
				i.printStackTrace();

			} catch (ClassNotFoundException c) {
				System.out.println("HaspMap class not found");
				c.printStackTrace();
			}
			// write new empty file for serialization to take place
		} else {
			try {
				FileWriter nameWriter = new FileWriter(nameMapConfigFile);
				nameWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {

		// Start with empty file
		TagRepository repo1 = new TagRepository("test.txt");

		if (repo1.getTags().size() > 0) {
			System.out.println("test.txt should be empty!");
		}

		// test add tags and save
		repo1.add("Tag1");
		repo1.add("Tag2");

		if (repo1.getTags().size() != 2) {
			System.out.println("Insert failed!");
		}

		repo1.saveAllToFile();

		// Create new repo from the same file
		TagRepository repo2 = new TagRepository("test.txt");

		// repositories are equal at this point
		if (!repo2.getTags().equals(repo1.getTags())) {
			System.out.println("Tag repos are not the same!");
		}

		// test delete
		repo2.delete("Tag2");

		if (repo2.getTags().size() != 1) {
			System.out.println("Delete failed!");
		}

		// test correct item deleted
		List<String> outcome = new ArrayList<String>();
		outcome.add("Tag1");
		if (!repo2.getTags().equals(outcome)) {
			System.out.println("Delete tag by name failed!");
		}

		// test delete at index functionality
		repo2.deleteAtIndex(0);
		if (repo2.getTags().size() != 0) {
			System.out.println("Delete at index failed!");
		}

		// restore original state (empty file)
		repo2.saveAllToFile();

	}

}