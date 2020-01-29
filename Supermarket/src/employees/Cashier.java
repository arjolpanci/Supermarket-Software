package employees;

import java.io.Serializable;
import java.util.ArrayList;

import data.Bill;
import interfaces.IChecker;
import util.SimpleDate;

public class Cashier extends User implements IChecker, Serializable{
	
	private ArrayList<Bill> bills;
	
	public Cashier(String name, String surname, String username, String password, SimpleDate birthday, int salary) {
		super(name, surname, username, password, birthday);
		this.setUsertype("Cashier");
		this.setSalary(salary);
		bills = new ArrayList<Bill>();
	}
	
	public void addBill(Bill b) {
		bills.add(b);
	}
	
	public ArrayList<Bill> getBills() {	return bills; }
	public void setBills(ArrayList<Bill> bills) { this.bills = bills; }

	@Override
	public boolean check(String user, String pw) {
		return (user.equals(this.getUsername()) && pw.equals(this.getPassword())) ? true : false;
	}
	
}
