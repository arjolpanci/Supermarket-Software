package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import products.Product;
import util.SimpleDate;

public class ProductIO {
	
	private ArrayList<Product> products;
	private String path = "files" + File.separator + "products.bin";
	private File file;
	private SaleManager sm;
	
	public ProductIO() {
		sm = new SaleManager();
		products = new ArrayList<Product>();
		file = new File(path);
		file.getParentFile().mkdirs();
		if(file.exists()) {
			read();
		}else {
			write();
		}
	}
	
	public ArrayList<Product> getProducts(){
		return products;
	}
	
	public void setProducts(ArrayList<Product> p) {
		products = p;
		write();
	}

	public void reAdd(Product p){
		Product toRemove = null;
		for(Product pr : products){
			if(pr.getName().equals(p.getName())){
				toRemove = pr;
			}
		}
		if(toRemove != null) {
			this.removeProduct(toRemove);
			this.addProduct(p, false);
		}
	}
	
	public Product getProductFromName(String name) {
		for(Product p : products) {
			if(p.getName().equals(name)) return p;
		}
		return null;
	}
	
	public int addSupplier(String name) {
		int flag = 0;
		for(Product p : products) {
			if(p.getSupplier().equals(name)) {
				Alert al = new Alert(AlertType.ERROR, "That supplier already exists", ButtonType.OK);
				al.showAndWait();
				return 0;
			}
		}
		flag = 1;
		if(flag == 1) {
			Product sup = new Product("RESERVED", name, 0, 0, 0, 0, new SimpleDate(1,1,1));
			products.add(sup);
			write();
		}	
		return 1;
	}
	
	public boolean doesSupplierExist(String name) {
		for(Product p : products) {
			if(p.getSupplier().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public void deleteSupplier(String name) {
		ArrayList<Product> toRemove = new ArrayList<Product>();
		for(Product p : products) {
			if(p.getName().equals("RESERVED") && p.getSupplier().equals(name)) toRemove.add(p);
		}
		for(Product p : toRemove) {
			products.remove(p);
		}
		write();
	}
	
	public int addProduct(Product product, boolean isfinancial) {
		boolean flag = true;
		for(Product p : products) {
			if((product.equals(p) || product.getName().equals(p.getName()))) {
				int qntybought = product.getQuantity() - p.getQuantity();
				if (qntybought < 0) isfinancial = false;
				if(isfinancial) {
					Product faProduct = new Product(product.getName(), 
							product.getSupplier(), qntybought, product.getBuyingprice(), 
							product.getPrice(), product.getBarcode(), new SimpleDate(product.getExpireDate()));
					FinancialAction fa = new FinancialAction(faProduct, faProduct.getBuyingprice() * qntybought * -1);
					sm.addFinancialAction(fa);
				}
				if(!product.getSupplier().equals(p.getSupplier())) {
					Alert al = new Alert(AlertType.WARNING, "You are already recieving this product from another supplier,"
							+ " the data of that product has been updated", ButtonType.OK);
					al.showAndWait();
				}
				p.setQuantity(product.getQuantity());
				p.setPrice(product.getPrice());
				reAdd(p);
				write();
				flag = false;
			}
		}
		if(flag) {
			for(Product p : products) {
				if(product.getBarcode() == p.getBarcode()) {
					Alert al = new Alert(AlertType.ERROR, "A product with that barcode already exists", ButtonType.OK);
					al.showAndWait();
					return 0;
				}
			}
			products.add(product);
			if(isfinancial) {
				FinancialAction fa = new FinancialAction(product, product.getBuyingprice() * product.getQuantity() * -1);
				sm.addFinancialAction(fa);	
			}
			write();
			return 1;
		}
		return 1;
	}
	
	public void update() { write(); }
	
	public void removeProduct(Product product) {
		products.remove(product);
	}

	private void write() {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(products);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void read() {
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			products = (ArrayList<Product>) ois.readObject();
			ois.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
