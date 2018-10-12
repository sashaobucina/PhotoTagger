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
 * The listener for the button add tag from tag list to image in the image list
 */
//OBSERVER DESIGN PATTERN:
//This is an example of an adapted observer design patter (listener is specific to GUI classes).
public class TagImageActionListener implements ActionListener {

	/** List with labels/tags */
	private JList<String> tagList;

	/** List of images */
	private JList<FileInfo> imageList;

	/** Repository of tags */
	private TagRepository tagRepo;

	/**
	 * Constructs an action listener for the button that tags an image in the imageList, with tags in tagList
	 *
	 * @param tagList
	 * 			list of tags
	 * @param tagRepo
	 * 			repository of tags
	 */
	public TagImageActionListener(JList<String> tagList, JList<FileInfo> imageList, TagRepository tagRepo) {
		this.tagList = tagList;
		this.imageList = imageList;
		this.tagRepo = tagRepo;

	}

	/**
	 * Handle the user clicking on the tag image button.
	 *
	 * @param e
	 *            the event object
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// get selected tag
		List<String> selectedTags = tagList.getSelectedValuesList();

		// get selected image from model (will be refreshed automatically)
		int selectedImageIndex = imageList.getSelectedIndex();

		if (selectedImageIndex != -1) {

			FileInfo fileInfo = (FileInfo) imageList.getModel().getElementAt(selectedImageIndex);

			// rename the file (add tag to file name)
			FileInfo newFileInfo = TagFileManager.getInstance().addMultipleTags(fileInfo, selectedTags);
			System.out.println("Old name: " + fileInfo.getName()  + " , new name: " + newFileInfo.getName());
			

			// log event only if there is selected tag to tag image with and change the mapping of previous names for that image
			if (!fileInfo.getName().equals(newFileInfo.getName())) {
				EventLogger.log("File renamed from " + fileInfo.getName() + " to " + newFileInfo.getName());
				tagRepo.changeNameMap(newFileInfo, fileInfo);
			}

			// updated image list model
			fileInfo.setName(newFileInfo.getName());

			imageList.updateUI();

		}

	}

}
