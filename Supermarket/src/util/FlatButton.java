package util;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;

public class FlatButton extends Button{
	
	public FlatButton(String string) {
		super(string);
		this.getStylesheets().add("style.css");
		this.getStyleClass().add("custom-button");
		this.setContentDisplay(ContentDisplay.TOP);
	}
	
	public FlatButton(String string, ImageView imgView) {
		super(string, imgView);
		this.getStylesheets().add("style.css");
		this.getStyleClass().add("custom-button");

		this.setContentDisplay(ContentDisplay.TOP);
	}
	
	public FlatButton(String string, int width, int height) {
		super(string);
		this.getStylesheets().add("style.css");
		this.getStyleClass().add("custom-button");
		this.setPrefSize(width, height);
		this.setContentDisplay(ContentDisplay.TOP);
	}
	
}
