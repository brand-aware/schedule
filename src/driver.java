import java.io.IOException;
import java.util.Calendar;

import core.Properties;
import core.Utilities;
import ui.ProgressScreen;
/**
 * 
 * @author mike802
 * 
 * ??? - 2017
 *
 */
public class driver {

	/**
	 * java driver <project dir>
	 */
	public static void main(String[] args) {
		Properties properties = new Properties(args[0]);
		Utilities util = new Utilities(properties);
		
		Calendar calendar = Calendar.getInstance();
		
		System.out.println("Year: " + calendar.get(Calendar.YEAR));
		System.out.println("Month: " + calendar.get(Calendar.MONTH));
		System.out.println("Day: " + calendar.get(Calendar.DATE));
		System.out.println("Day of the week: " + calendar.get(Calendar.DAY_OF_WEEK));
		System.out.print("Time: " + calendar.get(Calendar.HOUR));
		System.out.println(":" + calendar.get(Calendar.MINUTE));
		
		try {
			properties.setBackupTag("" + calendar.get(Calendar.YEAR) + 
					calendar.get(Calendar.MONTH) + 
					calendar.get(Calendar.DATE) +
					calendar.get(Calendar.HOUR) +
					calendar.get(Calendar.MINUTE));
			util.setProperties(properties);
			ProgressScreen progressScreen = new ProgressScreen(util);
			progressScreen.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}