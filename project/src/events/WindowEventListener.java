package events;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFrame;

import logging.EventLogger;
import repository.TagRepository;

/**
 * The listener for closing main window (exiting program)
 */
//OBSERVER DESIGN PATTERN:
//This is an example of an adapted observer design patter (listener is specific to GUI classes).
public class WindowEventListener extends WindowAdapter implements WindowListener {

	/** Frame to invoke this window listener */
	JFrame frame;
	
	/** Repository of tags */
	TagRepository tagRepository;

	/**
	 * Constructing an action listener for the default exit window button 
	 * 
	 * @param frame
	 * 			frame to invoke this window listener
	 * @param tagRepository
	 * 			repository of tags
	 */
	public WindowEventListener(JFrame frame, TagRepository tagRepository) {
		this.frame = frame;
		this.tagRepository = tagRepository;
	}

	/**
	 * Invoked when window is being closed
	 * 
	 * @param e 
	 * 			the event object
	 */
	public void windowClosing(WindowEvent e) {

		// save all tags/map of previous names to respective files
		try {
			tagRepository.saveAllToFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// Release resources (lock on events log file)
		EventLogger.close();
	}


}
