package application;

import java.io.File;

import data.ProductIO;
import employees.Cashier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import products.Product;
import util.FlatButton;
import util.SharedElements;

public class CashierStage {
	
	private ObservableList<Product> products = FXCollections.observableArrayList();
	private ObservableList<Product> billproducts = FXCollections.observableArrayList();
	private TableView productData;
	private TableView billData;
	
	public void view(Stage previousStage, Cashier cashier) {
		ProductIO pio = new ProductIO();
		
		Stage cashierStage = new Stage();
		cashierStage.getIcons().add(SharedElements.getIcon().getImage());
		SplitPane cashRegView = new SplitPane();
		BorderPane mainWindow = new BorderPane();
		ToolBar topBar = new ToolBar();
		topBar.setPrefHeight(85);
		topBar.setStyle("-fx-background-color: #074F76");
		mainWindow.setTop(topBar);
		
		//Setting up the Cash Register View
		BorderPane productsView = new BorderPane();
		refresh(pio);
		productData = viewProducts(pio);
		productsView.setCenter(productData);
		
		ToolBar cashRegButtonArea = new ToolBar();
		cashRegButtonArea.setStyle("-fx-background-color: #074F76");
		cashRegButtonArea.setPrefHeight(50);
		Label quantityLabel = new Label("Quantity: ");
		quantityLabel.setStyle("-fx-text-fill: White");
		TextField quantityTextField = new TextField();
		quantityTextField.getStyleClass().add("textfield");
		quantityTextField.setPromptText("Qty ... ");
		quantityTextField.setPrefWidth(50);
		HBox quantityArea = new HBox(15);
		quantityArea.setAlignment(Pos.CENTER);
		FlatButton addToBillButton = new FlatButton("Add");
		quantityArea.getChildren().addAll(quantityLabel, quantityTextField, addToBillButton);

		ImageView searchImgView = SharedElements.getSearchIcon();
		TextField searchTextField = new TextField();
		searchTextField.getStyleClass().add("textfield");
		searchTextField.setPromptText("Search ...");
		HBox searchArea = new HBox(15);
		searchArea.setAlignment(Pos.CENTER);
		searchArea.getChildren().addAll(searchImgView, searchTextField);
		
		HBox cashRegToolBar = new HBox(100);
		cashRegToolBar.setAlignment(Pos.CENTER);
		cashRegToolBar.getChildren().addAll(quantityArea, searchArea);
		cashRegButtonArea.getItems().add(cashRegToolBar);
		productsView.setBottom(cashRegButtonArea);
		
		
		//Setting up the Bill View
		BorderPane billView = new BorderPane();
		billView.setCenter(new Pane());
		billData = viewBillProducts();
		billView.setCenter(billData);
		
		
		//Setting up button's images
		ImageView cashRegisterIV = new ImageView();
		Image cashRegisterImg = new Image("resources" + File.separator + "cashregister.png");
		cashRegisterIV.setFitHeight(50);
		cashRegisterIV.setFitWidth(50);
		cashRegisterIV.setPreserveRatio(true);
		cashRegisterIV.setImage(cashRegisterImg);
		
		//Setting up buttons
		FlatButton cashRegisterButton = new FlatButton("Cash Register", cashRegisterIV);
		cashRegisterButton.setPrefSize(120, 85);
		
		//Adding functions to buttons
		cashRegisterButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mainWindow.setCenter(cashRegView);
			}
		});
		
		
		//Finishing the Layout
		cashRegView.getItems().addAll(productsView, billView);
		topBar.getItems().addAll(cashRegisterButton);
		
		Scene cashierScene = new Scene(mainWindow, 1024, 576);
		cashierScene.getStylesheets().add("style.css");
		cashierStage.setTitle("Cashier Window");
		cashierStage.setScene(cashierScene);
		mainWindow.requestFocus();
		cashierStage.show();
		
	}
	
	private TableView viewProducts(ProductIO pio) {
		
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
		productsTable.setPrefSize(1024, 491);
		
		return productsTable;
		
	}
	
	private TableView viewBillProducts() {
		
		TableView billProducts = new TableView();
		TableColumn<Product, String> column1 = new TableColumn<>("Name");
		column1.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<Product, Integer> column2 = new TableColumn<>("Quantity");
		column2.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		
		TableColumn<Product, Double> column3 = new TableColumn<>("Price");
		column3.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		billProducts.setItems(billproducts);
		billProducts.getColumns().addAll(column1, column2, column3);
		billProducts.setPrefSize(1024, 491);
		
		return billProducts;
		
	}
	
	private void refresh() {
		billproducts.clear();
		billData = viewBillProducts();
	}
	
	private void refresh(ProductIO pio) {
		products.clear();
		for(Product p : pio.getProducts()) {
			products.add(p);
		}
		productData = viewProducts(pio);
	}

}
