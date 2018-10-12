package events;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import model.FileInfo;
import repository.TagRepository;
import ui.ImageFilter;
import ui.ImageListSelection;
import util.FileNodeUtil;
import directory_explorer.FileNode;
import directory_explorer.FileType;

/**
 * The listener for the button to choose a directory. This is where most of the
 * work is done.
 */
//OBSERVER DESIGN PATTERN:
//This is an example of an adapted observer design patter (listener is specific to GUI classes).
public class FileChooserButtonListener implements ActionListener {

	/** The window the button is in. */
	private JFrame directoryFrame;
	/** The label for the full path to the chosen directory. */
	private JLabel directoryLabel;
	/** The file chooser to use when the user clicks. */
	private JFileChooser fileChooser;
	/** The area to use to display the nested directory contents. */
	/** List with labels/tags */
	private TagRepository tagManager;

	/**
	 * An action listener for window dirFrame, displaying a file path on
	 * dirLabel, using fileChooser to choose a file.
	 *
	 * @param dirFrame
	 *            the main window
	 * @param dirLabel
	 *            the label for the directory path
	 * @param fileChooser
	 *  			the file chooser to use
	 * @param tagManager
	 */
	public FileChooserButtonListener(JFrame dirFrame, JLabel dirLabel, JTextArea textArea, JFileChooser fileChooser,
			TagRepository tagManager) {
		this.directoryFrame = dirFrame;
		this.directoryLabel = dirLabel;
		this.fileChooser = fileChooser;
		this.tagManager = tagManager;
	}

	/**
	 * Handle the user clicking on the open button.
	 *
	 * @param e
	 *            the event object
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		Container container = directoryFrame.getContentPane();

		int returnVal = fileChooser.showOpenDialog(container);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if (file.exists()) {

				// Make the root.
				FileNode fileTree = new FileNode(file.getName(), null, FileType.DIRECTORY);
				FileChooserButtonListener.buildTree(file, fileTree, new ImageFilter());

				List<FileInfo> images = FileNodeUtil.getFileList(file.getParentFile().getAbsolutePath(), fileTree);

				// adding all the files to keep track of previous names
				for (FileInfo fileInfo : images) {
					Map<FileInfo, List<String>> nameMap = tagManager.getNameMap();
					if (!nameMap.containsKey(fileInfo)) {
						nameMap.put(fileInfo, new ArrayList<String>());
					}
					tagManager.setNameMap(nameMap);
				}

				// Create and set up the content pane.
				ImageListSelection imageListSelection = new ImageListSelection(images, tagManager,
						file.getAbsolutePath());
				imageListSelection.setOpaque(true);
				directoryFrame.setContentPane(imageListSelection);

				// Display the window.
				directoryFrame.pack();
				directoryFrame.setVisible(true);

			}
		} else {
			directoryLabel.setText("No Path Selected");
		}
	}

	/**
	 * Build the tree of nodes rooted at file in the file system; note curr is
	 * the FileNode corresponding to file, so this only adds nodes for children
	 * of file to the tree. Precondition: file represents a directory.
	 * 
	 * @param file
	 *            the file or directory we are building
	 * @param curr
	 *            the node representing file
	 * @param filter
	 *            FileFilter filtering files in the directory
	 */
	private static void buildTree(File file, FileNode curr, FileFilter filter) {

		// List directory contents
		File[] dirContents = file.listFiles(filter);

		if (dirContents != null) {

			for (int i = 0; i < dirContents.length; i++) {

				String name = dirContents[i].getName();
				FileType type = dirContents[i].isDirectory() ? FileType.DIRECTORY : FileType.FILE;

				FileNode child = new FileNode(name, curr, type);

				if (child.isDirectory()) {
					buildTree(dirContents[i], child, filter);
				}

				curr.addChild(name, child);
			}
		}
	}
}
