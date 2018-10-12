package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import logging.EventLogger;
import model.FileInfo;
import repository.TagRepository;
import service.TagFileManager;

/**
 * Viewer for the history of names that an image file had.
 */
public class NameHistoryViewer extends JFrame implements ActionListener {

	/** Default serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** Representation of image file */
	private FileInfo fileInfo;

	/** List of all the previous file names */
	private JList<String> nameList;

	/** Repository of tags */
	private TagRepository tagRepo;

	/**
	 * Constructor for the frame that views previous names of an image file
	 * 
	 * @param fileInfo
	 *            this representation of image file
	 * @param tagRepo
	 *            repository of tags
	 */
	public NameHistoryViewer(FileInfo fileInfo, TagRepository tagRepo) {

		super("Name History");

		this.fileInfo = fileInfo;
		this.tagRepo = tagRepo;

		setLayout(new BorderLayout());

		final DefaultListModel<String> model = new DefaultListModel<String>();

		Map<FileInfo, List<String>> nameMap = tagRepo.getNameMap();

		for (String prevName : nameMap.get(this.fileInfo)) {
			model.addElement(prevName);
		}

		JList<String> nameList = new JList<String>(model);

		// select first image in the list
		if (!nameMap.get(fileInfo).isEmpty()) {
			nameList.setSelectedIndex(0);
		}

		this.nameList = nameList;

		// add scroll bar to the list
		JScrollPane listPane = new JScrollPane(nameList);

		// add border around the list
		listPane.setBorder(BorderFactory.createTitledBorder("Name History of: " + fileInfo.getName()));

		// add button for renaming image files and action listener for this
		// event
		JButton renameButton = new JButton("Rename");
		renameButton.addActionListener(this);

		// Put it all together.
		Container c = getContentPane();
		c.add(listPane, BorderLayout.CENTER);
		c.add(renameButton, BorderLayout.SOUTH);

		setSize(500, 500);

	}

	/**
	 * Handler for when rename button is clicked
	 * 
	 * @param e
	 *            the event object
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// get selected image from model (will be refreshed automatically)
		int selectedImageIndex = nameList.getSelectedIndex();

		if (selectedImageIndex != -1) {
			String newFileName = (String) nameList.getModel().getElementAt(selectedImageIndex);
			FileInfo newFileInfo = TagFileManager.getInstance().renameImageFile(fileInfo, newFileName);

			// log event only if there is selected tag to tag image with and
			// then change the map of previous names
			if (!fileInfo.getName().equals(newFileInfo.getName())) {
				EventLogger.log("File renamed from " + fileInfo.getName() + " to " + newFileInfo.getName());
				tagRepo.changeNameMap(newFileInfo, fileInfo);

				// Hide the window whenever renaming in order to refresh UI next
				// time image is selected
				dispose();
			}

			// updated image list model
			fileInfo.setName(newFileInfo.getName());

			nameList.updateUI();
		}

	}

}
