package products;

import java.io.Serializable;
import java.time.LocalDate;

import data.ProductIO;
import util.NotEnoughQuantityException;
import util.SimpleDate;

public class Product implements Serializable, Cloneable{
	
	private String name;
	private String supplier;
	private int quantity;
	private float buyingprice;
	private float price;
	private long barcode;
	private LocalDate expireDate;
	
	public Product(String name, String supplier, int quantity, float buyingprice, float price, long barcode, SimpleDate expire) {
		this.name = name;
		this.supplier = supplier;
		this.quantity = quantity;
		this.buyingprice = buyingprice;
		this.price = price;
		this.barcode = barcode;
		this.expireDate = expire.toLocalDate();
	}
	
	public void updateQuantity(int qty, ProductIO pio) throws NotEnoughQuantityException{
		if(qty > quantity || qty <= 0) throw new NotEnoughQuantityException(qty);
		pio.removeProduct(this);
		quantity -= qty;
		pio.addProduct(this, false);
		pio.update();
	}
	
	public float getPriceForQuantity(int qty) {
		return qty * price;
	}
	
	public boolean hasExpired() {
		if(this.getExpireDate().isBefore(LocalDate.now())) return true;
		if(this.getExpireDate().isEqual(LocalDate.now())) return true;
		return false;
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getSupplier() { return supplier; }
	public void setSupplier(String supplier) { this.supplier = supplier; }

	public int getQuantity() { return quantity; }
	public void setQuantity(int quantity) { this.quantity = quantity; }
	
	public float getBuyingprice() {	return buyingprice;	}
	public void setBuyingprice(float buyingprice) {	this.buyingprice = buyingprice;	}

	public float getPrice() { return price; }
	public void setPrice(float price) { this.price = price; }

	public long getBarcode() { return barcode; }
	public void setBarcode(long barcode) { this.barcode = barcode; }
	
	public LocalDate getExpireDate() { return expireDate; }
	public void setExpireDate(SimpleDate expireDate) { this.expireDate = expireDate.toLocalDate(); }

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
}
