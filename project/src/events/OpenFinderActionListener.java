package events;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JList;

import model.FileInfo;

/**
 * The listener for an open in finder button.
 */
// OBSERVER DESIGN PATTERN
public class OpenFinderActionListener implements ActionListener {

	/** List of image files */
	private JList<FileInfo> imageList;

	/**
	 * Constructor for the action listener that finds file in user's file system
	 * 
	 * @param imageList
	 *            image list to be used
	 */
	public OpenFinderActionListener(JList<FileInfo> imageList) {
		this.imageList = imageList;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int indexSelected = imageList.getSelectedIndex();

		if (indexSelected != -1) {
			FileInfo fileInfo = (FileInfo) imageList.getModel().getElementAt(indexSelected);

			File file = new File(fileInfo.getParent());

			try {
				Desktop.getDesktop().open(file);
			} catch (IOException e1) {
				System.out.println("IOException for ActionListener when opening file in the Finder ");
				e1.printStackTrace();
			}

		}

	}

}
