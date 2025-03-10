package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import employees.Admin;
import employees.User;
import interfaces.IChecker;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class UserIO {
	
	private ArrayList<User> users;
	private String path = "files" + File.separator + "users.bin";
	private File file;
	private boolean isFirstTime = true;
	
	public UserIO() {
		users = new ArrayList<User>();
		file = new File(path);
		file.getParentFile().mkdirs(); 
		if(file.exists()) {
			isFirstTime = false;
			read();
		}
	}
	
	public boolean isFirstTime() { return isFirstTime; }

	public ArrayList<User> getUsers() {
		return users;
	}
	
	private int getId() {
		int id = 0;
		if(users.size() != 0) {
			id = users.get(users.size()-1).getId();
		}
		return ++id;
	}
	
	public int getAdminsCount() {
		int cnt = 0;
		for(User u : users) {
			if(u instanceof Admin) cnt++;
		}
		return cnt;
	}
	
	public boolean addUser(User user) {
		for(User u : users) {
			if(user.equals(u)) {
				Alert al = new Alert(AlertType.ERROR, "The user with the given information already exists", ButtonType.OK);
				al.show();
				return false;
			}else if(user.getUsername().equals(u.getUsername())) {
				Alert al = new Alert(AlertType.ERROR, "The user with the given username already exists", ButtonType.OK);
				al.show();
				return false;
			}
		}
		user.setId(getId());
		users.add(user);
		write();
		return true;
	}
	
	public void removeUser(User user) {
		users.remove(user);
		write();
	}
	
	private void read() {
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			users = (ArrayList<User>) ois.readObject();
			ois.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void write() {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(users);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		write();
		isFirstTime = false;
	}
	
	public User checkUser(String user, String pw) {
		for(User u : users) {
			if( ((IChecker) u).check(user, pw) ) return u;
		}
		return null;
	}
}
