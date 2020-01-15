package employees;

import java.io.Serializable;

import interfaces.IChecker;
import util.FlatButton;
import util.SimpleDate;

public class Cashier extends User implements IChecker, Serializable{
	
	public Cashier(String name, String surname, String username, String password, SimpleDate birthday) {
		super(name, surname, username, password, birthday);
		this.setUsertype("Cashier");
	}

	@Override
	public boolean check(String user, String pw) {
		
		return false;
	}
	
}
