package employees;

import java.io.Serializable;

import interfaces.IChecker;
import util.FlatButton;
import util.SimpleDate;

public class Cashier extends User implements IChecker, Serializable{
	
	public Cashier(String name, String surname, String username, String password, SimpleDate birthday, int salary) {
		super(name, surname, username, password, birthday);
		this.setUsertype("Cashier");
		this.setSalary(salary);
	}

	@Override
	public boolean check(String user, String pw) {
		return (user.equals(this.getUsername()) && pw.equals(this.getPassword())) ? true : false;
	}
	
}
