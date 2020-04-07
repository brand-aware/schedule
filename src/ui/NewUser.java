/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
package ui;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import config.ConfigNewUser;
import core.Properties;
import core.Utilities;


public class NewUser extends ConfigNewUser{
	
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = -3282120556623998740L;
	
	private Utilities utilities;
	private Properties properties;
	
	private JTextField username;
	private JButton create, close;
	
	private boolean initialized = false;
	
	public NewUser(Utilities utils) {
		super("create new user");
		utilities = utils;
		properties = utilities.getProperties();
	}
	
	private void createPage(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ButtonHandler handler = new ButtonHandler();
		ImageIcon icon = new ImageIcon(properties.getCompanySmall());
		setFrameIcon(icon);
		
		JLabel title = new JLabel(DIRECTIONS);
		
		JLabel usernameLabel = new JLabel(USERNAME_LABEL);
		username = new JTextField();
		username.setPreferredSize(new Dimension(USERNAME_FIELD_WIDTH, USERNAME_FIELD_HEIGHT));
		
		create = new JButton(CREATE_LABEL);
		create.setPreferredSize(new Dimension(CREATE_BUTTON_WIDTH, CREATE_BUTTON_HEIGHT));
		create.addActionListener(handler);
		
		close = new JButton(CLOSE_LABEL);
		close.setPreferredSize(new Dimension(CLOSE_BUTTON_WIDTH, CLOSE_BUTTON_HEIGHT));
		close.addActionListener(handler);
		
		JPanel panel1, panel2, panel3;
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		
		panel1.add(Box.createGlue());
		panel1.add(title);
		
		panel2.add(Box.createGlue());
		panel2.add(usernameLabel);
		panel2.add(username);
		
		panel3.add(Box.createGlue());
		panel3.add(create);
		panel3.add(close);
		
		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalGlue());
		box.add(panel1);
		box.add(panel2);
		box.add(panel3);
		
		add(box);
		pack();
		setVisible(true);
	}
	
	private class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == create){
				String name = username.getText();
				try {
					if(isUnique(name)){
						save(name);
						JOptionPane.showMessageDialog(null, name + SUCCESS_MESSAGE);
						dispose();					
					}else{
						JOptionPane.showMessageDialog(null, TRY_AGAIN_MESSAGE);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == close){
				dispose();
			}
		}
	}
	
	private final boolean isUnique(String name) throws IOException{
		String path = properties.getUsers();
		BufferedReader reader = new BufferedReader(new FileReader(path));
		while(reader.ready()){
			String line = reader.readLine();
			if(line.compareTo(name) == 0){
				reader.close();
				return false;
			}
		}
		reader.close();
		
		return true;
	}
	
	private final void save(String name) throws IOException{
		String path = properties.getUsers();
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String buffer = "";
		while(reader.ready()){
			String line = reader.readLine();
			buffer += line + "\n";
		}
		reader.close();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		writer.write(buffer);
		writer.write(name);
		writer.close();
		
		properties.setUsername(name);
		path = properties.getRootDir();
		File dir = new File(path + name);
		dir.mkdir();
		
		path = properties.getSchedules();
		File file = new File(path);
		file.createNewFile();
	}
	
	public final void init(){
		if(!initialized){
			createPage();
			initialized = true;
		}
	}
}
