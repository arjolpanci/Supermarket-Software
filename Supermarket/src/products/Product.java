package products;

import java.io.Serializable;

public class Product implements Serializable{
	
	private String name;
	private String supplier;
	private int quantity;
	private int price;
	private int barcode;
	
	public Product(String name, String supplier, int quantity, int price, int barcode) {
		this.name = name;
		this.supplier = supplier;
		this.quantity = quantity;
		this.price = price;
		this.barcode = barcode;
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getSupplier() { return supplier; }
	public void setSupplier(String supplier) { this.supplier = supplier; }

	public int getQuantity() { return quantity; }
	public void setQuantity(int quantity) { this.quantity = quantity; }

	public int getPrice() { return price; }
	public void setPrice(int price) { this.price = price; }

	public int getBarcode() { return barcode; }
	public void setBarcode(int barcode) { this.barcode = barcode; }
	
}
