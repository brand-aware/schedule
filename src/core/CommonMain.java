/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
package core;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import config.ConfigProgressScreen;
import ui.ChartBar;
import ui.NewUser;
import ui.ProgressScreen;
import ui.ProgressTextSummary;

public class CommonMain extends ConfigProgressScreen{
	
	protected JFrame progressPage = null;
	protected JDesktopPane desktopPane = null;

	protected Properties properties;
	protected Utilities utilities;
	protected NewUser newUser;
	protected ProgressChecker progressChecker;
	protected ProgressScreen progressScreen;
	protected ChartFilters chartFilters;
	protected ChartBar chartBar;
	protected ProgressCalculator progressCalculator;
	protected ProgressTextSummary progressTextSummary;	
	
	protected JPanel panel1;
	protected JFileChooser fileChooser;
	protected JMenuBar menuBar;
	protected JMenu file, tools, options, view, help;
	protected JMenuItem login, register, exit,
		inputData, manualInput, editEntry, deleteSelected, editSchedules, createNewSchedule,
		deleteUser, importData, backup, reset,
		textOnly, filters,
		about,faq;
	protected JCheckBoxMenuItem quickStart;
	protected String[] userList;
	
	protected boolean created = false;
	
	protected boolean progress = false;
	protected boolean progressBackup = false;
	protected boolean schedules = false;
	protected boolean etc = false;
	protected boolean initialized = false;
	
}