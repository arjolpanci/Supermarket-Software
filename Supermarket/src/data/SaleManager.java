package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

public class SaleManager {
	
	private ArrayList<FinancialAction> falist;
	private String path = "files" + File.separator + "finances.bin";
	private File file;
	
	public SaleManager() {
		falist = new ArrayList<FinancialAction>();
		file = new File(path);
		
		if(file.exists()) {
			read();
		}else {
			write();
		}
	}
	
	public void addFinancialAction(FinancialAction fa) {
		falist.add(fa);
		write();
	}
	
	public ArrayList<FinancialAction> getFinances() {
		return falist;
	}

	private void read() {
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			falist = (ArrayList<FinancialAction>) ois.readObject();
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

	private void write() {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(falist);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public float getTotalForMonth(int month, int year) {
		float total = 0;
		for(FinancialAction fa : falist) {
			if(fa.getDate().getMonth() == month && fa.getDate().getYear() == year) {
				total += fa.getAmount();
			}
		}
		return total;
	}

}
