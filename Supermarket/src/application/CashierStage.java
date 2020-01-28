package application;

import java.time.LocalDate;
import java.util.ArrayList;

import data.Bill;
import data.ProductIO;
import data.UserIO;
import employees.Cashier;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import products.Product;
import resources.ResourceManager;
import util.FlatButton;
import util.NotEnoughQuantityException;
import util.NumOnlyField;
import util.SharedElements;
import util.SimpleDate;

public class CashierStage {
	
	private ObservableList<Product> products = FXCollections.observableArrayList();
	private ObservableList<Product> billproducts = FXCollections.observableArrayList();
	private TableView<Product> productData;
	private TableView<Product> billData;
	private float total = 0;
	private ArrayList<Product> saved = new ArrayList<Product>();

	public void view(Stage previousStage, Cashier cashier, UserIO uio) {
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
		searchImgView.setSmooth(true);
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
		billData = viewBillProducts();
		billView.setCenter(billData);

		Label totalLabel = new Label("Total: " + total);
		totalLabel.setFont(new Font(22));
		totalLabel.setStyle("-fx-text-fill: White");

		Label givenLabel = new Label("Given:   ");
		givenLabel.setStyle("-fx-text-fill: White");
		NumOnlyField givenTField = new NumOnlyField();
		givenTField.getStyleClass().add("textfield");
		givenTField.setPromptText("Given Money ...");
		HBox givenArea = new HBox(20);
		givenArea.setAlignment(Pos.CENTER);
		givenArea.getChildren().addAll(givenLabel, givenTField);
		
		Label changeLabel = new Label("Change: 0.0");
		changeLabel.setStyle("-fx-text-fill: White");
		changeLabel.setFont(new Font(16));
		
		FlatButton printButton = new FlatButton("Print");
		FlatButton deleteButton = new FlatButton("Delete");
		FlatButton clearButton = new FlatButton("Clear");
		HBox buttonArea = new HBox(15);
		buttonArea.setAlignment(Pos.CENTER);
		buttonArea.getChildren().addAll(printButton, deleteButton, clearButton);
		
		VBox billLayout = new VBox(20);
		billLayout.setPrefHeight(150);
		billLayout.setAlignment(Pos.CENTER);
		billLayout.setStyle("-fx-background-color: #074F76");
		billLayout.getChildren().addAll(totalLabel, givenArea, changeLabel, buttonArea);
		billView.setBottom(billLayout);
		
		//Setting up button's images
		ImageView cashRegisterIV = new ImageView();
		Image cashRegisterImg = new Image(ResourceManager.cashregisterloc.toString());
		cashRegisterIV.setFitHeight(50);
		cashRegisterIV.setFitWidth(50);
		cashRegisterIV.setPreserveRatio(true);
		cashRegisterIV.setImage(cashRegisterImg);
		cashRegisterIV.setSmooth(true);

		ImageView logoutIV = new ImageView();
		logoutIV.setFitHeight(50);
		logoutIV.setFitWidth(50);
		logoutIV.setPreserveRatio(true);
		logoutIV.setImage(new Image(ResourceManager.logoutloc.toString()));
		logoutIV.setSmooth(true);
		
		//Setting up the main buttons
		FlatButton cashRegisterButton = new FlatButton("Cash Register", cashRegisterIV);
		cashRegisterButton.setPrefSize(120, 85);
		FlatButton logOutButton = new FlatButton("Log Out", logoutIV);
		logOutButton.setPrefSize(120, 85);
		
		searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.equals("")) {
				products.clear();
				refresh(pio);
				productsView.setCenter(productData);
			}else {
				products.clear();
				for(Product p : pio.getProducts()) {
					if(p.getName().contains(newValue)) {
						products.add(p);
						productsView.setCenter(productData);
					}
				}
				productData.setItems(products);
			}
		});
		
		givenTField.textProperty().addListener((observable, oldValue, newValue) -> {
			float given = 0;
			if(!newValue.equals("")) given = Float.parseFloat(newValue);
			float change = given - total;
			System.out.println(change);
			changeLabel.setText("Change " + change);
		});
		
		//Adding functions to buttons
		cashRegisterButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mainWindow.setCenter(cashRegView);
			}
		});

		logOutButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!billData.getItems().isEmpty()){
					for(Product p : saved){
						pio.reAdd(p);
					}
					saved.clear();
					billproducts.clear();
					products.clear();
				}
				cashierStage.close();
				LoginStage lgs = new LoginStage();
				lgs.view(cashierStage, uio);
			}
		});
		
		addToBillButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					int qty = Integer.parseInt(quantityTextField.getText());
					Product pr = productData.getSelectionModel().getSelectedItem();
					
					boolean flag = false;
					ArrayList<Product> tmp = new ArrayList<Product>();
					ArrayList<Product> toAdd = new ArrayList<Product>();
					for(Product p : billproducts) {
						if(p.getName().equals(pr.getName())) {
							pr.updateQuantity(qty, pio);
							refresh(pio);
							productsView.setCenter(productData);
							int nqty = p.getQuantity() + qty;
							tmp.add(p);
							Product newpr = new Product(pr.getName(), pr.getSupplier(), 
									nqty, pr.getBuyingprice(), pr.getPriceForQuantity(nqty), pr.getBarcode(), new SimpleDate(pr.getExpireDate()));
							toAdd.add(newpr);
							refresh();
							billView.setCenter(billData);
							flag = true;
						}
					}
					
					if(!flag) {
						try {
							saved.add((Product) pr.clone());
						} catch (CloneNotSupportedException e) {
							e.printStackTrace();
						}	
					}
					
					for(Product p : tmp) {
						billproducts.remove(p);
					}
					for(Product p : toAdd) {
						billproducts.add(p);
					}
					if(!flag) {
						pr.updateQuantity(qty, pio);
						refresh(pio);
						productsView.setCenter(productData);
						Product newpr = new Product(pr.getName(), pr.getSupplier(),
								qty, pr.getBuyingprice(), pr.getPriceForQuantity(qty), pr.getBarcode(), new SimpleDate(pr.getExpireDate()));
						billproducts.add(newpr);
						refresh();
						billView.setCenter(billData);
					}
					total = 0;
					for(Product p : billproducts){
						total += p.getPrice();
					}
					totalLabel.setText("Total: " + total);
					
				}catch (NullPointerException ex) {
					Alert al = new Alert(AlertType.ERROR, "Cannot process request", ButtonType.OK);
					al.setTitle("Error");
					al.showAndWait();
				} catch (NotEnoughQuantityException e) {
					e.printStackTrace();
				}
			}
		});

		printButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ArrayList<Product> prdcts = new ArrayList<Product>();
				for(Product p : billproducts){
					prdcts.add(p);
				}
				Bill newBill = new Bill(prdcts, total, cashier);
				cashier.addBill(newBill);
				uio.update();
				newBill.toFile();
				billproducts.clear();
				refresh();
			}
		});
		
		clearButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(!billData.getItems().isEmpty()){
					for(Product p : saved){
						pio.reAdd(p);
					}
					saved.clear();
					billproducts.clear();
					products.clear();
					refresh();
					refresh(pio);
					productData = viewProducts(pio);
					productsView.setCenter(productData);
					totalLabel.setText("Total: 0");
				}
			}
		});
		
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(!billData.getItems().isEmpty()){
					for(Product p : saved){
						pio.reAdd(p);
					}
					Product pr = billData.getSelectionModel().getSelectedItem();
					/**saved.clear();
					ArrayList<Product> tmp = new ArrayList<Product>();					
					for(Product p : saved) {
						if(p.getName().equals(pr)) {
							tmp.add(p);
						}
					}
					for(Product p : tmp) {
						saved.remove(p);
					}*/
					billproducts.remove(pr);
					refresh();
					products.clear();
					refresh(pio);
					productData = viewProducts(pio);
					productsView.setCenter(productData);
					float total = 0;
					for(Product p : billproducts) {
						total += p.getPrice();
					}
					totalLabel.setText("Total: " + total);
				}
			}
		});
		
		
		//Finishing the Layout
		cashRegView.getItems().addAll(productsView, billView);
		topBar.getItems().addAll(cashRegisterButton, logOutButton);
		
		Scene cashierScene = new Scene(mainWindow, 1024, 576);
		cashierScene.getStylesheets().add("style.css");
		cashierStage.setTitle("Cashier Window " + " ( " + cashier.getName() + " )");
		cashierStage.setScene(cashierScene);
		mainWindow.requestFocus();
		cashierStage.show();
		
	}
	
	private TableView<Product> viewProducts(ProductIO pio) {
		
		TableView<Product> productsTable = new TableView<Product>();
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
		billProducts.setPlaceholder(new Label("Add products to fill the bill"));
		
		return billProducts;
		
	}
	
	private void refresh() {
		billData = viewBillProducts();
	}
	
	private void refresh(ProductIO pio) {
		products.clear();
		for(Product p : pio.getProducts()) {
			if(p.getQuantity() != 0 && p.getExpireDate().isAfter(LocalDate.now())) {
				products.add(p);
			}
		}
		productData = viewProducts(pio);
	}

}
