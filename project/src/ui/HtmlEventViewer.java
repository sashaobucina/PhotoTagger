package ui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * Frame where we view the events that were logged
 */
public class HtmlEventViewer extends JFrame {

	/** Default serialVersionUID */
	private static final long serialVersionUID = 7980135598581493049L;

	/**
	 * Construct an interface to view events that were logged
	 * 
	 * @param events
	 * 			string representation of events logged
	 */
	public HtmlEventViewer(String events) {
		
		super("Event Viewer");
		
		JEditorPane ed = new JEditorPane("text/html", events);
		
		ed.setEditable(false);
		
		// Put it in a scroll pane in case the output is long
		JScrollPane scrollPane = new JScrollPane(ed);
		
		// Put it all together.
		Container c = getContentPane();
		c.add(scrollPane, BorderLayout.CENTER);
		
		setSize(650,700);
	}

}