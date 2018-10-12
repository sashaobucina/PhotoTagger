package model;

import java.io.Serializable;

/**
 * Representation of the file
 * (Note: Used to work with strings in our programming instead of with files)
 */
public class FileInfo implements Serializable {

	/** Default serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** the name of the file */
	private String name;

	/** directory that this file resides in */
	private String parent;

	public FileInfo(String fileName, String directory) {
		this.name = fileName;
		this.parent = directory;
	}

	/**
	 * Get name of this FileInfo
	 * 
	 * @return
	 * 			name of FileInfo
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name of this FileInfo
	 * 
	 * @param
	 * 			name of file
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get parent directory of this FileInfo
	 * 
	 * @param
	 * 			directory of file
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * Set parent directory of this FileInfo
	 * 
	 * @param
	 * 			directory of file
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	/**
	 * Return the string representation of a FileInfo, which is its file name
	 * 
	 * @return
	 * 		a string representation of FIleInfo
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * Gets the absolute path of this FileInfo
	 * 
	 * @return
	 * 			absolute path of this FileInfo
	 */
	public String getAbsolutePath() {
		return parent + name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}

	/**
	 * Indicates whether one FileInfo is equal to this one
	 * 
	 * @return
	 * 			whether two FileInfo's are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileInfo other = (FileInfo) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}

}