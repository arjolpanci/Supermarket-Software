package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import data.Bill;
import data.FinancialAction;
import data.ProductIO;
import data.SaleManager;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import products.Product;
import resources.ResourceManager;

public class SharedElements {
	
	public static void editUserView(Stage previousStage, User user, UserIO uio) {
		Stage editUserStage = new Stage();
		editUserStage.setResizable(false);
		editUserStage.getIcons().add(SharedElements.getIcon().getImage());
		editUserStage.initModality(Modality.APPLICATION_MODAL);
		
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
		saveButton.setPrefSize(70, 30);
		FlatButton cancelButton = new FlatButton("Cancel");
		cancelButton.setPrefSize(70, 30);
		
		HBox buttonArea = new HBox(80);
		buttonArea.setPrefHeight(60);
		buttonArea.setAlignment(Pos.CENTER);
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
		layout.getChildren().addAll(SharedElements.getHeader("Edit User: " + user.getName(), 0, 70), nameArea, surnameArea, usernameArea, passwordArea, birthdayArea, buttonArea);

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
		
		TextOnlyField nameTField = new TextOnlyField();
		nameTField.setPrefWidth(200);
		nameTField.setPromptText("Enter Name ...");
		Label nameLabel = new Label("First Name: ");
		HBox nameArea = new HBox(20);
		nameArea.setAlignment(Pos.CENTER);
		nameArea.getChildren().addAll(nameLabel, nameTField);
		
		TextOnlyField surnameTField = new TextOnlyField();
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
					nameTField.clear();
					surnameTField.clear();
					usernameTField.clear();
					passwordTField.clear();
					birthdayField.setValue(null);
					salaryField.clear();
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
		finalLayout.getChildren().addAll(SharedElements.getHeader("Add User", 0, 60), fullLayout);
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
		
		TextField buyingpriceTField = new TextField();
		buyingpriceTField.getStyleClass().add("textfield");
		buyingpriceTField.setPrefWidth(200);
		buyingpriceTField.setPromptText("Enter Product Price ...");
		Label buyingpriceLabel = new Label("Product Price: ");
		HBox buyingpriceArea = new HBox(20);
		buyingpriceArea.setAlignment(Pos.CENTER);
		buyingpriceArea.getChildren().addAll(buyingpriceLabel, buyingpriceTField);
		
		TextField priceTField = new TextField();
		priceTField.getStyleClass().add("textfield");
		priceTField.setPrefWidth(200);
		priceTField.setPromptText("Enter Selling Price ...");
		Label priceLabel = new Label("Selling Price: ");
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
		
		DatePicker dateField = new DatePicker();
		Label expireLabel = new Label("Expire Date: ");
		HBox expireArea = new HBox(20);
		expireArea.setAlignment(Pos.CENTER);
		expireArea.getChildren().addAll(expireLabel, dateField);
		
		dataArea.getChildren().addAll(existingArea, nameArea, supplierArea, quantityArea, buyingpriceArea, priceArea, barcodeArea, buttonArea, expireArea);
		fullLayout.getChildren().addAll(dataArea, buttonArea);
		finalLayout.getChildren().addAll(SharedElements.getHeader("Add Product", 0, 60), fullLayout);
		Scene addProductScene = new Scene(finalLayout, 400,520);
		addProductScene.getStylesheets().add("style.css");
		
		
		//Adding functions to buttons
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					String name = nameTField.getText();
					String supplier = supplierTField.getText();
					int quantity = Integer.parseInt(quantityTField.getText());
					float buyingprice = Float.parseFloat(buyingpriceTField.getText());
					float price = Float.parseFloat(priceTField.getText());
					int barcode = Integer.parseInt(barcodeTField.getText());
					pio.addProduct(new Product(name, supplier, quantity, buyingprice, price, barcode, new SimpleDate(dateField.getValue())), true);
					Alert al = new Alert(AlertType.INFORMATION, "Action performed succesfully", ButtonType.OK);
					al.showAndWait();
					nameTField.clear();
					supplierTField.clear();
					quantityTField.clear();
					buyingpriceTField.clear();
					priceTField.clear();
					barcodeTField.clear();
					dateField.setValue(null);
					existingCBox.getItems().remove(1, existingCBox.getItems().size());
					for(Product p : pio.getProducts()) {
						existingCBox.getItems().add(p.getName());
					}
				} catch (Exception ex) {
					Alert al = new Alert(AlertType.ERROR, "Cannot Process Request", ButtonType.OK);
					al.setTitle("Error");
					al.showAndWait();
				}

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
					nameTField.clear();
					supplierTField.setDisable(false);
					supplierTField.clear();
					quantityTField.setDisable(false);
					quantityTField.clear();
					priceTField.setDisable(false);
					priceTField.clear();
					barcodeTField.setDisable(false);
					barcodeTField.clear();
					dateField.setDisable(false);
					dateField.setValue(null);
				}else {
					Product p = pio.getProductFromName(arg2);
					nameTField.setDisable(true);
					nameTField.setText(p.getName());
					supplierTField.setDisable(true);
					supplierTField.setText(p.getName());
					quantityTField.setText("" + p.getQuantity());
					buyingpriceTField.setDisable(true);
					buyingpriceTField.setText("" + p.getBuyingprice());
					priceTField.setText("" + p.getPrice());
					barcodeTField.setDisable(true);
					barcodeTField.setText("" + p.getBarcode());
					dateField.setDisable(true);
					dateField.setValue(p.getExpireDate());
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
		editProductStage.initModality(Modality.APPLICATION_MODAL);
		editProductStage.getIcons().add(SharedElements.getIcon().getImage());
		editProductStage.setResizable(false);
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
						pio.addProduct(p, true);
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
		layout.getChildren().addAll(SharedElements.getHeader("Edit Product: " + p.getName(), 0, 60), quantityArea, priceArea, buttonArea);
		Scene editProductScene = new Scene(layout, 400, 240);
		editProductStage.setTitle("Edit Product");
		editProductStage.setScene(editProductScene);
		editProductStage.showAndWait();
	}
	
	public static void notificationView(User sender, NotificationManager nm) {
		Stage createNotificationStage = new Stage();
		createNotificationStage.setResizable(false);
		createNotificationStage.getIcons().add(SharedElements.getIcon().getImage());
		createNotificationStage.initModality(Modality.APPLICATION_MODAL);
		createNotificationStage.setTitle("Send Notification");
		
		Label sendToLabel = new Label("Send To: ");
		ChoiceBox<String> reciever = new ChoiceBox<String>();
		reciever.getItems().addAll("Administrator", "Economist", "Cashier");
		HBox sendArea = new HBox(20);
		sendArea.setAlignment(Pos.CENTER);
		sendArea.getChildren().addAll(sendToLabel, reciever);
		
		TextArea msgArea = new TextArea();
		msgArea.setPrefWidth(300);
		msgArea.setPromptText("Enter message here");
		HBox messageArea = new HBox(20);
		messageArea.setAlignment(Pos.CENTER);
		messageArea.getChildren().addAll(msgArea);
		
		HBox button = new HBox();
		button.setAlignment(Pos.CENTER);
		FlatButton sendNotification = new FlatButton("Send");
		button.getChildren().add(sendNotification);
		button.setPrefHeight(50);
		
		sendNotification.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					String choice = reciever.getSelectionModel().getSelectedItem();
					if(msgArea.getText().equals("")) {
						Alert al = new Alert(AlertType.ERROR, "Please fill in the message", ButtonType.OK);
						al.showAndWait();
						return;
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
				msgArea.clear();
			}
		});
		
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(SharedElements.getHeader("Send Notifications", 0, 60), sendArea, messageArea, button);
		
		Scene notifCreatorScene = new Scene(layout, 400, 330);
		createNotificationStage.setScene(notifCreatorScene);
		createNotificationStage.showAndWait();
	}
	
	public static void viewStatistics(Cashier c) {
		Stage statsStage = new Stage();
		statsStage.initModality(Modality.APPLICATION_MODAL);
		statsStage.setTitle("Statistics");
		statsStage.getIcons().add(SharedElements.getIcon().getImage());
		
		TableView<Bill> bills = new TableView<Bill>();
		TableColumn<Bill, Integer> column1 = new TableColumn<>("Id");
		column1.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Bill, String> column2 = new TableColumn<>("Date Created");
		column2.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
		TableColumn<Bill, Integer> column3 = new TableColumn<>("Total");
		column3.setCellValueFactory(new PropertyValueFactory<>("total"));
		bills.getColumns().addAll(column1, column2, column3);
		bills.setPrefWidth(400);
		
		for(Bill b : c.getBills()) {
			bills.getItems().add(b);
		}
		
		ScrollPane table = new ScrollPane();
		table.setContent(bills);
		table.setFitToWidth(true);
		
		DatePicker startDate = new DatePicker();
		Label fromLabel = new Label("From: ");
		fromLabel.setStyle("-fx-text-fill: White");
		HBox startArea = new HBox(10);
		startArea.setAlignment(Pos.CENTER);
		startArea.getChildren().addAll(fromLabel, startDate);
		
		DatePicker endDate = new DatePicker();
		Label toLabel = new Label("To: ");
		toLabel.setStyle("-fx-text-fill: White");
		HBox endArea = new HBox(10);
		endArea.setAlignment(Pos.CENTER);
		endArea.getChildren().addAll(toLabel, endDate);
		
		FlatButton refreshBtn = new FlatButton("Refresh");
		HBox dateArea = new HBox(20);
		dateArea.setAlignment(Pos.CENTER);
		dateArea.getChildren().addAll(startArea, endArea, refreshBtn);
		
		FlatButton viewDetails = new FlatButton("View Details");
		
		HBox bottom = new HBox(100);
		bottom.setAlignment(Pos.CENTER);
		bottom.getChildren().addAll(dateArea, viewDetails);
		
		ToolBar bottomBar = new ToolBar();
		bottomBar.setStyle("-fx-background-color: #074F76");
		bottomBar.getItems().add(bottom);
		
		
		//Adding functions to buttons
		refreshBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					LocalDate from = startDate.getValue();
					LocalDate to = endDate.getValue();
					if(to.isBefore(from)) {
						Alert al = new Alert(AlertType.ERROR, "The \"from\" date is before the \"to\" date", ButtonType.OK);
						al.showAndWait();
						return;
					}
					bills.getItems().clear();
					for(Bill b : c.getBills()) {
						if(b.getDateCreated().isBetween(from, to)) {
							bills.getItems().add(b);
						}
					}
					table.setContent(bills);
				}catch(NullPointerException ex) {
					Alert al = new Alert(AlertType.ERROR, "Please fill in the date fields", ButtonType.OK);
					al.showAndWait();
				}
			}
		});
		
		viewDetails.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					Bill b = bills.getSelectionModel().getSelectedItem();
					Stage billStage = new Stage();
					billStage.setTitle("Bill Data");
					billStage.getIcons().add(SharedElements.getIcon().getImage());
					billStage.initModality(Modality.APPLICATION_MODAL);
					billStage.setResizable(false);
					
					String billData = "";
					File file = new File("files" + File.separator + "bills" + File.separator +
							b.getOwner().getName() + File.separator + b.getId() + ".txt");
					try {
						Scanner in = new Scanner(file);
						while(in.hasNextLine()) {
							billData += in.nextLine();
							billData += "\n";
						}
						in.close();
					} catch (FileNotFoundException e) {
						Alert al = new Alert(AlertType.ERROR, "Cannot retrieve bill data", ButtonType.OK);
						al.showAndWait();
						e.printStackTrace();
					}
					
					ScrollPane main = new ScrollPane();
					main.setContent(new Label(billData));
					main.setPrefHeight(300);
					
					FlatButton okBtn = new FlatButton("Ok");
					okBtn.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent arg0) {
							billStage.close();
						}
					});
					
					VBox layout = new VBox(20);
					layout.setAlignment(Pos.CENTER);
					layout.getChildren().addAll(SharedElements.getHeader("Bill nr: " + b.getId(), 0, 60), main, okBtn);
					Scene billScene = new Scene(layout);
					billStage.setScene(billScene);
					billStage.showAndWait();
				} catch(NullPointerException ex) {
					Alert al = new Alert(AlertType.ERROR, "No Bill selected", ButtonType.OK);
					al.showAndWait();
				}
			}
		});
		
		BorderPane layout = new BorderPane();
		layout.setTop(SharedElements.getHeader(c.getName() + "'s Bills", 0, 60));
		layout.setCenter(table);
		layout.setBottom(bottomBar);
		
		Scene statsScene = new Scene(layout, 800, 500);
		statsStage.setResizable(false);
		statsStage.setScene(statsScene);
		statsStage.showAndWait();
	}
	
	public static void initialView(UserIO uio) {
		Stage initialstg = new Stage();
		initialstg.getIcons().add(SharedElements.getIcon().getImage());
		initialstg.initModality(Modality.APPLICATION_MODAL);
		initialstg.setTitle("Welcome");
		
		//initial Scene
		BorderPane layout = new BorderPane();
		VBox main = new VBox(20);
		main.setAlignment(Pos.CENTER);
		main.setStyle("-fx-background-color: #074F76");
		FlatButton nxtButton = new FlatButton("Next", 100, 50);
		nxtButton.setFont(new Font(20));
		main.getChildren().addAll(SharedElements.getIcon(), 
				SharedElements.getHeader("Welcome to Supermarket Software 1.0", 0, 0), nxtButton);
		layout.setCenter(main);
		Scene firstScene = new Scene(layout, 600, 300);
		
		
		//Adding Admin details Scene
		BorderPane addlayout = new BorderPane();
		addlayout.setTop(SharedElements.getHeader("Add Administrator Data", 0, 60));
		
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
		
		VBox dataArea = new VBox(35);
		dataArea.setAlignment(Pos.CENTER);
		dataArea.getChildren().addAll(nameArea, surnameArea, usernameArea, passwordArea, birthdayArea);
		addlayout.setCenter(dataArea);
		
		FlatButton nextButton = new FlatButton("Next", 100, 50);
		HBox bottom = new HBox();
		bottom.setAlignment(Pos.CENTER_RIGHT);
		bottom.setStyle("-fx-background-color: #074F76");
		bottom.getChildren().add(nextButton);
		addlayout.setBottom(bottom);
		
		Scene secondScene = new Scene(addlayout, 400, 400);
		secondScene.getStylesheets().add("style.css");
		
		nxtButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				initialstg.setScene(secondScene);
			}
		});
		
		nextButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					if(nameTField.getText().equals("") || surnameTField.getText().equals("") ||
							usernameTField.getText().equals("") || passwordTField.getText().equals("") || birthdayField.getValue().equals(null)) {
						Alert al = new Alert(AlertType.ERROR, "Please fill in all the data", ButtonType.OK);
						al.show();
					}else {
						Admin adm = new Admin(nameTField.getText(), surnameTField.getText(), usernameTField.getText(),
								passwordTField.getText(), new SimpleDate(birthdayField.getValue()));
						adm.setId(1);
						uio.addUser(adm);
						initialstg.close();
						return;
					}
				}catch (Exception ex) {
                	Alert al = new Alert(AlertType.ERROR, "Cannot process request", ButtonType.OK);
                	al.show();
				}
			}
		});
		
		initialstg.setScene(firstScene);
		main.requestFocus();
		initialstg.showAndWait();
	}
	
	public static void salesView() {
		SaleManager sm = new SaleManager();
		Stage saleStage = new Stage();
		saleStage.initModality(Modality.APPLICATION_MODAL);
		saleStage.setTitle("Sales");
		saleStage.getIcons().add(SharedElements.getIcon().getImage());
		
		BorderPane mainWindow = new BorderPane();
		mainWindow.setTop(SharedElements.getHeader("Financials", 0, 60));
		
		TableView<FinancialAction> tb = new TableView<FinancialAction>();
		tb.setPrefSize(1620, 1024);
		TableColumn<FinancialAction, String> column1 = new TableColumn<>("Date");
		column1.setCellValueFactory(new PropertyValueFactory<>("date"));
		TableColumn<FinancialAction, User> column2 = new TableColumn<>("Employee Salary");
		column2.setCellValueFactory(new PropertyValueFactory<>("actionUser"));
		TableColumn<FinancialAction, Product> column3 = new TableColumn<>("Product Expense / Income");
		column3.setCellValueFactory(new PropertyValueFactory<>("actionProduct"));
		TableColumn<FinancialAction, Integer> column4 = new TableColumn<>("Amount");
		column4.setCellValueFactory(new PropertyValueFactory<>("amount"));
		tb.getColumns().addAll(column1, column2, column3, column4);
		
		ScrollPane allDataPane = new ScrollPane();
		allDataPane.setContent(tb);
		
		float expenses = 0;
		float income = 0;
		float total = 0;
		
		for(FinancialAction fa : sm.getFinances()) {
			tb.getItems().add(fa);
			if(fa.getAmount() <= 0) expenses += fa.getAmount();
			if(fa.getAmount() > 0) income += fa.getAmount();
		}
		
		total = expenses + income;	
		
		
		DatePicker startDate = new DatePicker();
		Label fromLabel = new Label("From: ");
		fromLabel.setStyle("-fx-text-fill: White");
		HBox startArea = new HBox(10);
		startArea.setAlignment(Pos.CENTER);
		startArea.getChildren().addAll(fromLabel, startDate);
		
		DatePicker endDate = new DatePicker();
		Label toLabel = new Label("To: ");
		toLabel.setStyle("-fx-text-fill: White");
		HBox endArea = new HBox(10);
		endArea.setAlignment(Pos.CENTER);
		endArea.getChildren().addAll(toLabel, endDate);
		
		FlatButton refreshBtn = new FlatButton("Refresh");
		FlatButton detailsBtn = new FlatButton("View Details");
		
		HBox buttonArea = new HBox(25);
		buttonArea.setAlignment(Pos.CENTER);
		buttonArea.getChildren().addAll(startArea, endArea, refreshBtn, detailsBtn);
		
		HBox textArea = new HBox(40);
		textArea.setAlignment(Pos.CENTER);
		Label expenseLabel = new Label("Expenses: " + expenses + "$");
		expenseLabel.setFont(new Font(18));
		expenseLabel.setStyle("-fx-text-fill: Red");
		Label profitLabel = new Label("Profit: " + income + "$");
		profitLabel.setFont(new Font(18));
		profitLabel.setStyle("-fx-text-fill: LightGreen");
		Label netLabel = new Label("Net Income: " + total + "$");
		netLabel.setFont(new Font(18));
		netLabel.setStyle("-fx-text-fill: White");
		textArea.getChildren().addAll(expenseLabel, profitLabel, netLabel);
		
		VBox bottom = new VBox(20);
		bottom.setAlignment(Pos.CENTER);
		bottom.setStyle("-fx-background-color: #074F76");
		bottom.getChildren().addAll(buttonArea, textArea);

		mainWindow.setBottom(bottom);
		
		
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Month");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Net Income");
        
        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis); 
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<String, Number>();
        dataSeries.setName("Income for month");
        
        ScrollPane spane = new ScrollPane();
        spane.setContent(barChart);
        barChart.setPrefWidth(2000);
        barChart.setPrefHeight(600);
        barChart.setMinWidth(2000);
        Label placeHolder = new Label("Please fill-in the date fields to display the graph data");
        placeHolder.setFont(new Font(30));
        StackPane sp = new StackPane();
        sp.setAlignment(Pos.CENTER);
        sp.getChildren().addAll(spane, placeHolder);
        
		TabPane tp = new TabPane();
		tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		tp.getTabs().add(new Tab("Total Data", allDataPane));
		tp.getTabs().add(new Tab("Monthly Data", sp));
		
		mainWindow.setCenter(tp);
        
        
		//Adding functions to buttons
		refreshBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					LocalDate from = startDate.getValue();
					LocalDate to = endDate.getValue();
					if(to.isBefore(from)) {
						Alert al = new Alert(AlertType.ERROR, "The \"from\" date is before the \"to\" date", ButtonType.OK);
						al.showAndWait();
						return;
					}
					tb.getItems().clear();
					float expenses = 0;
					float income = 0;
					float total = 0;
					for(FinancialAction fa : sm.getFinances()) {
						if(fa.getDate().isBetween(from, to)) {
							tb.getItems().add(fa);
							if(fa.getAmount() <= 0) expenses += fa.getAmount();
							if(fa.getAmount() > 0) income += fa.getAmount();
						}
					}
					total = expenses + income;
					expenseLabel.setText("Expenses: " + expenses + "$");
					profitLabel.setText("Profit: " + income + "$");
					netLabel.setText("Net Income: " + total + "$");
					
					dataSeries.getData().clear();
					barChart.getData().clear();
					
					ArrayList<Integer> existingMonths = new ArrayList<Integer>();
					for(FinancialAction fa : sm.getFinances()) {
						if(fa.getDate().isBetween(from, to)) {
							if(!existingMonths.contains(fa.getDate().getMonth())) {
								XYChart.Series ds = new XYChart.Series();
								String mnth = fa.getDate().toLocalDate().getMonth().toString() + " " + fa.getDate().getYear();
								Float data = sm.getTotalForMonth(fa.getDate().getMonth(), fa.getDate().getYear());
								ds.getData().add(new XYChart.Data(mnth, data));
								barChart.getData().add(ds);
								existingMonths.add(fa.getDate().getMonth());		
							}
						}
					}
					placeHolder.setVisible(false);
				}catch(NullPointerException ex) {
					Alert al = new Alert(AlertType.ERROR, "Please fill in the date fields", ButtonType.OK);
					al.showAndWait();
				}
			}
		});
		
		detailsBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					FinancialAction fa = tb.getSelectionModel().getSelectedItem();
					Stage stg = new Stage();
					stg.initModality(Modality.APPLICATION_MODAL);
					stg.setTitle("Financial Detail");
					stg.setResizable(false);
					
					HBox sp = new HBox(20);
					sp.setAlignment(Pos.CENTER);
					sp.setStyle("-fx-background-color: #074F76");
					Label lb = new Label();
					lb.setFont(new Font(20));
					lb.setStyle("-fx-text-fill: White");
					if(fa.getAmount() < 0) {
						if(fa.getActionProduct() != null) {
							lb.setText("Bought " + fa.getActionProduct().getQuantity() + " of " + fa.getActionProduct().getName()
									+ " for " + fa.getActionProduct().getBuyingprice() + "$ each");
						}else {
							lb.setText("Paid salary of " + fa.getActionUser().getName() + " for " + " " 
						+ fa.getActionUser().getSalary() + "$" );
						}
					}else {
						lb.setText("Sold " + fa.getActionProduct().getQuantity() + " of " + fa.getActionProduct().getName()
								+ " for " + (fa.getActionProduct().getPrice()/fa.getActionProduct().getQuantity()) + "$ each");
					}
					FlatButton okBtn = new FlatButton("Ok");
					sp.getChildren().addAll(lb, okBtn);
					
					okBtn.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent arg0) {
							stg.close();
						}
					});
					
					Scene fscene = new Scene(sp, 600, 130);
					stg.setScene(fscene);
					stg.showAndWait();
				}catch(NullPointerException ex) {
					Alert al = new Alert(AlertType.ERROR, "No entry has been selected", ButtonType.OK);
					al.showAndWait();
				}
			}
		});
		
		
		Scene saleScene = new Scene(mainWindow, 800, 500);
		saleScene.getStylesheets().add("style.css");
		saleStage.setScene(saleScene);
		saleStage.showAndWait();
	}
	
	public static ImageView getIcon() {
		ImageView logoImg = new ImageView();
		logoImg.setFitWidth(150);
		logoImg.setFitHeight(150);
		logoImg.setPreserveRatio(true);
		logoImg.setImage(new Image(ResourceManager.logoloc.toString()));
		logoImg.setSmooth(true);
		return logoImg;
	}
	
	public static ImageView getSearchIcon() {
		ImageView searchImgView = new ImageView();
		searchImgView.setFitHeight(20);
		searchImgView.setFitWidth(20);
		searchImgView.setPreserveRatio(true);
		searchImgView.setImage(new Image(ResourceManager.searchloc.toString()));
		return searchImgView;
	}
	
	public static StackPane getHeader(String header, int width, int height) {
		StackPane sp = new StackPane();
		sp.setStyle("-fx-background-color: #074F76");
		sp.setAlignment(Pos.CENTER);
		if(width != 0) sp.setPrefWidth(width);
		if(height != 0) sp.setPrefHeight(height);
		Label lbl = new Label(header);
		lbl.setFont(new Font(20));
		lbl.setStyle("-fx-text-fill: White");
		
		sp.getChildren().add(lbl);
		return sp;
	}
}
