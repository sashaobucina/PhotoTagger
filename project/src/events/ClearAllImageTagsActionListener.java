package events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

import logging.EventLogger;
import model.FileInfo;
import repository.TagRepository;
import service.TagFileManager;
/**
 * The listener for the button to remove all tags from image
 */
//OBSERVER DESIGN PATTERN:
//This is an example of an adapted observer design patter (listener is specific to GUI classes).
public class ClearAllImageTagsActionListener implements ActionListener {

	
	/** List of images */
	private JList<FileInfo> imageList;
	
	/** Repository of tags */
	private TagRepository tagRepo;

	/**
	 * An action listener for the clear image tags button, choosing the image from imageList
	 *
	 * @param imageList
	 * 			list of images
	 */
	public ClearAllImageTagsActionListener( JList<FileInfo> imageList, TagRepository tagRepo) {
		this.imageList = imageList;
		this.tagRepo = tagRepo;
		
	}

	/**
	 * Handle the user clicking on remove image tags button
	 *
	 * @param e the event object
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// get selected image from model (will be refreshed automatically)
		int selectedImageIndex = imageList.getSelectedIndex();
		
		// executes block if an image is selected
		if (selectedImageIndex != -1) {
		
	        FileInfo fileInfo = (FileInfo) imageList.getModel().getElementAt(selectedImageIndex);
	        
	        // rename the file (add tag to file name)
	        FileInfo newFileInfo = TagFileManager.getInstance().clearAllTags(fileInfo);
	        
	        // log event and change mapping of previous names
	        if (! fileInfo.getName().equals(newFileInfo.getName())) {
	        	EventLogger.log("File renamed from " + fileInfo.getName() + " to " + newFileInfo.getName());
	        	tagRepo.changeNameMap(newFileInfo, fileInfo);
	        }
	        
	        // updated image list model
	        fileInfo.setName(newFileInfo.getName());      
	        
	        // refresh list display
	        imageList.updateUI();
		}
	}


}
