package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import model.FileInfo;


// SINGLETON DESIGN PATTERN:
// The class TagFileManager uses the singleton design pattern in order to only have one instance of the class.
// We don't need more than one instance of TagFileManager and have a global point of access from our getInstance method
// We use this one instance to call our public methods that change image files by add/deleting tags or renaming the file, 
// which are used in all our action listeners for buttons that work with changing the names of image files.
/**
 * Manager of all back-end operations when renaming image files
 */
public class TagFileManager {
	
	//
	private static TagFileManager instance;
	
	// Prevent instantiation
	private TagFileManager() {
	}
	
	// get single instance of this type
	public  static TagFileManager getInstance() {
		if (instance == null) {
			instance = new TagFileManager();
		}
		
		return instance;
	}
	
	public FileInfo addTagToFile(FileInfo fileInfo, String tag){
		

		String fileName = fileInfo.getName();
		
        int i = fileName.lastIndexOf('.');
        
        String fullTag = '@' + tag;
        
		if (fileName.contains(fullTag)){				
			return fileInfo;			
		}
		
        
        String newFileName = fileName.substring(0, i) + "@" + tag + fileName.substring(i, fileName.length());
        
        File file = new File(fileInfo.getAbsolutePath());
        
        Path source = file.toPath();
        try {
             Files.move(source, source.resolveSibling(newFileName));
        } catch (IOException e) {
             e.printStackTrace();
        }
  
		FileInfo newfile = new FileInfo(newFileName, fileInfo.getParent());

		return newfile;
		
		
		
		
	}
	
	public FileInfo addMultipleTags(FileInfo fileInfo, List<String> selectedTags){
		
		String fileName = fileInfo.getName();
		
		StringBuffer joinTags = new StringBuffer();
		
		for (String tag: selectedTags) {
			
			String fullTag = '@' + tag;
			
			if (!fileName.contains(fullTag)){				
				joinTags.append(fullTag);				
			}
			
		}
		
		if (joinTags.length() == 0) {
			// nothing to do, all tags have already been applied
			return fileInfo;
		}
		else {
			return addTagToFile(fileInfo, joinTags.substring(1));
		}
		
	}
	
	public FileInfo clearAllTags(FileInfo fileInfo) {
		
		String name = fileInfo.getName();
		String newFileName = name;
		if (name.contains("@")){
			int i = name.indexOf('@');
	        int j = name.lastIndexOf('.');
			newFileName = name.substring(0, i) + name.substring(j, name.length());	
		}
		
        File file = new File(fileInfo.getAbsolutePath());
		
        Path source = file.toPath();
        try {
             Files.move(source, source.resolveSibling(newFileName));
        } catch (IOException e) {
             e.printStackTrace();
        }
		FileInfo newfile = new FileInfo(newFileName, fileInfo.getParent());
		
		return newfile;
	}
	
	public FileInfo clearSingleTag(FileInfo fileInfo, List<String> tags) {
		
		
		String name = fileInfo.getName();
		String newFileName = name;

		for (String tag: tags) {
		String fullTag =  '@' + tag;
		if (name.contains(fullTag)){
			newFileName = newFileName.replaceAll(fullTag, "");
		}
	}
		
        File file = new File(fileInfo.getAbsolutePath());
		
        Path source = file.toPath();
        try {
             Files.move(source, source.resolveSibling(newFileName));
        } catch (IOException e) {
             e.printStackTrace();
        }
        
		FileInfo newfile = new FileInfo(newFileName, fileInfo.getParent());
		
		return newfile;
	}
	
	public FileInfo renameImageFile(FileInfo fileInfo, String newFileName) {
		
		File file = new File(fileInfo.getAbsolutePath());
		
		Path source = file.toPath();
		try {
			Files.move(source, source.resolveSibling(newFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FileInfo newFile = new FileInfo(newFileName, fileInfo.getParent());
		
		return newFile;
	}
	
	
	
	public static void main(String[] args) {
		
		TagFileManager.getInstance().addTagToFile(new FileInfo("Desert.jpg", "C:\\Temp\\"), "Stef");
	}


}
