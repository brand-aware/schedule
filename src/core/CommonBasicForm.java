/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
package core;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

import config.ConfigBasicForm;

public class CommonBasicForm extends ConfigBasicForm{
	
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = -6332334346791449803L;
	public CommonBasicForm(String header) {
		super(header);
	}
	protected Properties properties;
	protected Utilities utilities;
	protected JLayeredPane layeredPane;
	
	protected JTextField activity;
	protected JCheckBox sun, mon, tue, wed, thur, fri, sat;
	protected boolean[] week = new boolean[DAYS];
	
	protected JLabel[] weekLabels1 = new JLabel[DAYS];
	protected JLabel[] weekLabels2 = new JLabel[DAYS];
	protected JTextField[] time = new JTextField[DAYS];
	protected JLabel[] timeUnits = new JLabel[DAYS];
	protected ArrayList<JComboBox<String>> freqList = new ArrayList<JComboBox<String>>();
	protected JComboBox<String> frequencies;
	protected String[] frequencyList = FREQUENCY_LABELS;
	protected JButton enter, cancel;
	protected boolean edit = false;
	protected boolean initialized = false;
	
	protected int basicY = 0, currentX = 0, currentY = 0;
	protected int finalX = 0;

}
