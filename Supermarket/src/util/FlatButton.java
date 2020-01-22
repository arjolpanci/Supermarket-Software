package util;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;

public class FlatButton extends Button{
	
	public FlatButton(String string) {
		super(string);
		this.getStylesheets().add("style.css");
		this.getStyleClass().add("custom-button");
		//this.setStyle("-fx-background-radius: 0;" + "-fx-text-fill: #074F76;" 
		//		+ "-fx-font-size: 14;" + "-fx-font-weight: bold;");
		this.setContentDisplay(ContentDisplay.TOP);
	}
	
	public FlatButton(String string, ImageView imgView) {
		super(string, imgView);
		this.getStylesheets().add("style.css");
		this.getStyleClass().add("custom-button");
		//this.setStyle("-fx-background-radius: 0;" + "-fx-text-fill: #074F76;" 
		//		+ "-fx-font-size: 14;" + "-fx-font-weight: bold;");
		this.setContentDisplay(ContentDisplay.TOP);
	}
	

}
