package util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class NotEnoughQuantityException extends Exception{
	
	public NotEnoughQuantityException(int qty) {
		Alert al;
		if(qty <= 0) {
			al = new Alert(AlertType.ERROR, "You can't have zero or negative quantity", ButtonType.OK);
		}else {
			al = new Alert(AlertType.ERROR, "You don't have enough of that product", ButtonType.OK);
		}
		al.setTitle("Error");
		al.show();
	}
	
}
