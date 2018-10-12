package ui;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Used to filter image files
 */
public class ImageFilter implements FileFilter {

	/** Array of accepted image extensions */
	private static final String[] images = {"png", "jpeg", "jpg", "gif", "tiff", "tiff", "tif"};
	
	/** Set of accepted image extensions */
	private static final Set<String> imageExtensions = new HashSet<String>(Arrays.asList(images));
	
    /**
     * Accepts image type files with extension from the set of accepted extensions
     * 
     * @param file
     * 			the file being checked
     */
    public boolean accept(File file) {
        
    	if (file.isDirectory()) {
            return true;
        }

        String fileName = file.getName();
        
        // Figure out file extension
        String extension = null;
        int i = fileName.lastIndexOf('.');
        if (i != -1 && i < fileName.length() - 1) {
        	extension = fileName.substring(i+1).toLowerCase();
        }
 
        return extension == null? false: imageExtensions.contains(extension);
    }

}
