package util;

import java.io.File;

import data.ProductIO;
import data.UserIO;
import employees.Admin;
import employees.Cashier;
import employees.Economist;
import employees.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import products.Product;

public class SharedElements {
	
	public static void editUserView(Stage previousStage, User user, UserIO uio) {
		Stage editUserStage = new Stage();
		editUserStage.getIcons().add(SharedElements.getIcon().getImage());
		editUserStage.initModality(Modality.APPLICATION_MODAL);
		StackPane header = new StackPane();
		header.setAlignment(Pos.CENTER);
		header.setPrefHeight(70);
		Label headerLabel = new Label("Edit User (" + user.getName() + ")");
		headerLabel.setStyle("-fx-text-fill: White");
		headerLabel.setFont(new Font(18));
		header.getChildren().addAll(headerLabel);
		header.setStyle("-fx-background-color: #074F76");
		
		TextField editNameTField = new TextField();
		editNameTField.setText(user.getName());
		Label nameLabel = new Label("First Name: \t");
		HBox nameArea = new HBox(10);
		nameArea.setAlignment(Pos.CENTER);
		nameArea.getChildren().addAll(nameLabel, editNameTField);
		
		TextField editSurnameTField = new TextField();
		editSurnameTField.setText(user.getSurname());
		Label surnameLabel = new Label("Last Name: \t");
		HBox surnameArea = new HBox(10);
		surnameArea.setAlignment(Pos.CENTER);
		surnameArea.getChildren().addAll(surnameLabel, editSurnameTField);
		
		TextField editUsernameTField = new TextField();
		editUsernameTField.setText(user.getUsername());
		Label usernameLabel = new Label("Username: \t");
		HBox usernameArea = new HBox(10);
		usernameArea.setAlignment(Pos.CENTER);
		usernameArea.getChildren().addAll(usernameLabel, editUsernameTField);
		
		TextField editPasswordTField = new TextField();
		editPasswordTField.setText(user.getPassword());
		Label passwordLabel = new Label("Password: \t");
		HBox passwordArea = new HBox(10);
		passwordArea.setAlignment(Pos.CENTER);
		passwordArea.getChildren().addAll(passwordLabel, editPasswordTField);
		
		DatePicker editBirthdayField = new DatePicker();
		editBirthdayField.setValue(user.getBirthday().toLocalDate());
		Label birthdayLabel = new Label("Birthday: \t");
		HBox birthdayArea = new HBox(10);
		birthdayArea.setAlignment(Pos.CENTER);
		birthdayArea.getChildren().addAll(birthdayLabel, editBirthdayField);
		
		FlatButton saveButton = new FlatButton("Save");
		saveButton.setPrefSize(80, 50);
		FlatButton cancelButton = new FlatButton("Cancel");
		cancelButton.setPrefSize(80, 50);
		
		HBox buttonArea = new HBox(80);
		buttonArea.setPrefHeight(60);
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
		
		VBox layout = new VBox(20);
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(header, nameArea, surnameArea, usernameArea, passwordArea, birthdayArea, buttonArea);

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
						case "Economist":
							Economist ec = new Economist(nameTField.getText(), surnameTField.getText(), usernameTField.getText(),
									passwordTField.getText(), new SimpleDate(birthdayField.getValue()), Integer.parseInt(salaryField.getText()));
							uio.addUser(ec);
							flag = true;
							break;
						default:
			               	Alert al = new Alert(AlertType.ERROR, "Cannot process request", ButtonType.OK);
		                	al.show();
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
	
	public static void addProductView(Stage previousStage, ProductIO pio) {
		Stage addProductStage = new Stage();

		//Layout stuff
		VBox finalLayout = new VBox(40);
		
		StackPane header = new StackPane();
		header.setPrefHeight(60);
		header.setAlignment(Pos.CENTER);
		header.setStyle("-fx-background-color: #074F76");
		Label headerLabel = new Label("Add Product");
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
		ChoiceBox<String> existingCBox = new ChoiceBox<String>();
		existingCBox.getItems().add("New Product");
		for(Product p : pio.getProducts()) {
			existingCBox.getItems().add(p.getName());
		}
		existingCBox.setValue(existingCBox.getItems().get(0));
		Label existingLabel = new Label("Existing Products: ");
		HBox existingArea = new HBox(20);
		existingArea.setAlignment(Pos.CENTER);
		existingArea.getChildren().addAll(existingLabel, existingCBox);
		
		TextField nameTField = new TextField();
		nameTField.getStyleClass().add("textfield");
		nameTField.setPrefWidth(200);
		nameTField.setPromptText("Enter Name ...");
		Label nameLabel = new Label("Name: ");
		HBox nameArea = new HBox(20);
		nameArea.setAlignment(Pos.CENTER);
		nameArea.getChildren().addAll(nameLabel, nameTField);
		
		TextField supplierTField = new TextField();
		supplierTField.getStyleClass().add("textfield");
		supplierTField.setPrefWidth(200);
		supplierTField.setPromptText("Enter Supplier ...");
		Label supplierLabel = new Label("Supplier: ");
		HBox supplierArea = new HBox(20);
		supplierArea.setAlignment(Pos.CENTER);
		supplierArea.getChildren().addAll(supplierLabel, supplierTField);
		
		TextField quantityTField = new TextField();
		quantityTField.getStyleClass().add("textfield");
		quantityTField.setPrefWidth(200);
		quantityTField.setPromptText("Enter Quantity ...");
		Label quantityLabel = new Label("Quantity: ");
		HBox quantityArea = new HBox(20);
		quantityArea.setAlignment(Pos.CENTER);
		quantityArea.getChildren().addAll(quantityLabel, quantityTField);
		
		TextField priceTField = new TextField();
		priceTField.getStyleClass().add("textfield");
		priceTField.setPrefWidth(200);
		priceTField.setPromptText("Enter Selling Price ...");
		Label priceLabel = new Label("Price: ");
		HBox priceArea = new HBox(20);
		priceArea.setAlignment(Pos.CENTER);
		priceArea.getChildren().addAll(priceLabel, priceTField);

		TextField barcodeTField = new TextField();
		barcodeTField.getStyleClass().add("textfield");
		barcodeTField.setPrefWidth(200);
		barcodeTField.setPromptText("Enter Barcode ...");
		Label barcodeLabel = new Label("Barcode: ");
		HBox barcodeArea = new HBox(20);
		barcodeArea.setAlignment(Pos.CENTER);
		barcodeArea.getChildren().addAll(barcodeLabel, barcodeTField);
		
		dataArea.getChildren().addAll(existingArea, nameArea, supplierArea, quantityArea, priceArea, barcodeArea, buttonArea);
		fullLayout.getChildren().addAll(dataArea, buttonArea);
		finalLayout.getChildren().addAll(header, fullLayout);
		Scene addProductScene = new Scene(finalLayout, 400,460);
		addProductScene.getStylesheets().add("style.css");
		
		
		//Adding functions to buttons
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String name = nameTField.getText();
				String supplier = supplierTField.getText();
				int quantity = Integer.parseInt(quantityTField.getText());
				float price = Float.parseFloat(priceTField.getText());
				int barcode = Integer.parseInt(barcodeTField.getText());
				pio.addProduct(new Product(name, supplier, quantity, price, barcode));
				Alert al = new Alert(AlertType.INFORMATION, "Action performed succesfully", ButtonType.OK);
				al.showAndWait();
			}
		});
		
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				addProductStage.close();
			}
		});
		
		//Handling other events
		existingCBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if(arg2.equals("New Product")) {
					nameTField.setDisable(false);
					nameTField.setText("");
					supplierTField.setDisable(false);
					supplierTField.setText("");
					quantityTField.setDisable(false);
					quantityTField.setText("");
					priceTField.setDisable(false);
					priceTField.setText("");
					barcodeTField.setDisable(false);
					barcodeTField.setText("");
				}else {
					Product p = pio.getProductFromName(arg2);
					nameTField.setDisable(true);
					nameTField.setText(p.getName());
					supplierTField.setDisable(true);
					supplierTField.setText(p.getName());
					quantityTField.setText("" + p.getQuantity());
					priceTField.setText("" + p.getPrice());
					barcodeTField.setDisable(true);
					barcodeTField.setText("" + p.getBarcode());
				}
			}
		});

		addProductStage.setScene(addProductScene);
		addProductStage.getIcons().add(SharedElements.getIcon().getImage());
		addProductStage.initModality(Modality.APPLICATION_MODAL);
		addProductStage.setTitle("Add Product");
		addProductStage.setResizable(false);
		addProductStage.showAndWait();
	}
	
	public static void editProductView(Stage previousStage, ProductIO pio, Product p) {
		Stage editProductStage = new Stage();
		Label quantityLabel = new Label("Change Quantity: ");
		TextField qtyTField = new TextField();
		qtyTField.setText("" + p.getQuantity());
		qtyTField.getStyleClass().add("textfield");
		HBox quantityArea = new HBox(15);
		quantityArea.setAlignment(Pos.CENTER);
		quantityArea.getChildren().addAll(quantityLabel, qtyTField);
		
		Label priceLabel = new Label("Change Price: ");
		TextField priceTField = new TextField();
		priceTField.setText("" + p.getPrice());
		priceTField.getStyleClass().add("textfield");
		HBox priceArea = new HBox(15);
		priceArea.setAlignment(Pos.CENTER);
		priceArea.getChildren().addAll(priceLabel, priceTField);
		
		FlatButton editButton = new FlatButton("Edit");
		FlatButton cancelButton = new FlatButton("Cancel");
		HBox buttonArea = new HBox(30);
		buttonArea.setAlignment(Pos.CENTER);
		buttonArea.setPrefHeight(100);
		buttonArea.setStyle("-fx-background-color: #074F76");
		buttonArea.getChildren().addAll(editButton, cancelButton);
		
		//Adding Functions to buttons
		editButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(qtyTField.getText().equals("") || priceTField.getText().equals("")) {
					Alert al = new Alert(AlertType.ERROR, "Please fill in all the data", ButtonType.OK);
					al.setTitle("Error");
					al.showAndWait();
				}else {
					try {
						int newqty = Integer.parseInt(qtyTField.getText());
						float newprice = Float.parseFloat(priceTField.getText());
						pio.removeProduct(p);
						p.setQuantity(newqty);
						p.setPrice(newprice);
						pio.addProduct(p);
						pio.update();
						Alert al = new Alert(AlertType.INFORMATION, "Action performed succesfully", ButtonType.OK);
						al.setTitle("Information");
						al.showAndWait();
					} catch(Exception ex) {
						Alert al = new Alert(AlertType.ERROR, "Cannot Process Request", ButtonType.OK);
						al.setTitle("Error");
						al.showAndWait();
					}
				}
			}
		});
		
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				editProductStage.close();
			}
		});
		
		VBox layout = new VBox(20);
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(quantityArea, priceArea, buttonArea);
		Scene editProductScene = new Scene(layout, 400, 150);
		editProductStage.setTitle("Edit Product");
		editProductStage.setScene(editProductScene);
		editProductStage.showAndWait();
	}
	
	public static void notificationView(User sender, NotificationManager nm) {
		Stage createNotificationStage = new Stage();
		createNotificationStage.initModality(Modality.APPLICATION_MODAL);
		createNotificationStage.setTitle("Send Notification");
		
		Label sendToLabel = new Label("Send To: ");
		ChoiceBox<String> reciever = new ChoiceBox<String>();
		reciever.getItems().addAll("Administrator", "Economist", "Cashier");
		HBox sendArea = new HBox(20);
		sendArea.setAlignment(Pos.CENTER);
		sendArea.getChildren().addAll(sendToLabel, reciever);
		
		Label messageLabel = new Label("Message: ");
		TextArea msgArea = new TextArea();
		msgArea.setPromptText("Enter message here");
		HBox messageArea = new HBox(20);
		messageArea.setAlignment(Pos.CENTER);
		messageArea.getChildren().addAll(messageLabel, msgArea);
		
		FlatButton sendNotification = new FlatButton("Send");
		
		sendNotification.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					String choice = reciever.getSelectionModel().getSelectedItem();
					if(msgArea.getText().equals("")) {
						Alert al = new Alert(AlertType.ERROR, "Please fill in the message", ButtonType.OK);
						al.showAndWait();
					}
					Notification n = new Notification(sender.getUsertype(), choice, msgArea.getText());
					nm.addNotification(n);
					nm.update();
					Alert al = new Alert(AlertType.INFORMATION, "Action performed succesfully", ButtonType.OK);
					al.showAndWait();
				} catch (Exception ex) {
					Alert al = new Alert(AlertType.ERROR, "Cannot process request", ButtonType.OK);
					al.showAndWait();
				}
				
			}
		});
		
		VBox layout = new VBox(15);
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(sendArea, messageArea, sendNotification);
		
		Scene notifCreatorScene = new Scene(layout, 250, 250);
		createNotificationStage.setScene(notifCreatorScene);
		createNotificationStage.showAndWait();
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
