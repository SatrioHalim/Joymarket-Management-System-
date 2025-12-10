package view;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Product;

public class ProductListView extends VBox{
	private TableView<Product> productTable;
    private TableColumn<Product, String> nameCol;
    private TableColumn<Product, Double> priceCol;
    private TableColumn<Product, Integer> stockCol;
    private TableColumn<Product, Void> actionCol;
    private TextField searchField;
    private Button searchBtn;
    private Button backBtn;
    
    public ProductListView() {
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #ffffff;");
        
        // Header dengan judul dan tombol back
        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setSpacing(15);
        
        backBtn = new Button("← Back");
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #333333; -fx-font-size: 14px;");
        
        Label title = new Label("Products");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));
        title.setStyle("-fx-text-fill: #333333;");
        
        headerBox.getChildren().addAll(backBtn, title);
        
        // Search bar
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        
        searchField = new TextField();
        searchField.setPromptText("Search products...");
        searchField.setPrefWidth(250);
        searchField.setPrefHeight(35);
        searchField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-width: 1px;");
        
        searchBtn = new Button("Search");
        searchBtn.setPrefHeight(35);
        searchBtn.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: #333333; -fx-border-color: #cccccc; -fx-border-width: 1px;");
        
        searchBox.getChildren().addAll(searchField, searchBtn);
        
        // Product Table
        productTable = new TableView<>();
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        productTable.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1px;");
        
        nameCol = new TableColumn<>("Product Name");
        nameCol.setPrefWidth(250);
        nameCol.setStyle("-fx-font-size: 13px;");
        
        priceCol = new TableColumn<>("Price");
        priceCol.setPrefWidth(100);
        priceCol.setStyle("-fx-font-size: 13px;");
        
        stockCol = new TableColumn<>("Stock");
        stockCol.setPrefWidth(100);
        stockCol.setStyle("-fx-font-size: 13px;");
        
        actionCol = new TableColumn<>("Add to Cart");
        actionCol.setPrefWidth(150);
        actionCol.setCellFactory(col -> new TableCell<Product, Void>() {
            private final TextField quantityField = new TextField();
            private final Button addBtn = new Button("Add");
            private final HBox container = new HBox(5, quantityField, addBtn);
            
            {
                quantityField.setPromptText("Qty");
                quantityField.setPrefWidth(60);
                quantityField.setText("1");
                quantityField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-width: 1px;");
                
                addBtn.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: #333333; -fx-border-color: #cccccc; -fx-border-width: 1px;");
                
                container.setAlignment(Pos.CENTER);
                
                addBtn.setOnAction(e -> {
                    Product product = getTableView().getItems().get(getIndex());
                    String qtyText = quantityField.getText();
                    // Handle add to cart logic (will be implemented in controller)
                });
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : container);
            }
        });
        
        productTable.getColumns().addAll(nameCol, priceCol, stockCol, actionCol);
        
        // Status message
        Label statusLabel = new Label("Enter quantity and click 'Add' to add items to cart");
        statusLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12px;");
        
        this.getChildren().addAll(headerBox, searchBox, productTable, statusLabel);
    }
    
    // Methods to populate table
    public void setProducts(ObservableList<Product> products) {
        productTable.setItems(products);
    }
    
    public void setStockWarning(int threshold) {
        stockCol.setCellFactory(col -> new TableCell<Product, Integer>() {
            @Override
            protected void updateItem(Integer stock, boolean empty) {
                super.updateItem(stock, empty);
                if (empty || stock == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(stock.toString());
                    if (stock <= threshold) {
                        setStyle("-fx-text-fill: #990000; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: #333333;");
                    }
                }
            }
        });
    }
    
    // Getters
    public TableView<Product> getProductTable() { return productTable; }
    public TextField getSearchField() { return searchField; }
    public Button getSearchBtn() { return searchBtn; }
    public Button getBackBtn() { return backBtn; }
}
