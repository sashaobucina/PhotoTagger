package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import events.WindowEventListener;
import logging.EventLogger;
import repository.TagRepository;
import events.FileChooserButtonListener;

/**
 * Create and show a directory explorer, which displays the images of a
 * directory, allowing the user to then rename images using a GUI.
 */
public class PhotoRenamer {

	/**
	 * Create and return the window for the photo renamer.
	 *
	 * @return the window for the directory explorer
	 */
	public static JFrame buildWindow() {
		JFrame directoryFrame = new JFrame("PhotoRenamer");
		directoryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		JLabel directoryLabel = new JLabel("Select a directory to rename photos and tag images");

		// Set up the area for the directory contents.
		JTextArea textArea = new JTextArea(15, 50);
		textArea.setEditable(false);

		// Put it in a scroll pane in case the output is long.
		JScrollPane scrollPane = new JScrollPane(textArea);

		// The directory choosing button.
		JButton openButton = new JButton("Choose Directory");
		openButton.setVerticalTextPosition(AbstractButton.CENTER);
		openButton.setHorizontalTextPosition(AbstractButton.LEADING); // aka
																		// LEFT,
																		// for
																		// left-to-right
																		// locales
		openButton.setMnemonic(KeyEvent.VK_D);
		openButton.setActionCommand("disable");

		// Load tag repository
		TagRepository tagRepository = new TagRepository();
		
		// Initialize logger
		EventLogger.init();
		
		// The listener for openButton.
		ActionListener buttonListener = new FileChooserButtonListener(directoryFrame, directoryLabel, textArea,
				fileChooser, tagRepository);
		openButton.addActionListener(buttonListener);

		// Put it all together.
		Container c = directoryFrame.getContentPane();
		c.add(directoryLabel, BorderLayout.PAGE_START);
		c.add(scrollPane, BorderLayout.CENTER);
		c.add(openButton, BorderLayout.PAGE_END);
		
		// add window listener for closing the frame
		directoryFrame.addWindowListener(new WindowEventListener(directoryFrame, tagRepository));

		directoryFrame.pack();
		return directoryFrame;
	}

	/**
	 * Create and show a directory explorer, which displays the contents of a
	 * directory.
	 *
	 * @param args
	 *            the command-line arguments.
	 */
	public static void main(String[] args) {
		PhotoRenamer.buildWindow().setVisible(true);
	}

}
