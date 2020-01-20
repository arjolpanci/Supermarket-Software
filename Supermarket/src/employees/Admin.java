package employees;

import java.io.Serializable;

import interfaces.IChecker;
import util.SimpleDate;

public class Admin extends User implements IChecker, Serializable{

	public Admin(String name, String surname, String username, String password, SimpleDate birthday) {
		super(name, surname, username, password, birthday);
		this.setUsertype("Administrator");
		this.setSalary(0);
	}

	@Override
	public boolean check(String user, String pw) {
		return (user.equals(this.getUsername()) && pw.equals(this.getPassword())) ? true : false;
	}

}
