package data;

import java.io.Serializable;
import java.time.LocalDate;

import employees.User;
import products.Product;
import util.SimpleDate;

public class FinancialAction implements Serializable{
	
	private User actionUser;
	private Product actionProduct;
	private float amount;
	private SimpleDate date;
	
	public FinancialAction(Product actionProduct ,float amount) {
		this.actionUser = null;
		this.actionProduct = actionProduct;
		this.amount = amount;
		date = new SimpleDate(LocalDate.now());
	}
	
	public FinancialAction(User actionUser ,float amount) {
		this.actionProduct = null;
		this.actionUser = actionUser;
		this.amount = amount;
		date = new SimpleDate(LocalDate.now());
	}
	
	public User getActionUser() { return actionUser; }
	public void setActionUser(User actionUser) { this.actionUser = actionUser; }
	
	public Product getActionProduct() {	return actionProduct; }
	public void setActionProduct(Product actionProduct) { this.actionProduct = actionProduct; }

	public float getAmount() { return amount; }
	public void setAmount(float amount) { this.amount = amount; }
	
	public SimpleDate getDate() { return date; }
	public void setDate(SimpleDate date) { this.date = date; }
	
}
