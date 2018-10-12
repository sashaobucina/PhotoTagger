package events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import logging.EventLogger;
import ui.HtmlEventViewer;
/**
 * The listener for viewing events (history)
 */
//OBSERVER DESIGN PATTERN:
//This is an example of an adapted observer design patter (listener is specific to GUI classes).
public class ViewEventsActionListener implements ActionListener {

	/**
	 * Constructing an action listener for viewing events (history)
	 *
	 */
	public ViewEventsActionListener() {
	}

	/**
	 * Handle the user clicking on show history (events).
	 *
	 * @param e 
	 * 			the event object
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		new HtmlEventViewer(EventLogger.getEvents()).setVisible(true);

	}


}