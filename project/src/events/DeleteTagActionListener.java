package events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import repository.TagRepository;
/**
 * The listener for the button to delete selected tag from tag list. 
 */
//OBSERVER DESIGN PATTERN:
//This is an example of an adapted observer design patter (listener is specific to GUI classes).
public class DeleteTagActionListener implements ActionListener {

	/** List with labels/tags */
	private JList<String> tagList;
	
	/** Repository of tags */
	private TagRepository tagRepository;

	/**
	 * An action listener for button that deletes tags from tagList
	 *
	 * @param tagList
	 * 			list of tags
	 * @param tagManager
	 * 			repository of tags
	 */
	public DeleteTagActionListener(JList<String> tagList, TagRepository tagRepository) {
		this.tagList = tagList;
		this.tagRepository = tagRepository;
	}

	/**
	 * Handle the user clicking on the delete tag button.
	 *
	 * @param e the event object
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// get selected tags
		int[] selectedIndices = tagList.getSelectedIndices();
		
		DefaultListModel<String> model = (DefaultListModel<String>) tagList.getModel();
		
		// remove tag from tag repository and tag list if tag is selected
		if (selectedIndices.length >  0) {
	          for (int i = selectedIndices.length-1; i >=0; i--) {
	        	  model.removeElementAt(selectedIndices[i]);
	        	  tagRepository.deleteAtIndex(selectedIndices[i]);
			}
		}
	
		// refresh GUI
		tagList.updateUI();
	}


}