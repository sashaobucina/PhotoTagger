package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import events.AddTagActionListener;
import events.ClearAllFileTagsActionListener;
import events.ClearAllImageTagsActionListener;
import events.DeleteImageTagsActionListener;
import events.DeleteTagActionListener;
import events.OpenFinderActionListener;
import events.TagImageActionListener;
import events.ViewEventsActionListener;
import events.ViewNameHistoryActionListener;
import model.FileInfo;
import repository.TagRepository;
 
/**
 * Create a panel where you can work with tagging images, view images in a directory, 
 * add available tags, view events, and view previous names of images
 */
public class ImageListSelection extends JPanel {
    
    /** Default serialVersionUID */
	private static final long serialVersionUID = 3075066422802970988L;
	
	/**
	 * Constructs the main interface where you work with tagging images.
	 * 
	 * @param fileList
	 * 			list of files
	 * @param tagManager
	 * 			repository of tags
	 * @param selectedFolder
	 * 			folder selected
	 */
	public ImageListSelection(List<FileInfo> fileList, TagRepository tagManager, String selectedFolder) {
       
		
    	super(new BorderLayout());
    	
    	super.setPreferredSize(new Dimension(1300, 900));
    	
    	final DefaultListModel<FileInfo> model = new DefaultListModel<FileInfo>();
    	
    	for (FileInfo file : fileList) {
        	model.addElement(file);
    	}

        // create JList of all representations of image files
    	JList<FileInfo> list = new JList<FileInfo>(model);
        
        // select first image in the list
        if (!fileList.isEmpty()) {
        	list.setSelectedIndex(0);
        }
        
        // add scroll bar to the list
        JScrollPane listPane = new JScrollPane(list);
 
        // create split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        add(splitPane, BorderLayout.CENTER);
 
        // image list will be located at the top
        JPanel topHalf = new JPanel();
        topHalf.setLayout(new BoxLayout(topHalf, BoxLayout.LINE_AXIS));
        JPanel listContainer = new JPanel(new GridLayout(1,1));
        listContainer.setBorder(BorderFactory.createTitledBorder("Images in Folder: " + selectedFolder));
        listContainer.add(listPane);
         
        topHalf.setBorder(BorderFactory.createEmptyBorder(5,5,0,5));
        topHalf.add(listContainer);

        topHalf.setMinimumSize(new Dimension(100, 50));
        topHalf.setPreferredSize(new Dimension(200, 220));
        splitPane.add(topHalf);
 
        // image preview and tags will be at the bottom
        JPanel bottomHalf = new JPanel(new BorderLayout());
        
        ImagePreview preview = new ImagePreview(list);
        preview.setPreferredSize(new Dimension(300, 300));
        
        JPanel controlPane = new JPanel();
        
        
    	final DefaultListModel<String> tagModel = new DefaultListModel<String>();
    	
    	for (String tag: tagManager.getTags()) {
    		tagModel.addElement(tag);
    	}

        JList<String> tagList = new JList<String>(tagModel);
        
        // add scroll bar to the tag list
        JScrollPane tagListPane = new JScrollPane(tagList);

        JPanel tagListContainer = new JPanel(new GridLayout(1,1));
        tagListContainer.setBorder(BorderFactory.createTitledBorder("Available Tags"));
        tagListContainer.add(tagListPane);
        tagListContainer.setPreferredSize(new Dimension(600, 150));
      
        bottomHalf.add(controlPane, BorderLayout.PAGE_START);
        bottomHalf.add(preview, BorderLayout.CENTER);
    
        JTextField text = new JTextField();
        text.setPreferredSize(new Dimension(100,28));
        
		// Add new tag button
		JButton addTagButton = new JButton("Add");
		
		// Add listener for delete action
		ActionListener addTagListener = new AddTagActionListener(tagList, text, tagManager);
		addTagButton.addActionListener(addTagListener);		
        
		// Delete (tag) button
		JButton deleteTagButton = new JButton("Delete");
        
		// Add listener for delete action
		ActionListener deleteTagListener = new DeleteTagActionListener(tagList, tagManager);
		deleteTagButton.addActionListener(deleteTagListener);
		
		// Tag image button
		JButton tagImageButton = new JButton("Add Tags");
        
		ActionListener tagImageListener = new TagImageActionListener(tagList, list, tagManager);
		tagImageButton.addActionListener(tagImageListener);
		
		// Clear all image tags button
		JButton clearImageTagsButton = new JButton("Clear All Tags");
        
		// Add listener for clearing all image tags
		ActionListener clearAllImageTagsListener = new ClearAllImageTagsActionListener(list, tagManager);
		clearImageTagsButton.addActionListener(clearAllImageTagsListener);

		// Delete multiple image tags button
		JButton clearMultipleTagsButton = new JButton("Delete Tags");
		
		// Add listener for deleting multiple image tags
		ActionListener deleteImageTagsListener = new DeleteImageTagsActionListener(tagList, list, tagManager);
		clearMultipleTagsButton.addActionListener(deleteImageTagsListener);
		
		// Clear all tags from every image file button
		JButton clearAllFileTags = new JButton("Clear All");
		
		// Add listener for clear all file tags button
		ActionListener clearAllFileTagsListener = new ClearAllFileTagsActionListener(list, tagManager);
		clearAllFileTags.addActionListener(clearAllFileTagsListener);
		
		// View events button and adding listener for view events
		JButton viewEventsButton = new JButton("View Events");
		ActionListener viewEventsListener = new ViewEventsActionListener();
		viewEventsButton.addActionListener(viewEventsListener);
		
		// View image name history button
        JButton viewImageNameHistoryButton = new JButton("View Name History");
        
        // Add listener for view name history of an image
        ActionListener viewNameHistoryListener = new ViewNameHistoryActionListener(list, tagManager);
        viewImageNameHistoryButton.addActionListener(viewNameHistoryListener);
        
        // Open in finder button
        JButton openInFinderButton = new JButton("Open in Finder");
        
        // Add listener for the open in finder button
        ActionListener openInFinderListener = new OpenFinderActionListener(list);
        openInFinderButton.addActionListener(openInFinderListener);
        
		// Create the "buttons" panel
        JPanel buttons = new JPanel();
        
        // adding panel for managing tag buttons
        JPanel manageTags = new JPanel();
        manageTags.setBorder(BorderFactory.createTitledBorder("Manage Tags"));
        manageTags.add(text);
        manageTags.add(addTagButton);
        manageTags.add(deleteTagButton);
        
        // adding panel for managing image buttons
        JPanel manageImage = new JPanel();
        manageImage.setBorder(BorderFactory.createTitledBorder("Manage Images"));
        manageImage.add(tagImageButton);
        manageImage.add(clearMultipleTagsButton);
        manageImage.add(clearImageTagsButton);
        manageImage.add(clearAllFileTags);
        
        // adding panel for showing history of file names and log event buttons
        JPanel history = new JPanel();
        history.setBorder(BorderFactory.createTitledBorder("History"));
        history.add(viewEventsButton);
        history.add(viewImageNameHistoryButton);
        
        // adding panel for button that opens image in file system 
        JPanel finder = new JPanel();
        finder.setBorder(BorderFactory.createTitledBorder("View in Finder"));
        finder.add(openInFinderButton);
        
        // add panels for managing tags, images, naming history, and opening images in Finder
        buttons.add(manageTags);
        buttons.add(manageImage);
        buttons.add(history);
        buttons.add(finder);
        
        
        JPanel tagListWithButtons = new JPanel(new BorderLayout());
        tagListWithButtons.add(tagListContainer, BorderLayout.PAGE_START);
        tagListWithButtons.add(buttons, BorderLayout.PAGE_END);
        
        bottomHalf.add(tagListWithButtons, BorderLayout.PAGE_END);

        bottomHalf.setPreferredSize(new Dimension(450, 355));
        splitPane.add(bottomHalf);
    }
}


