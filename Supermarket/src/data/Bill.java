package data;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import employees.Cashier;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import products.Product;
import util.SimpleDate;

public class Bill implements Serializable{
	
	private ArrayList<Product> products = new ArrayList<Product>();
	private SimpleDate dateCreated;
	private double total;
	private Cashier owner;
	private int id = 1;
	
	public Bill(ArrayList<Product> products, double total, Cashier owner) {
		this.products = products;
		LocalDateTime now = LocalDateTime.now();
		dateCreated = new SimpleDate(now);
		this.total = total;
		this.owner = owner;
		setbillId();
	}
	
	public int getId() { return id;	}
	
	public void addProduct(Product product) {
		products.add(product);
	}
	
	private void setbillId() {
		int index = 0;
		int newid;
		if(owner.getBills().size() != 0) {
			newid = owner.getBills().get(owner.getBills().size() - 1).getId();
		}else {
			newid = 0;
		}
		this.id = ++newid;
	}

	public ArrayList<Product> getProducts() { return products; }
	
	public SimpleDate getDateCreated() { return dateCreated; }

	public double getTotal() { return total; }
	public void setTotal(double total) { this.total = total; }

	public void toFile() {
		try {
			File file = new File("files" + File.separator + "bills" + File.separator +
					owner.getName() + File.separator + id + ".txt");
			file.getParentFile().mkdirs();
			PrintWriter pwr = new PrintWriter(file);
			pwr.println(dateCreated.toString() + "                " + owner.getName());
			pwr.println("=====================================");
			pwr.printf("%-20s%-10s%-5s\n", "Product", "Quantity" , "Price");
			for(Product p : products){
				pwr.printf("%-20s%-10s%-5s\n", p.getName(), p.getQuantity(), p.getPrice());
			}
			pwr.println("=====================================");
			pwr.write("Total: " + total);
			pwr.close();
			Alert al = new Alert(Alert.AlertType.INFORMATION, "Action performed successfully", ButtonType.OK);
			al.showAndWait();
		} catch (FileNotFoundException e) {
			Alert al = new Alert(Alert.AlertType.ERROR, "Cannot process request", ButtonType.OK);
			al.showAndWait();
			e.printStackTrace();
		}

	}
	
}
