package logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * An HTML formatter for logging events
 */
class HtmlFormatter extends Formatter {

	/**
	 * Format the given log record into an HTML format and return it as a string
	 * 
	 * @param record
	 * 			log record to format
	 */
	@Override
	public String format(LogRecord record) {
    	DateFormat dateFormatter = new SimpleDateFormat("yyyy HH:mm:ss");
    	String formattedDate = dateFormatter.format(new Date());
		return ("<tr><td>" + formattedDate + "</td><td>" + record.getMessage()
				+ "</td></tr>\n");
	}


}
