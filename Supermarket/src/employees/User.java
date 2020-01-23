package employees;

import java.io.Serializable;

import util.SimpleDate;

public abstract class User implements Serializable{
	
	private int id;
	private String usertype;
	private String name;
	private String surname;
	private String username;
	private String password;
	private SimpleDate birthday;
	private int salary;
	
	public User(String name, String surname, String username, String password, SimpleDate birthday) {
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
		this.birthday = birthday;
	} 
	
	
	public String getUsertype() { return usertype; }
	public void setUsertype(String usertype) { this.usertype = usertype; }

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getSurname() { return surname; }
	public void setSurname(String surname) { this.surname = surname; }
	
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	
	public SimpleDate getBirthday() { return birthday; }
	public void setBirthday(SimpleDate birthday) { this.birthday = birthday; }

	public int getSalary() { return salary; }
	public void setSalary(int salary) { this.salary = salary; }

}
