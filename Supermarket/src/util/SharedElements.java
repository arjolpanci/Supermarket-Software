package util;

import java.io.File;

import data.UserIO;
import employees.Admin;
import employees.Cashier;
import employees.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
					user.setBirthday(new SimpleDate(editBirthdayField.getValue()));
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
				editUserStage.close();
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
	
	public static void addUserView(Stage previousStage, UserIO uio) {
		Stage addUserStage = new Stage();

		//Layout stuff
		VBox finalLayout = new VBox(40);
		
		StackPane header = new StackPane();
		header.setPrefHeight(60);
		header.setAlignment(Pos.CENTER);
		header.setStyle("-fx-background-color: #074F76");
		Label headerLabel = new Label("Add User");
		if(uio.isFirstTime()) headerLabel.setText("Add Administrator");
		headerLabel.setStyle("-fx-text-fill: White");
		headerLabel.setFont(new Font(20));
		header.getChildren().add(headerLabel);
		
		VBox fullLayout = new VBox(30);
		fullLayout.setAlignment(Pos.CENTER);
		HBox buttonArea = new HBox(20);
		buttonArea.setAlignment(Pos.CENTER);
		VBox dataArea = new VBox(22);
		dataArea.setAlignment(Pos.CENTER);
		
		//Button stuff
		FlatButton addButton = new FlatButton("Add");
		FlatButton cancelButton = new FlatButton("Cancel");
		buttonArea.getChildren().addAll(addButton, cancelButton);

		//Setting up the data fields
		ChoiceBox<String> choiceField = new ChoiceBox<String>();
		choiceField.getStyleClass().add("combobox");
		choiceField.setPrefWidth(200);
		choiceField.getItems().add("Administrator");
		choiceField.getItems().add("Cashier");
		choiceField.getItems().add("Economist");
		Label typeLabel = new Label("User Type: ");
		HBox choiceArea = new HBox(20);
		choiceArea.setAlignment(Pos.CENTER);
		choiceArea.getChildren().addAll(typeLabel, choiceField);
		if(uio.isFirstTime()) {
			choiceField.setValue("Administrator");
			choiceField.setDisable(true);
		}
		
		TextField nameTField = new TextField();
		nameTField.getStyleClass().add("textfield");
		nameTField.setPrefWidth(200);
		nameTField.setPromptText("Enter Name ...");
		Label nameLabel = new Label("First Name: ");
		HBox nameArea = new HBox(20);
		nameArea.setAlignment(Pos.CENTER);
		nameArea.getChildren().addAll(nameLabel, nameTField);
		
		TextField surnameTField = new TextField();
		surnameTField.getStyleClass().add("textfield");
		surnameTField.setPrefWidth(200);
		surnameTField.setPromptText("Enter Surname ...");
		Label surnameLabel = new Label("Last Name: ");
		HBox surnameArea = new HBox(20);
		surnameArea.setAlignment(Pos.CENTER);
		surnameArea.getChildren().addAll(surnameLabel, surnameTField);
		
		TextField usernameTField = new TextField();
		usernameTField.getStyleClass().add("textfield");
		usernameTField.setPrefWidth(200);
		usernameTField.setPromptText("Enter Username ...");
		Label usernameLabel = new Label("Username: ");
		HBox usernameArea = new HBox(20);
		usernameArea.setAlignment(Pos.CENTER);
		usernameArea.getChildren().addAll(usernameLabel, usernameTField);
		
		TextField passwordTField = new TextField();
		passwordTField.getStyleClass().add("textfield");
		passwordTField.setPrefWidth(200);
		passwordTField.setPromptText("Enter Password ...");
		Label passwordLabel = new Label("Password: ");
		HBox passwordArea = new HBox(20);
		passwordArea.setAlignment(Pos.CENTER);
		passwordArea.getChildren().addAll(passwordLabel, passwordTField);
		
		DatePicker birthdayField = new DatePicker();
		Label dateLabel = new Label("Birthday: ");
		HBox birthdayArea = new HBox(20);
		birthdayArea.setAlignment(Pos.CENTER);
		birthdayArea.getChildren().addAll(dateLabel, birthdayField);
		
		TextField salaryField = new TextField();
		salaryField.getStyleClass().add("textfield");
		salaryField.setPrefWidth(200);
		salaryField.setPromptText("Enter Salary ...");
		Label salaryLabel = new Label("Salary: ");
		HBox salaryArea = new HBox(20);
		salaryArea.setAlignment(Pos.CENTER);
		salaryArea.getChildren().addAll(salaryLabel, salaryField);
		if(uio.isFirstTime()) {
			salaryField.setText("0");
			salaryField.setDisable(true);
		}
		
		//Button Functions
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean flag = false;
				try {
					if(choiceField.getValue().equals(null) || nameTField.getText().equals("") || surnameTField.getText().equals("") ||
							usernameTField.getText().equals("") || passwordTField.getText().equals("") || birthdayField.getValue().equals(null)
							|| salaryField.getText().equals("")) {
						Alert al = new Alert(AlertType.ERROR, "Please fill in all the data", ButtonType.OK);
						al.show();
					}else {
						String type = choiceField.getValue();
						switch (type) {
						case "Administrator":
							Admin adm = new Admin(nameTField.getText(), surnameTField.getText(), usernameTField.getText(),
									passwordTField.getText(), new SimpleDate(birthdayField.getValue()));
							if(uio.isFirstTime()) {
								adm.setId(1);
								uio.addUser(adm);
								addUserStage.close();
								return;
							}
							uio.addUser(adm);
							flag = true;
							break;
						case "Cashier":
							Cashier csh = new Cashier(nameTField.getText(), surnameTField.getText(), usernameTField.getText(),
									passwordTField.getText(), new SimpleDate(birthdayField.getValue()), Integer.parseInt(salaryField.getText()));
							uio.addUser(csh);
							flag = true;
							break;
						}
					}	
				}catch (Exception ex) {
                	Alert al = new Alert(AlertType.ERROR, "Cannot process request", ButtonType.OK);
                	al.show();
				}

				if(flag) {
					Alert al = new Alert(AlertType.INFORMATION, "Action performed succesfully", ButtonType.OK);
					al.showAndWait();
				}
				
			}
		});
		
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				addUserStage.close();
			}
		});
		
		//Event handlers
		choiceField.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if(arg2.equals("Administrator")) {
					salaryField.setText("0");
					salaryField.setDisable(true);
				}else {
					salaryField.setDisable(false);
				}
			}
		});
		
		//Constructing the scene
		dataArea.getChildren().addAll(choiceArea, nameArea, surnameArea, usernameArea, passwordArea, birthdayArea, salaryArea, buttonArea);
		fullLayout.getChildren().addAll(dataArea, buttonArea);
		finalLayout.getChildren().addAll(header, fullLayout);
		Scene addUserScene = new Scene(finalLayout, 400,460);
		addUserScene.getStylesheets().add("style.css");

		addUserStage.setScene(addUserScene);
		addUserStage.getIcons().add(SharedElements.getIcon().getImage());
		addUserStage.initModality(Modality.APPLICATION_MODAL);
		addUserStage.setTitle("Add User");
		addUserStage.setResizable(false);
		addUserStage.showAndWait();
	}
	
	public static ImageView getIcon() {
		ImageView logoImg = new ImageView();
		logoImg.setFitWidth(150);
		logoImg.setFitHeight(150);
		logoImg.setPreserveRatio(true);
		logoImg.setImage(new Image("resources\\logo.png"));
		return logoImg;
	}
	
	public static ImageView getSearchIcon() {
		ImageView searchImgView = new ImageView();
		searchImgView.setFitHeight(20);
		searchImgView.setFitWidth(20);
		searchImgView.setPreserveRatio(true);
		searchImgView.setImage(new Image("resources" + File.separator + "search.png"));
		return searchImgView;
	}


}
