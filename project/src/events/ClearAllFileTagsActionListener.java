package events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;
import javax.swing.ListModel;

import logging.EventLogger;
import model.FileInfo;
import repository.TagRepository;
import service.TagFileManager;

public class ClearAllFileTagsActionListener implements ActionListener {

	private JList<FileInfo> imageList;

	private TagRepository tagRepo;

	public ClearAllFileTagsActionListener(JList<FileInfo> imageList, TagRepository tagRepo) {
		this.imageList = imageList;
		this.tagRepo = tagRepo;

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		ListModel<FileInfo> fileList = imageList.getModel();

		// iterate through all image files in directory, clearing all tags
		for (int i = 0; i < fileList.getSize(); i++) {
			FileInfo fileInfo = fileList.getElementAt(i);

			FileInfo newFileInfo = TagFileManager.getInstance().clearAllTags(fileInfo);

			// log event and change mapping of previous names
			if (!fileInfo.getName().equals(newFileInfo.getName())) {
				EventLogger.log("File renamed from " + fileInfo.getName() + " to " + newFileInfo.getName());
				tagRepo.changeNameMap(newFileInfo, fileInfo);
			}

			// updated image list model
			fileInfo.setName(newFileInfo.getName());

		}
		
		// refresh list display
		imageList.updateUI();

	}

}
