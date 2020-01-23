package application;

import java.io.File;
import java.util.ArrayList;

import data.ProductIO;
import data.UserIO;
import employees.Admin;
import employees.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import products.Product;
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
		ImageView userImg = new ImageView(new Image("resources" + File.separator + "man.png"));
		userImg.setFitHeight(50);
		userImg.setFitWidth(50);
		userImg.setPreserveRatio(true);
		
		ImageView productsImg = new ImageView(new Image("resources" + File.separator + "cart.png"));
		productsImg.setFitHeight(50);
		productsImg.setFitWidth(50);
		productsImg.setPreserveRatio(true);
		
		ImageView incomeImg = new ImageView(new Image("resources" + File.separator + "money.png"));
		incomeImg.setFitHeight(50);
		incomeImg.setFitWidth(50);
		incomeImg.setPreserveRatio(true);
		
		ImageView notifIV = new ImageView(new Image("resources" + File.separator + "alert.png"));
		notifIV.setFitHeight(50);
		notifIV.setFitWidth(50);
		notifIV.setPreserveRatio(true);
		
		ImageView logoutIV = new ImageView(new Image("resources" + File.separator + "logout.png"));
		logoutIV.setFitHeight(50);
		logoutIV.setFitWidth(50);
		logoutIV.setPreserveRatio(true);
		
		
		//Setting up the buttons for User View
		FlatButton usersButton = new FlatButton("Users", userImg);
		usersButton.setPrefSize(100, 85);
		FlatButton productsButton = new FlatButton("Products", productsImg);
		productsButton.setPrefSize(100, 85);
		FlatButton incomeButton = new FlatButton("Income", incomeImg);
		incomeButton.setPrefSize(100, 85);
		FlatButton notificationButton = new FlatButton("Notification", notifIV);
		notificationButton.setPrefSize(100, 85);
		FlatButton logOutButton = new FlatButton("Log Out", logoutIV);
		logOutButton.setPrefSize(120, 85);
		
		FlatButton addUserButton = new FlatButton("Add User");
		FlatButton editUserButton = new FlatButton("Edit User");
		FlatButton removeUserButton = new FlatButton("Remove User");
		FlatButton statsUserButton = new FlatButton("View Statistics");
		
		
		//Setting up buttons for Product View
		FlatButton addProductButton = new FlatButton("Add Product");
		FlatButton editProductButton = new FlatButton("Edit Product");
		FlatButton removeProductButton = new FlatButton("Remove Product");
		ToolBar productsTBar = new ToolBar();
		productsTBar.setStyle("-fx-background-color: #074F76");
		productsTBar.getItems().addAll(addProductButton, editProductButton, removeProductButton);

		
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
					al.show();
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
		
		
		//Adding functions to products related buttons
		addProductButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				SharedElements.addProductView(adminStage, pio);
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
		

		
		
		MenuBar menu = new MenuBar();
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
		menu.getMenus().add(logOut);
		
		topBar.getItems().addAll(usersButton, productsButton, incomeButton, notificationButton, logOutButton);
		VBox top = new VBox(menu, topBar);
		usersButtonTbar.getChildren().addAll(addUserButton, editUserButton, removeUserButton, statsUserButton);
		mainWindow.setTop(top);
		Scene adminScene = new Scene(mainWindow, 1024, 576);
		adminScene.getStylesheets().add("style.css");
		adminStage.setTitle("Admin Window");
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
		//nm.update();
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
		
		TableColumn<Product, Integer> column4 = new TableColumn<>("Price");
		column4.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		TableColumn<Product, Integer> column5 = new TableColumn<>("Barcode");
		column5.setCellValueFactory(new PropertyValueFactory<>("barcode"));
		
		productsTable.setItems(products);
		
		productsTable.getColumns().addAll(column1, column2, column3, column4, column5);
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
			products.add(p);
		}
		productData = viewProducts(pio);
	}
	
}
