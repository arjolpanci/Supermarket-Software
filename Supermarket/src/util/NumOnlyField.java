package util;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class NumOnlyField extends TextField{
	
	public NumOnlyField() {
		super();
		this.getStylesheets().add("style.css");
		this.getStyleClass().add("textfield");
		Pattern pattern = Pattern.compile("[.0-9]*");
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
