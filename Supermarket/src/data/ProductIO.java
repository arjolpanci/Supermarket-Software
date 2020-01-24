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

public class ProductIO {
	
	private ArrayList<Product> products;
	private String path = "files" + File.separator + "products.bin";
	private File file;
	
	public ProductIO() {
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
	
	public Product getProductFromName(String name) {
		for(Product p : products) {
			if(p.getName().equals(name)) return p;
		}
		return null;
	}
	
	public void addProduct(Product product) {
		boolean flag = true;
		for(Product p : products) {
			if(product.equals(p) || product.getName().equals(p.getName()) || product.getSupplier().equals(p.getSupplier())) {
				products.remove(p);
				p.setQuantity(product.getQuantity());
				p.setPrice(product.getPrice());
				Alert al = new Alert(AlertType.INFORMATION, "That product was already on stock, "
						+ "only quantity\\price has been changed", ButtonType.OK);
				al.show();
				products.add(p);
				write();
				flag = false;
			}
		}
		if(flag) {
			products.add(product);
			write();
		}
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
