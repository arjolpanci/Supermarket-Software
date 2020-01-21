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
	private String path = "files\\products.bin";
	private File file;
	
	public ProductIO() {
		products = new ArrayList<Product>();
		file = new File(path);
		if(file.exists()) {
			read();
		}else {
			write();
		}
	}
	
	public ArrayList<Product> getProducts(){
		return products;
	}
	
	public void addProduct(Product product) {
		for(Product p : products) {
			if(product.equals(p)) {
				p.setQuantity(p.getQuantity()+1);
				Alert al = new Alert(AlertType.INFORMATION, "That product was already on stock, "
						+ "only quantity has been added", ButtonType.OK);
				al.show();
				write();
			}else {
				products.add(product);
				write();
			}
		}
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
