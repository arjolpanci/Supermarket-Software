package application;

import java.util.ArrayList;

import data.UserIO;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import util.FlatButton;
import util.SharedElements;

public class AdminStage {
	
	private ObservableList<User> users = FXCollections.observableArrayList();
	private TableView data;
	
	public void view(Stage previousStage) {
		UserIO uio = new UserIO();
		
		//Setting up the layout
		Stage adminStage = new Stage();
		adminStage.getIcons().add(SharedElements.getIcon().getImage());
		ScrollPane mainPane = new ScrollPane();
		BorderPane mainWindow = new BorderPane();
		mainWindow.setCenter(mainPane);
		ToolBar topBar = new ToolBar();
		topBar.setPrefHeight(85);
		topBar.setStyle("-fx-background-color: #074F76");
		
		HBox tbarButtonArea = new HBox(20);
		ToolBar usersbottomBar = new ToolBar();
		usersbottomBar.getItems().add(tbarButtonArea);
		
		//Setting up the button's images
		ImageView userImg = new ImageView(new Image("resources\\user.png"));
		userImg.setFitHeight(50);
		userImg.setFitWidth(50);
		userImg.setPreserveRatio(true);
		
		ImageView productsImg = new ImageView(new Image("resources\\products.png"));
		productsImg.setFitHeight(50);
		productsImg.setFitWidth(50);
		productsImg.setPreserveRatio(true);
		
		ImageView incomeImg = new ImageView(new Image("resources\\income.png"));
		incomeImg.setFitHeight(50);
		incomeImg.setFitWidth(50);
		incomeImg.setPreserveRatio(true);
		
		//Setting up the buttons
		FlatButton usersButton = new FlatButton("Users", userImg);
		usersButton.setPrefSize(100, 85);
		FlatButton productsButton = new FlatButton("Products", productsImg);
		productsButton.setPrefSize(100, 85);
		FlatButton incomeButton = new FlatButton("Income", incomeImg);
		incomeButton.setPrefSize(100, 85);
		
		FlatButton addUserButton = new FlatButton("Add User");
		FlatButton editUserButton = new FlatButton("Edit User");
		FlatButton removeUserButton = new FlatButton("Remove User");
		FlatButton statsUserButton = new FlatButton("View Statistics");

		
		
		//TableView tview = viewUsers(uio);
		data = viewUsers(uio);
		//Adding functions to buttons
		usersButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				mainWindow.setBottom(usersbottomBar);
				refresh(uio);
				mainPane.setContent(data);
			}
		});
		
		addUserButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				SharedElements.addUserView(adminStage, uio);
				refresh(uio);
				mainPane.setContent(data);
			}
		});
		
		editUserButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					SharedElements.editUserView(adminStage, (User) data.getSelectionModel().getSelectedItem(), uio);
					refresh(uio);
					mainPane.setContent(data);
				} catch (NullPointerException ex) {
					Alert al = new Alert(AlertType.ERROR, "No user selected", ButtonType.OK);
					al.show();
				}
			}
		});
		
		topBar.getItems().addAll(usersButton, productsButton , incomeButton);
		tbarButtonArea.getChildren().addAll(addUserButton, editUserButton, removeUserButton, statsUserButton);
		mainWindow.setTop(topBar);
		Scene adminScene = new Scene(mainWindow, 1024, 576);
		adminStage.setTitle("Admin Window");
		adminStage.setResizable(false);
		adminStage.setScene(adminScene);
		adminStage.show();
		
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
       
        usersTable.setItems(users);
        
        usersTable.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7);
        usersTable.setPlaceholder(new Label("No user data to display"));
        usersTable.setPrefSize(1024, 491);
		
		return usersTable;
	}
	
	private void refresh(UserIO uio) {
		users.clear();
		for(User u : uio.getUsers()) {
			users.add(u);
		}
		data = viewUsers(uio);
	}
	
}
