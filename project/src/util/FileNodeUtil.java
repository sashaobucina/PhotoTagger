package util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import model.FileInfo;
import directory_explorer.FileNode;

/**
 * Utility class for working with file node
 *
 */
public class FileNodeUtil {
	
	/**
	 * Figure out path (directory) where file is located
	 * 
	 * @param selectedFolder
	 * 			folder selected
	 * @param fileNode
	 * 			the file node
	 * @return
	 */
	public static String getAbsolutePath(String selectedFolder, FileNode fileNode) {
		String relativePath = "";
	
		FileNode parent = fileNode.getParent();
		while(parent!=null) {
			relativePath = parent.getName() + File.separatorChar + relativePath;
			parent = parent.getParent();
		}
		
		return selectedFolder + File.separatorChar + relativePath;
	}

	/**
	 * Get all files (not directories) in fileNode subtree
	 * 
	 * @param selectedFolder
	 * 			folder selected
	 * @param fileNode
	 * 			file tree to get file list from
	 * @return
	 * 			list of all representations of files in the file tree
	 */
	public static List<FileInfo> getFileList(String selectedFolder, FileNode fileNode) {
		
		List<FileInfo> files = new ArrayList<FileInfo>();
		
		for (FileNode file : fileNode.getChildren()){
			if (file.isDirectory()){
				List<FileInfo> dirFiles = getFileList(selectedFolder, file);
				files.addAll(dirFiles);
			}
				
			else{
				FileInfo child = new FileInfo(file.getName(), getAbsolutePath(selectedFolder, file));
			
				files.add(child);				
			}				
		}
		
		return files;
			
		
	}
	
}
