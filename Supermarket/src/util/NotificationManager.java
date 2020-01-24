package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class NotificationManager {
	
	private ArrayList<Notification> notifications;
	private String path = "files" + File.separator + "notifications.bin";
	private File file;
	
	public NotificationManager() {
		notifications = new ArrayList<Notification>();
		file = new File(path);
		if(file.exists()) {
			read();
		}else {
			write();
		}
	}
	
	public void update() {
		write();
	}
	
	public boolean exits(Notification n) {
		for(Notification not : notifications) {
			if(not.getSender().equals(n.getSender()) && not.getReciever().equals(n.getReciever()) 
					&& not.getMessage().equals(n.getMessage())) return true;
		}
		return false;
	}
	
	public ArrayList<Notification> getNotifications(){
		return notifications;
	}
	
	public void removeNotification(Notification not) {
		notifications.remove(not);
		write();
	}
	
	public void addNotification(Notification not) {
		notifications.add(not);
		write();
	}

	private void write() {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(notifications);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void read() {
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			notifications = (ArrayList<Notification>) ois.readObject();
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
	
}
