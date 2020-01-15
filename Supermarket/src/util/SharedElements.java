package util;

import data.UserIO;
import employees.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SharedElements {
	
	public static void editUserView(Stage previousStage, User user, UserIO uio) {
		Stage editUserStage = new Stage();
		editUserStage.getIcons().add(SharedElements.getIcon().getImage());
		editUserStage.initModality(Modality.APPLICATION_MODAL);
		
		Separator sep = new Separator();
		sep.setOrientation(Orientation.VERTICAL);
		
		Label currentValues = new Label("Current Values");
		currentValues.setFont(new Font(20));
		Label newValues = new Label("New Values");
		newValues.setFont(new Font(20));
		
		Label currentName = new Label("First Name: \t" + user.getName());
		Label currentSurname = new Label("Last Name: \t" + user.getSurname());
		Label currentUsername = new Label("Username: \t" + user.getUsername());
		Label currentPassword = new Label("Password: \t" + user.getPassword());
		Label currentBirthday = new Label("Birthday: \t" + user.getBirthday().toString());

		VBox labelArea = new VBox(30);
		labelArea.getChildren().addAll(currentValues, currentName, currentSurname, currentUsername, currentPassword, currentBirthday);
		
		
		TextField editNameTField = new TextField();
		editNameTField.setPromptText("New Name");
		TextField editSurnameTField = new TextField();
		editSurnameTField.setPromptText("New Surname");
		TextField editUsernameTField = new TextField();
		editUsernameTField.setPromptText("New Username");
		TextField editPasswordTField = new TextField();
		editPasswordTField.setPromptText("New Password");
		DatePicker editBirthdayField = new DatePicker();
		
		FlatButton saveButton = new FlatButton("Save");
		saveButton.setPrefSize(80, 50);
		FlatButton cancelButton = new FlatButton("Cancel");
		cancelButton.setPrefSize(80, 50);
		
		HBox buttonArea = new HBox(80);
		buttonArea.setPrefHeight(70);
		buttonArea.setAlignment(Pos.CENTER);
		buttonArea.setStyle("-fx-background-color: #074F76");
		buttonArea.getChildren().addAll(saveButton, cancelButton);
		
		
		//Adding button functions
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				boolean flag = false;
				
				uio.removeUser(user);
				
				if(!editNameTField.getText().equals("")) {
					user.setName(editNameTField.getText());
					flag = true;
				}
				if(!editSurnameTField.getText().equals("")) {
					user.setSurname(editSurnameTField.getText());
					flag = true;
				}
				if(!editUsernameTField.getText().equals("")) {
					user.setUsername(editUsernameTField.getText());
					flag = true;
				}
				if(!editPasswordTField.getText().equals("")) {
					user.setPassword(editPasswordTField.getText());
					flag = true;
				}
				if(editBirthdayField.getValue() != null) {
					user.setBirthday(new SimpleDate(editBirthdayField.getValue().getDayOfMonth(),
							editBirthdayField.getValue().getMonthValue(),
							editBirthdayField.getValue().getYear()));
					flag = true;
				}
				
				uio.addUser(user);
				
				if(!flag) {
					Alert al = new Alert(AlertType.ERROR, "No new data was entered", ButtonType.OK);
					al.show();
				}else {
					Alert al = new Alert(AlertType.INFORMATION, "Action performed succesfully", ButtonType.OK);
					al.show();
				}
			}
		});
		
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				previousStage.close();
				editUserStage.close();
				previousStage.show();
			}
		});
		
		VBox tfieldArea = new VBox(20);
		tfieldArea.setAlignment(Pos.CENTER);
		tfieldArea.getChildren().addAll(newValues, editNameTField, editSurnameTField, editUsernameTField, editPasswordTField, editBirthdayField);
		
		
		HBox fullLayout = new HBox(40);
		fullLayout.setAlignment(Pos.CENTER);
		fullLayout.getChildren().addAll(labelArea, sep, tfieldArea);
		
		VBox layout = new VBox(45);
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(fullLayout, buttonArea);

		
		Scene editUserScene = new Scene(layout, 450, 360);
		editUserStage.setTitle("Edit User");
		editUserStage.setResizable(false);
		editUserStage.setScene(editUserScene);
		editUserStage.showAndWait();

	}
	
	public static ImageView getIcon() {
		ImageView logoImg = new ImageView();
		logoImg.setFitWidth(150);
		logoImg.setFitHeight(150);
		logoImg.setPreserveRatio(true);
		logoImg.setImage(new Image("resources\\logo.png"));
		return logoImg;
	}


}
