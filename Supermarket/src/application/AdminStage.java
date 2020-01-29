package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import data.ProductIO;
import data.UserIO;
import employees.Admin;
import employees.Cashier;
import employees.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import products.Product;
import resources.ResourceManager;
import util.FlatButton;
import util.Notification;
import util.NotificationManager;
import util.SharedElements;

public class AdminStage {
	
	private ObservableList<User> users = FXCollections.observableArrayList();
	private ObservableList<Product> products = FXCollections.observableArrayList();
	private TableView userData;
	private TableView productData;
	
	public void view(Stage previousStage, Admin adm) {
		UserIO uio = new UserIO();
		ProductIO pio = new ProductIO();
		NotificationManager nm = new NotificationManager();
		
		//Setting up the layout
		Stage adminStage = new Stage();
		adminStage.getIcons().add(SharedElements.getIcon().getImage());
		ScrollPane mainPane = new ScrollPane();
		mainPane.setStyle("-fx-background-color: #032030");
		BorderPane mainWindow = new BorderPane();
		mainWindow.setCenter(mainPane);
		ToolBar topBar = new ToolBar();
		topBar.setPrefHeight(85);
		topBar.setStyle("-fx-background-color: #074F76");
		
		HBox usersButtonTbar = new HBox(20);
		ToolBar usersbottomBar = new ToolBar();
		usersbottomBar.setStyle("-fx-background-color: #074F76");
		usersbottomBar.getItems().add(usersButtonTbar);
		
		//Setting up the button's images for top toolbar
		ImageView userImg = new ImageView(new Image(ResourceManager.manloc.toString()));
		userImg.setFitHeight(50);
		userImg.setFitWidth(50);
		userImg.setPreserveRatio(true);
		userImg.setSmooth(true);
		
		ImageView productsImg = new ImageView(new Image(ResourceManager.cartloc.toString()));
		productsImg.setFitHeight(50);
		productsImg.setFitWidth(50);
		productsImg.setPreserveRatio(true);
		productsImg.setSmooth(true);
		
		ImageView incomeImg = new ImageView(new Image(ResourceManager.moneyloc.toString()));
		incomeImg.setFitHeight(50);
		incomeImg.setFitWidth(50);
		incomeImg.setPreserveRatio(true);
		incomeImg.setSmooth(true);
		
		ImageView notifIV = new ImageView(new Image(ResourceManager.notificationloc.toString()));
		notifIV.setFitHeight(50);
		notifIV.setFitWidth(50);
		notifIV.setPreserveRatio(true);
		notifIV.setSmooth(true);
		
		ImageView logoutIV = new ImageView(new Image(ResourceManager.logoutloc.toString()));
		logoutIV.setFitHeight(50);
		logoutIV.setFitWidth(50);
		logoutIV.setPreserveRatio(true);
		logoutIV.setSmooth(true);
		
		
		//Setting up the buttons for User View
		FlatButton usersButton = new FlatButton("Users", userImg);
		usersButton.setPrefSize(120, 85);
		FlatButton productsButton = new FlatButton("Products", productsImg);
		productsButton.setPrefSize(120, 85);
		FlatButton incomeButton = new FlatButton("Income", incomeImg);
		incomeButton.setPrefSize(120, 85);
		FlatButton notificationButton = new FlatButton("Notifications", notifIV);
		notificationButton.setPrefSize(120, 85);
		FlatButton logOutButton = new FlatButton("Log Out", logoutIV);
		logOutButton.setPrefSize(120, 85);
		
		FlatButton addUserButton = new FlatButton("Add User");
		FlatButton editUserButton = new FlatButton("Edit User");
		FlatButton removeUserButton = new FlatButton("Remove User");
		FlatButton statsUserButton = new FlatButton("View Bills");
		ImageView userSearchIV = SharedElements.getSearchIcon();
		userSearchIV.setSmooth(true);
		TextField userSearchField = new TextField();
		userSearchField.getStyleClass().add("textfield");
		userSearchField.setPromptText("Search ...");
		HBox userSearchArea = new HBox(15);
		userSearchArea.setAlignment(Pos.CENTER);
		userSearchArea.getChildren().addAll(userSearchIV, userSearchField);
		usersButtonTbar.getChildren().addAll(addUserButton, editUserButton, removeUserButton, statsUserButton, userSearchArea);
	
		
		//Setting up buttons for Product View
		FlatButton addProductButton = new FlatButton("Add Product");
		FlatButton addSupplierButton = new FlatButton("Add Supplier");
		FlatButton viewSupplierButton = new FlatButton("View Suppliers");
		FlatButton editProductButton = new FlatButton("Edit Product");
		FlatButton removeProductButton = new FlatButton("Remove Product");
		ImageView productSearchIV = SharedElements.getSearchIcon();
		productSearchIV.setSmooth(true);
		TextField productSearchField = new TextField();
		productSearchField.getStyleClass().add("textfield");
		productSearchField.setPromptText("Search ...");
		HBox productSearchArea = new HBox(15);
		productSearchArea.setAlignment(Pos.CENTER);
		productSearchArea.getChildren().addAll(productSearchIV, productSearchField);
		ToolBar productsTBar = new ToolBar();
		productsTBar.setStyle("-fx-background-color: #074F76");
		productsTBar.getItems().addAll(addProductButton, addSupplierButton, viewSupplierButton, editProductButton, removeProductButton, productSearchArea);
		
		userData = viewUsers(uio);
		productData = viewProducts(pio);
		
		
		//Adding top toolbar button's functions
		usersButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				mainWindow.setBottom(usersbottomBar);
				refresh(uio);
				mainPane.setContent(userData);
			}
		});
		
		productsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				mainWindow.setBottom(productsTBar);
				refresh(pio);
				mainPane.setContent(productData);
			}
		});
		
		notificationButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				SharedElements.notificationView(adm, nm);
			}
		});
		
		incomeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				SharedElements.salesView();
			}
		});
		
		logOutButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				adminStage.close();
				LoginStage lgs = new LoginStage();
				lgs.view(adminStage, uio);
			}
		});
		
		//Adding functions to User related buttons
		addUserButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				SharedElements.addUserView(adminStage, uio);
				refresh(uio);
				mainPane.setContent(userData);
			}
		});
		
		editUserButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					SharedElements.editUserView(adminStage, (User) userData.getSelectionModel().getSelectedItem(), uio);
					refresh(uio);
					mainPane.setContent(userData);
				} catch (NullPointerException ex) {
					Alert al = new Alert(AlertType.ERROR, "No user selected", ButtonType.OK);
					al.showAndWait();
				}
			}
		});
		
		statsUserButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					User u = (User) userData.getSelectionModel().getSelectedItem();
					if(u instanceof Cashier) {
						SharedElements.viewStatistics((Cashier) u);
					}else {
						Alert al = new Alert(AlertType.WARNING, "No data is available for that user type", ButtonType.OK);
						al.showAndWait();
					}
				} catch (NullPointerException ex) {
					Alert al = new Alert(AlertType.ERROR, "No user selected", ButtonType.OK);
					al.showAndWait();
				}
			}
		});
		
		removeUserButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					User u = (User) userData.getSelectionModel().getSelectedItem();
					if(u instanceof Admin && uio.getAdminsCount() <= 1) {
						Alert al = new Alert(AlertType.ERROR, "Cannot delete the only admin", ButtonType.OK);
						al.show();
						return;
					}
					uio.removeUser(u);
					refresh(uio);
					mainPane.setContent(userData);
				} catch (NullPointerException ex) {
					Alert al = new Alert(AlertType.ERROR, "No user selected", ButtonType.OK);
					al.show();
				}
			}
		});
		
		//Search Field Events
		productSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.equals("")) {
				refresh(pio);
				mainPane.setContent(productData);
			}else {
				products.clear();
				for(Product p : pio.getProducts()) {
					if(p.getName().contains(newValue) || p.getSupplier().contains(newValue) 
							|| String.valueOf(p.getBarcode()).contains(newValue)) {
						if(!(p.getName().equals("RESERVED"))) products.add(p);
						mainPane.setContent(productData);
					}
				}
				productData.setItems(products);
			}
		});
		
		userSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.equals("")) {
				refresh(uio);
				mainPane.setContent(userData);
			}else {
				users.clear();
				for(User u : uio.getUsers()) {
					if(u.getName().contains(newValue)) {
						users.add(u);
						mainPane.setContent(userData);
					}
				}
				userData.setItems(users);
			}
		});
		
		
		//Adding functions to products related buttons
		addProductButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				SharedElements.addProductView(adminStage, pio);
				refresh(pio);
				mainPane.setContent(productData);
			}
		});
		
		addSupplierButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				SharedElements.addSupplierView(pio);
				refresh(pio);
				mainPane.setContent(productData);
			}
		});
		
		viewSupplierButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				SharedElements.viewSuppliers(pio);
				refresh(pio);
				mainPane.setContent(productData);
			}
		});
		
		editProductButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					Product p = (Product) productData.getSelectionModel().getSelectedItem();
					SharedElements.editProductView(previousStage, pio, p);
					refresh(pio);
					mainPane.setContent(productData);
				} catch (NullPointerException ex) {
					Alert al = new Alert(AlertType.ERROR, "No product selected", ButtonType.OK);
					al.show();
				}
			}
		});
		
		removeProductButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Product p = (Product) productData.getSelectionModel().getSelectedItem();
				pio.removeProduct(p);
				pio.update();
				refresh(pio);
				mainPane.setContent(productData);
			}
		});
		
		
		//Menu Stuff
		MenuBar menu = new MenuBar();
		Menu file = new Menu("File");
		MenuItem openFiles = new MenuItem("Data Files");
		openFiles.setOnAction(e -> {
			try {
				Desktop.getDesktop().open(new File("files"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		MenuItem refresh = new MenuItem("Refresh");
		refresh.setOnAction(e -> {
			adminStage.close();
			AdminStage as = new AdminStage();
			as.view(previousStage, adm);
		});
		
		MenuItem quit = new MenuItem("Exit");
		quit.setOnAction(e -> {
			adminStage.close();
		});
		
		file.getItems().addAll(openFiles, refresh, quit);
		
		Menu help = new Menu("Help");
		MenuItem about = new MenuItem("About");
		about.setOnAction(e -> {
			Stage aboutstg = new Stage();
			VBox layout = new VBox(10);
			layout.setStyle("-fx-background-color: #074F76");
			layout.setAlignment(Pos.CENTER);
			
			Label txt = new Label("This application has been developed");
			txt.setStyle("-fx-text-fill: White");
			txt.setFont(new Font(16));
			Label txt2 = new Label("by Arjol Panci as a University");
			txt2.setStyle("-fx-text-fill: White");
			txt2.setFont(new Font(16));
			Label txt3 = new Label("project using Java / JavaFX.");
			txt3.setStyle("-fx-text-fill: White");
			txt3.setFont(new Font(16));
			layout.getChildren().addAll(SharedElements.getIcon(), txt, txt2, txt3);
			
			Scene aboutsc = new Scene(layout, 300, 300);
			aboutstg.setTitle("About");
			aboutstg.getIcons().add(SharedElements.getIcon().getImage());
			aboutstg.setScene(aboutsc);
			aboutstg.show();
		});
		help.getItems().add(about);
		
		Menu logOut = new Menu();
		Label logOutLabel = new Label("Log Out");
		logOutLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				adminStage.close();
				LoginStage lgs = new LoginStage();
				lgs.view(previousStage, uio);
			}
		});
		logOut.setGraphic(logOutLabel);
		menu.getMenus().addAll(file, help, logOut);
		
		
		topBar.getItems().addAll(usersButton, productsButton, incomeButton, notificationButton, logOutButton);
		VBox top = new VBox(menu, topBar);
		mainWindow.setTop(top);
		Scene adminScene = new Scene(mainWindow, 1152, 648);
		adminScene.getStylesheets().add("style.css");
		adminStage.setTitle("Admin Window ( " + adm.getName() + " )");
		adminStage.setScene(adminScene);
		mainWindow.requestFocus();
		adminStage.show();
		
		ArrayList<Notification> toRemove = new ArrayList<Notification>();
		for(Notification n : nm.getNotifications()) {
			if(n.getReciever().equals("Administrator")) {
				n.show(nm, toRemove);
			}
		}
		for(Notification n : toRemove) {
			nm.removeNotification(n);
			nm.update();
		}
	}
	
	private TableView viewUsers(UserIO uio) {
		
		TableView usersTable = new TableView();
		TableColumn<User, Integer> column1 = new TableColumn<>("Id");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn<User, String> column2 = new TableColumn<>("User Type");
        column2.setCellValueFactory(new PropertyValueFactory<>("usertype"));
		
        TableColumn<User, String> column3 = new TableColumn<>("First Name");
        column3.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<User, String> column4 = new TableColumn<>("Last Name");
        column4.setCellValueFactory(new PropertyValueFactory<>("surname"));
        
        TableColumn<User, String> column5 = new TableColumn<>("Username");
        column5.setCellValueFactory(new PropertyValueFactory<>("username"));
        
        TableColumn<User, String> column6 = new TableColumn<>("Password");
        column6.setCellValueFactory(new PropertyValueFactory<>("password"));
        
        TableColumn<User, String> column7 = new TableColumn<>("Birthday");
        column7.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        
        TableColumn<User, String> column8 = new TableColumn<>("Salary");
        column8.setCellValueFactory(new PropertyValueFactory<>("salary"));
       
        usersTable.setItems(users);
        
        usersTable.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7, column8);
        usersTable.setPlaceholder(new Label("No user data to display"));
        usersTable.setPrefSize(1600, 1200);
        
		return usersTable;
		
	}
	
	private TableView viewProducts(ProductIO uio) {
	
		TableView productsTable = new TableView();
		TableColumn<Product, String> column1 = new TableColumn<>("Name");
		column1.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<Product, String> column2 = new TableColumn<>("Supplier");
		column2.setCellValueFactory(new PropertyValueFactory<>("supplier"));
		
		TableColumn<Product, Integer> column3 = new TableColumn<>("Quantity");
		column3.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		
		TableColumn<Product, Integer> column4 = new TableColumn<>("Product Price");
		column4.setCellValueFactory(new PropertyValueFactory<>("buyingprice"));
		
		TableColumn<Product, Integer> column5 = new TableColumn<>("Selling Price");
		column5.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		TableColumn<Product, Integer> column6 = new TableColumn<>("Barcode");
		column6.setCellValueFactory(new PropertyValueFactory<>("barcode"));
		
		TableColumn<Product, String> column7 = new TableColumn<>("Expire Date");
		column7.setCellValueFactory(new PropertyValueFactory<>("expireDate"));
		
		productsTable.setItems(products);
		
		productsTable.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7);
		productsTable.setPlaceholder(new Label("No products data to display"));
		productsTable.setPrefSize(1600, 1200);
		
		return productsTable;
		
	}
	
	private void refresh(UserIO uio) {
		users.clear();
		for(User u : uio.getUsers()) {
			users.add(u);
		}
		userData = viewUsers(uio);
	}
	
	private void refresh(ProductIO pio) {
		products.clear();
		for(Product p : pio.getProducts()) {
			if(!p.getName().equals("RESERVED")) products.add(p);
		}
		productData = viewProducts(pio);
	}
	
}
