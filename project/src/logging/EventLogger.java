package logging;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The logger for events in our PhotoRenamer project
 */
public class EventLogger {

	/** the logger */
	private final static Logger logger = Logger.getLogger(EventLogger.class.getName());
	
	/** the file handler*/
	private static FileHandler fileHandler = null;
	
	/** the formatter for logging events*/
	private static Formatter formatter = null;
	
	/** file name for file that logs events */
	private static final String fileName = "./events.log";

	/**
	 * Initialize the event logger
	 */
	public static void init(){
		try {
			fileHandler = new FileHandler(fileName, 0, 1, true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		formatter = new HtmlFormatter();
		fileHandler.setFormatter(formatter);
		logger.addHandler(fileHandler);
		logger.setLevel(Level.INFO);
	}
	
	/**
	 * Closes the file handler
	 */
	public static void close() {
		fileHandler.close();
	}
	
	/**
	 *  Logs an event
	 *  
	 * @param event
	 * 			the event that occurs
	 */
	public static void log(String event) {
		logger.log(Level.INFO, event);	
	}
	

	/**
	 * Gets all the events from file that holds the log
	 * 
	 * @return 
	 * 			the events recorded as a string
	 */
	public static String getEvents() {
		
		StringBuilder sb = new StringBuilder();
		
		// append table header
		sb.append("<html>\n  <body>\n" + "<Table border>\n<tr><th>Event Timetamp</th><th>Event Message</th></tr>\n");

		FileInputStream fs = null;
		BufferedReader br = null;
		try {
			
			fs = new FileInputStream(fileName);
			br = new BufferedReader(new InputStreamReader(fs));
			
			// read line by line
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				sb.append(strLine);
			}

		} catch (Exception e) {
			// return an empty string if file not found
			e.printStackTrace();
		}
		finally {
			if (br != null) {
				try {
					br.close();
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
			
		// close html tags
		sb.append("</table>\n</body>\n</html>");
		
		return sb.toString();

	}

	public static void main(String[] args) {
		
		EventLogger.init();

		for (int i = 0; i <100 ; i++) {
			logger.log(Level.INFO, "File some@tag.txt has been renamed to some@tag@sandra.txt");
		}

	}
}