package application;

import data.UserIO;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import util.FlatButton;
import util.SharedElements;


public class LoginStage {
	
	public void view(Stage previousStage, UserIO uio) {
		Stage loginStage = new Stage();
		
		BorderPane loginLayout = new BorderPane();
		StackPane leftArea = new StackPane();
		leftArea.setAlignment(Pos.CENTER);
		leftArea.setPrefWidth(180);
		leftArea.setStyle("-fx-background-color: #074F76");
		VBox loginForm = new VBox(65);
		loginForm.setAlignment(Pos.CENTER);
		HBox buttonArea = new HBox(40);
		buttonArea.setAlignment(Pos.CENTER);
		VBox textboxArea = new VBox(40);
		textboxArea.setAlignment(Pos.CENTER);
		
		
		//Username textbox layout
		ImageView userImg = new ImageView();
		userImg.setFitWidth(30);
		userImg.setFitHeight(30);
		userImg.setPreserveRatio(true);
		userImg.setImage(new Image("resources\\user.png"));
		
		TextField userTField = new TextField();
		userTField.setPromptText("Username");
		userTField.getStyleClass().add("textfield");
		userTField.setPrefWidth(300);
		
		HBox usernameLayout = new HBox(15);
		usernameLayout.setAlignment(Pos.CENTER);
		usernameLayout.getChildren().addAll(userImg, userTField);
		
		
		//Password textbox layout
		ImageView pwImg = new ImageView();
		pwImg.setFitWidth(30);
		pwImg.setFitHeight(30);
		pwImg.setPreserveRatio(true);
		pwImg.setImage(new Image("resources\\key.png"));
		
		PasswordField pwTField = new PasswordField();
		pwTField.setPromptText("Password");
		pwTField.getStyleClass().add("textfield");
		pwTField.setPrefWidth(300);
		pwTField.isFocused();
		
		HBox passwordLayout = new HBox(15);
		passwordLayout.setAlignment(Pos.CENTER);
		passwordLayout.getChildren().addAll(pwImg, pwTField);
		
		
		//Buttons layout
		FlatButton loginButton = new FlatButton("Login");
		loginButton.setPrefSize(100, 50);
		FlatButton exitButton = new FlatButton("Exit");
		exitButton.setPrefSize(100, 50);
	
		
		//Adding layout elements to the stage
		leftArea.getChildren().addAll(SharedElements.getIcon());
		buttonArea.getChildren().addAll(loginButton, exitButton);
		textboxArea.getChildren().addAll(usernameLayout, passwordLayout);
		loginForm.getChildren().addAll(textboxArea, buttonArea);
		loginLayout.setLeft(leftArea);
		loginLayout.setCenter(loginForm);
		
		Scene loginScene = new Scene(loginLayout,600,300);
		loginScene.getStylesheets().add("style.css");
		
		
		//Instantiating the stage
		loginStage.setTitle("Log In");
		loginStage.setResizable(false);
		loginStage.setScene(loginScene);
		loginStage.getIcons().add(SharedElements.getIcon().getImage());
		leftArea.requestFocus();
		loginStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        loginStage.setX((primScreenBounds.getWidth() - loginStage.getWidth()) / 2);
        loginStage.setY((primScreenBounds.getHeight() - loginStage.getHeight()) / 2);
        
        //Adding button handlers
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(uio.checkUser(userTField.getText(), pwTField.getText())) {
					AdminStage adminStage = new AdminStage();
					adminStage.view(loginStage);
					loginStage.close();
				}else {
					Alert al = new Alert(AlertType.ERROR, "No user exists with the given data", ButtonType.OK);
					al.show();
				}
			}
        });
        
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				loginStage.close();
			}
        });
	}
}