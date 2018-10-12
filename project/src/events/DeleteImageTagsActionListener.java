package events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JList;

import logging.EventLogger;
import model.FileInfo;
import repository.TagRepository;
import service.TagFileManager;
/**
 * The listener for the button delete multiple tags from a tag list to image in the image list 
 */
//OBSERVER DESIGN PATTERN:
//This is an example of an adapted observer design patter (listener is specific to GUI classes).
public class DeleteImageTagsActionListener implements ActionListener {

	/** List with labels/tags */
	private JList<String> tagList;
	
	/** List of images */
	private JList<FileInfo> imageList;
	
	/** Repository of tags */
	private TagRepository tagRepo;

	/**
	 * An action listener for the delete image tags button, taking a tag from tagList and adding it to an image
	 * in imageList 
	 *
	 * @param tagList
	 * 			list with tags
	 * @param tagManager
	 * 			repository of tags to be modified
	 */
	public DeleteImageTagsActionListener(JList<String> tagList,  JList<FileInfo> imageList, TagRepository tapRepo) {
		this.tagList = tagList;
		this.imageList = imageList;
		this.tagRepo = tapRepo;
		
	}

	/**
	 * Handle the user clicking on the clear multiple tags image button.
	 *
	 * @param e 
	 * 			the event object
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// get selected tag
		List<String> selectedTags = tagList.getSelectedValuesList();
		
		// get selected image from model (will be refreshed automatically)
		int selectedImageIndex = imageList.getSelectedIndex();
		
		// execute block if an image is selected
		if (selectedImageIndex != -1) {
		
	        FileInfo fileInfo = (FileInfo) imageList.getModel().getElementAt(selectedImageIndex);
	        
	        // rename the file (add tag to file name)
	        FileInfo newFileInfo = TagFileManager.getInstance().clearSingleTag(fileInfo, selectedTags);
	        System.out.println("Old name: " + fileInfo.getName()  + " , new name: " + newFileInfo.getName());
	        // change mapping of previous names 
	        
	        // log event and change the mapping of previous names for this image
	        if (! fileInfo.getName().equals(newFileInfo.getName())) {
	        	EventLogger.log("File renamed from " + fileInfo.getName() + " to " + newFileInfo.getName());
		        tagRepo.changeNameMap(newFileInfo, fileInfo);
	        }
	        
	        // updated image list model
	        fileInfo.setName(newFileInfo.getName()); 
	        
	        imageList.updateUI();
        
		}
        
	}


}