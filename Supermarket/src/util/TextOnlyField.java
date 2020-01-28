package util;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import employees.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import products.Product;

public class TextOnlyField extends TextField{
	
	public TextOnlyField() {
		super();
		this.getStylesheets().add("style.css");
		this.getStyleClass().add("textfield");
		Pattern pattern = Pattern.compile("[a-zA-Z]*");
		UnaryOperator<TextFormatter.Change> filter = c -> {
		    if (pattern.matcher(c.getControlNewText()).matches()) {
		        return c ;
		    } else {
		        return null ;
		    }
		};
		TextFormatter<String> formatter = new TextFormatter<>(filter);
		this.setTextFormatter(formatter);
	}
	
}
