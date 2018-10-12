package events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

import model.FileInfo;
import repository.TagRepository;
import ui.NameHistoryViewer;

/**
 * The listener for the button view name history from an image in the image list
 */
//OBSERVER DESIGN PATTERN:
//This is an example of an adapted observer design patter (listener is specific to GUI classes).
public class ViewNameHistoryActionListener implements ActionListener {

	/** List of images */
	private JList<FileInfo> imageList;

	/** Repository of tags */
	private TagRepository tagRepo;

	/**
	 * Constructing an action listener for the viewing name history for an image in imageList
	 *
	 * @param imageList
	 * 			list of images
	 * @param tagRepo
	 * 			repository of tags
	 */
	public ViewNameHistoryActionListener(JList<FileInfo> imageList, TagRepository tagRepo) {
		this.imageList = imageList;
		this.tagRepo = tagRepo;
	}

	/**
	 * Handle the user clicking on view name history for an image file
	 * 
	 * @param e 
	 * 			the event object
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// get selected image from model (will be refreshed automatically)
		int selectedImageIndex = imageList.getSelectedIndex();
		
		// create a new frame to view history of names of an image, if an image is selected
		if (selectedImageIndex != -1) {
			
			FileInfo fileInfo = (FileInfo) imageList.getModel().getElementAt(selectedImageIndex);
			
			new NameHistoryViewer(fileInfo, tagRepo).setVisible(true);
		}

	}

}
