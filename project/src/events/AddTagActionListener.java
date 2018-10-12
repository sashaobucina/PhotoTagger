package events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextField;

import repository.TagRepository;

/**
 * The listener for the button to add new tag to tag list. 
 */
// OBSERVER DESIGN PATTERN:
// This is an example of an adapted observer design patter (listener is specific to GUI classes).
public class AddTagActionListener implements ActionListener {

	/** list with labels/tags */
	private JList<String> tagList = null;
	
	/** Tag to add */
	private JTextField text = null;
	
	/** Tag repository to modify */
	TagRepository tagRepository = null;
	
	/**
	 * An action listener for the button to add a tag to the tagList
	 * 
	 * @param tagList 
	 * 			list with tags
	 * @param text 
	 * 			tag to be added 
	 * @param tagRepository 
	 * 			tag repository to be modified 
	 */
	public AddTagActionListener(JList<String> tagList, JTextField text, TagRepository tagRepository) {
		this.tagList = tagList;
		this.text = text;
		this.tagRepository = tagRepository;
	}

	
	/**
	 * Handle the user clicking on the add tag button.
	 *
	 * @param e the event object
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// update repository
		int insertedIndex = tagRepository.add(text.getText());
		
		// update list model
		DefaultListModel<String> model = (DefaultListModel<String>)tagList.getModel();
		
		if (insertedIndex >= 0) {
			// tags are sorted (same as tag repository)
			model.insertElementAt(text.getText(),  insertedIndex);
		}
		
		// Select added element for user convenience
		int selected = insertedIndex >= 0? insertedIndex: tagRepository.getTags().indexOf(text.getText());
		tagList.setSelectedIndex(selected);
		
		// refresh GUI
		tagList.updateUI();
		text.setText("");
	}
}