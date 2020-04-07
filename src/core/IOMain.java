/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class IOMain extends CommonMain{
	
	/*public final void addUserToUserList(String name){
		System.out.println("ADDING USERNAME TO LIST");
		String[] newUserList = new String[userList.length + 1];
		for(int x = 0; x < userList.length; x++){
			//usernames.removeItem(userList[x]);
			newUserList[x] = userList[x];
			//usernames.addItem(userList[x]);
		}
		usernames.addItem(name);
		newUserList[userList.length] = name;
		usernames.setSelectedIndex(userList.length);
		userList = newUserList;
	}*/

	protected final void importAllUserData() throws Exception{
		int choice = fileChooser.showOpenDialog(null);
		if(choice == JFileChooser.APPROVE_OPTION){
			File importData = fileChooser.getSelectedFile();
			if(importData.isFile()){
				String path = importData.getAbsolutePath();
				if(path.contains(properties.getImportExtention())){
					confirmImportFileLoad(path);
				}
			}
		}
	}
	
	protected final void confirmImportFileLoad(String path) throws Exception{
		int choice = JOptionPane.showOptionDialog(null, 
				"Click \"ok\" to import all user data for user: " +
				path.substring(path.lastIndexOf(File.separator), 
						path.indexOf(properties.getImportExtention())), 
				"import all user data", 
				JOptionPane.OK_CANCEL_OPTION, 
				JOptionPane.PLAIN_MESSAGE, 
				new ImageIcon(properties.getCompany()), 
				null, null);
		if(choice == 0){
			loadAllUserData(path);
		}
	}
	
	protected final void loadAllUserData(String path) throws Exception{
		BufferedReader reader = new BufferedReader(new FileReader(path));
		BufferedWriter writer = null;
		File folder;
		File file;
		String username = path.substring(path.lastIndexOf(File.separator) + 1,
				path.lastIndexOf("_"));
		properties.setUsername(username);
		folder = new File(properties.getRootDir() + username);
		if(!folder.exists()){
			System.out.println("Making folder: " + properties.getRootDir() + 
					username);
			folder.mkdir();
		}
		
		while(reader.ready()){
			String line = reader.readLine();
			if(line.compareTo("##########") == 0){
				writer.close();
				if(reader.ready()){
					line = reader.readLine();
					line = line.replaceAll("<", "");
					line = line.replaceAll(">", "");
					if(line.compareTo("progress") == 0){
						progress = true;
						progressBackup = false;
						schedules = false;
						etc = false;
						folder = new File(properties.getRootDir() + 
								properties.getUsername() + File.separator +
								"progress");
						System.out.println("Making folder: " + properties.getRootDir() + 
								properties.getUsername() + File.separator +
								"progress");
						if(!folder.exists()){
							folder.mkdir();
						}
								
					}else if(line.compareTo("progress_backup") == 0){
						progress = false;
						progressBackup = true;
						schedules = false;
						etc = false;
						folder = new File(properties.getRootDir() +
								properties.getUsername() + File.separator +
								"progress_backup");
						if(!folder.exists()){
							folder.mkdir();
						}
					}else if(line.compareTo("schedules") == 0){
						progress = false;
						progressBackup = false;
						schedules = true;
						etc = false;
						folder = new File(properties.getRootDir() +
								properties.getUsername() + File.separator +
								"schedules");
						if(!folder.exists()){
							folder.mkdir();
						}
					}else if(line.compareTo("etc") == 0){
						progress = false;
						progressBackup = false;
						schedules = false;
						etc = true;
					}else{
						reader.close();
						throw new Exception("Invalid data format");
					}
				}
			}else if(line.compareTo("@@@@@@@@@@") == 0){
				if(reader.ready()){
					line = reader.readLine();
					line = line.replaceAll("<", "");
					line = line.replaceAll(">", "");
					String folderPath = folder.getAbsolutePath();
					String filePath = folderPath + File.separator + line;
					file = new File(filePath);
					if(!file.exists()){
						file.createNewFile();
					}
					writer = new BufferedWriter(new FileWriter(file));
				}
			}else{
				if(progress){
					if(writer != null){
						writer.write(line);
					}
				}else if(progressBackup){
					if(writer != null){
						writer.write(line);
					}
				}else if(schedules){
					if(writer != null){
						writer.write(line);
					}
				}else if(etc){
					if(writer != null){
						writer.write(line);
					}
				}else{
					reader.close();
					throw new Exception("Import error:  Please contact support");
				}
			}
		}
		reader.close();
		writer.close();
	}
	
	protected String loadUserList(String purpose) throws IOException{
		String path = properties.getUsers();
		BufferedReader reader = new BufferedReader(new FileReader(path));
		ArrayList<String> list = new ArrayList<String>();
		while(reader.ready()){
			String line = reader.readLine();
			list.add(line);
		}
		reader.close();
		
		int size = list.size();
		userList = new String[size];
		for(int x = 0; x < size; x++){
			String user = list.get(x);
			userList[x] = user;
		}
		if(userList.length < 1){
			userList = new String[] {"<empty>"};
		}
		
		Object choice = JOptionPane.showInputDialog(null, 
				purpose, 
				"select username", 
				JOptionPane.QUESTION_MESSAGE, 
				new ImageIcon(properties.getCompany()), 
				userList, 0);
		if(choice != null){
			return (String) choice;
		}
		return null;
	}
	
	protected final String[] loadScheduleList() throws IOException{
		String path = properties.getSchedules();
		BufferedReader reader = new BufferedReader(new FileReader(path));
		ArrayList<String> list = new ArrayList<String>();
		while(reader.ready()){
			String line = reader.readLine();
			list.add(line);
		}
		reader.close();
		
		String[] scheduleList = new String[list.size()];
		for(int x = 0; x < list.size(); x++){
			scheduleList[x] = list.get(x);
		}
		return scheduleList;
	}
	
	protected final void deleteAllUserData() throws IOException{
		String name = loadUserList("delete user data");
		File rootDir = new File(properties.getRootDir() + name);
		System.out.println(rootDir.getAbsolutePath());
		if(rootDir.exists()){
			File[] files = rootDir.listFiles();
			for(int x = 0; x < files.length; x++){
				if(files[x].isDirectory()){
					removeDirectory(files[x]);
				}else{
					files[x].delete();
				}
			}
		}
		if(rootDir.exists()){
			File[] files = rootDir.listFiles();
			for(int x = 0; x < files.length; x++){
				if(files[x].exists()){
					files[x].delete();
				}
			}
		}
		rootDir.delete();
		
		BufferedReader reader = new BufferedReader(new FileReader(properties.getUsers()));
		System.out.println(properties.getUsers());
		int count = 0;
		String buffer = "";
		while(reader.ready()){
			String line = reader.readLine();
			if(line.compareTo(name) != 0){
				System.out.println(line);
				if(count != 0){
					buffer += "\n" + line;
				}else{
					buffer += line;
					count++;
				}
			}
		}
		reader.close();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(properties.getUsers()));
		writer.write(buffer);
		writer.close();
		
		desktopPane.moveToBack(panel1);
		JLabel defaultChart = new JLabel();
		ImageIcon chartPic = new ImageIcon(properties.getSampleChart());
		defaultChart.setIcon(chartPic);
		panel1.add(defaultChart);
		desktopPane.moveToFront(panel1);
	}
	
	protected final void removeDirectory(File folder){
		File[] files = folder.listFiles();
		for(int x = 0; x < files.length; x++){
			if(files[x].isDirectory()){
				removeDirectory(files[x]);
			}else{
				files[x].delete();
			}
		}
	}
	
	protected final void backupAllData() throws IOException{
		String name = loadUserList("create user backup");
		properties.setUsername(name);
		String path = properties.getRootDir() + properties.getUsername();
		String buffer = "";
		File dir = new File(path);
		System.out.println(dir.getAbsolutePath());
		File[] rootDir = dir.listFiles();
		for(int x = 0; x < rootDir.length; x++){
			if(rootDir[x].isDirectory()){
				String folder = rootDir[x].getAbsolutePath();
				if(folder.contains("progress_backup")){
					buffer += "##########\n";
					buffer += "<progress_backup>\n";
					buffer += readProgress(folder);
				}else if(folder.contains("schedules")){
					buffer += "##########\n";
					buffer += "<schedules>\n";
					buffer += readSchedules(folder);
				}else{
					buffer += "##########\n";
					buffer += "<progress>\n";
					buffer += readProgress(folder);
				}
			}else{
				buffer += "##########\n";
				buffer += "<etc>\n";
				String filePath = rootDir[x].getAbsolutePath();
				String title = filePath.substring(filePath.lastIndexOf(File.separator), filePath.length());
				if(title.contains("activities")){
					buffer += "@@@@@@@@@@\n";
					buffer += "<activities.txt>\n";
					buffer += readActivities(filePath);
				}else if(title.contains("previous")){
					buffer += "@@@@@@@@@@\n";
					buffer += "<previous.txt>\n";
					buffer += readPrevious(filePath);
					buffer += "\n";
				}
			}
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(properties.getBackupPath()));
		writer.write(buffer);
		writer.close();
	}
	
	protected final String readProgress(String filePath) throws IOException{
		BufferedReader reader;
		String buffer = "";
		File folder = new File(filePath);
		File[] progressFiles = folder.listFiles();
		
		for(int x = 0; x < progressFiles.length; x++){
			String path = progressFiles[x].getAbsolutePath();
			reader = new BufferedReader(new FileReader(path));
			buffer += "@@@@@@@@@@\n";
			buffer += "<" + path.substring(
					path.lastIndexOf(File.separator) + 1, 
					path.length()) + ">\n";
			while(reader.ready()){
				String line = reader.readLine();
				if(line.compareTo(" ") != 0 && line.compareTo("\n") != 0 &&
						line.compareTo("") != 0){
					buffer += line + "\n";
				}
			}
			reader.close();
		}
		
		return buffer;
	}
	
	protected final String readSchedules(String filePath) throws IOException{
		BufferedReader reader;
		File folder = new File(filePath);
		File[] scheduleFiles = folder.listFiles();
		String buffer = "";
		buffer += "@@@@@@@@@@\n";
		for(int x = 0; x < scheduleFiles.length; x++){
			String path = scheduleFiles[x].getAbsolutePath();
			buffer += "<" + path.substring(
					path.lastIndexOf(File.separator) + 1, 
					path.length()) + ">\n";
			reader = new BufferedReader(new FileReader(path));
			while(reader.ready()){
				String line = reader.readLine();
				if(line.compareTo(" ") != 0 && line.compareTo("\n") != 0 &&
						line.compareTo("") != 0){
					buffer += line;
				}
			}		
			
			reader.close();
			if((x + 1) < scheduleFiles.length){
				buffer += "\n@@@@@@@@@@\n";
			}
		}
		return buffer;
	}
	
	protected final String readActivities(String filePath) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String buffer = "";
		while(reader.ready()){
			String line = reader.readLine();
			buffer += line + "\n";
		}
		
		reader.close();
		return buffer;
	}
	
	protected final String readPrevious(String filePath) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String buffer = "";
		while(reader.ready()){
			String line = reader.readLine();
			buffer += line;
		}
		
		reader.close();
		return buffer;
	}
	
}
