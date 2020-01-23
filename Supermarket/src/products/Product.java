package products;

import java.io.Serializable;

import data.ProductIO;
import util.NotEnoughQuantityException;

public class Product implements Serializable{
	
	private String name;
	private String supplier;
	private int quantity;
	private float price;
	private int barcode;
	
	public Product(String name, String supplier, int quantity, float price, int barcode) {
		this.name = name;
		this.supplier = supplier;
		this.quantity = quantity;
		this.price = price;
		this.barcode = barcode;
	}
	
	public void updateQuantity(int qty, ProductIO pio) throws NotEnoughQuantityException{
		if(qty > quantity || qty < 0) throw new NotEnoughQuantityException(qty);
		pio.removeProduct(this);
		quantity -= qty;
		pio.addProduct(this);
		pio.update();
	}
	
	public float getPriceForQuantity(int qty) {
		return qty * price;
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getSupplier() { return supplier; }
	public void setSupplier(String supplier) { this.supplier = supplier; }

	public int getQuantity() { return quantity; }
	public void setQuantity(int quantity) { this.quantity = quantity; }

	public float getPrice() { return price; }
	public void setPrice(float price) { this.price = price; }

	public int getBarcode() { return barcode; }
	public void setBarcode(int barcode) { this.barcode = barcode; }
	
}
