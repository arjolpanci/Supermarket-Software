package application;


import java.time.LocalDate;

import data.FinancialAction;
import data.ProductIO;
import data.SaleManager;
import data.UserIO;
import employees.Admin;
import employees.User;
import javafx.application.Application;
import javafx.stage.Stage;
import products.Product;
import util.Notification;
import util.NotificationManager;
import util.SharedElements;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		LocalDate date = LocalDate.now();
		UserIO uio = new UserIO();
		SaleManager sm = new SaleManager();
		NotificationManager nm = new NotificationManager();
		
		if(date.getDayOfMonth() == 1) {
			for(User u : uio.getUsers()) {
				if(!(u instanceof Admin)) {
					FinancialAction fa = new FinancialAction(u, u.getSalary() * -1);
					sm.addFinancialAction(fa);
					Notification na = new Notification("Application", "Administrator", "Salaries have been paid");
					Notification ne = new Notification("Application", "Economist","Salaries have been paid");
					if(!nm.exits(na)) nm.addNotification(na);
					if(!nm.exits(ne))nm.addNotification(ne);
				}
			}
		}
	
		
		if(uio.isFirstTime() || uio.getAdminsCount() == 0) {
			SharedElements.initialView(uio);
			uio.update();
			LoginStage lgs = new LoginStage();
			lgs.view(primaryStage, uio);
		}else {
			LoginStage lgs = new LoginStage();
			lgs.view(primaryStage, uio);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
