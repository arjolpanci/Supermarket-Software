package employees;

import java.io.Serializable;

import interfaces.IChecker;
import util.SimpleDate;

public class Economist extends User implements IChecker, Serializable {

	public Economist(String name, String surname, String username, String password, SimpleDate birthday, int salary) {
		super(name, surname, username, password, birthday);
		this.setUsertype("Economist");
		this.setSalary(salary);
	}

	@Override
	public boolean check(String user, String pw) {
		return (user.equals(getName()) && pw.equals(getPassword())) ? true : false;
	}

}
