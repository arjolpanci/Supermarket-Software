package data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

import products.Product;
import util.SimpleDate;

public class Bill implements Serializable{
	
	private ArrayList<Product> products = new ArrayList<Product>();
	private SimpleDate dateCreated;
	private double total;
	
	public Bill(ArrayList<Product> products, double total) {
		this.products = products;
		LocalDateTime now = LocalDateTime.now();
		dateCreated = new SimpleDate(now);
		this.total = total;
	}
	
	public void addProduct(Product product) {
		products.add(product);
	}

	public ArrayList<Product> getProducts() { return products; }
	
	public SimpleDate getDateCreated() { return dateCreated; }

	public double getTotal() { return total; }
	public void setTotal(double total) { this.total = total; }
	
}
