package view;

import java.util.function.BiConsumer;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Product;

public class ProductListView extends VBox {
    private TableView<Product> productTable;
    private TableColumn<Product, String> nameCol;
    private TableColumn<Product, Double> priceCol;
    private TableColumn<Product, Integer> stockCol;
    private TableColumn<Product, Void> actionCol;
    private TextField searchField;
    private Button searchBtn;
    private Button backBtn;
    private ObservableList<Product> products = FXCollections.observableArrayList();
    private BiConsumer<Product, String> addToCartHandler;
    
    public ProductListView() {
        initializeUI();
    }
    
    private void initializeUI() {
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #ffffff;");
        
        setupHeader();
        setupSearchBar();
        setupProductTable();
        setupStatusLabel();
    }
    
    private void setupHeader() {
        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setSpacing(15);
        
        backBtn = new Button("← Back");
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #333333; -fx-font-size: 14px;");
        
        Label title = new Label("Products");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));
        title.setStyle("-fx-text-fill: #333333;");
        
        headerBox.getChildren().addAll(backBtn, title);
        this.getChildren().add(headerBox);
    }
    
    private void setupSearchBar() {
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
        this.getChildren().add(searchBox);
    }
    
    private void setupProductTable() {
        productTable = new TableView<>();
        productTable.setItems(products);
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        productTable.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1px;");
        
        initializeTableColumns();
        productTable.getColumns().addAll(nameCol, priceCol, stockCol, actionCol);
        
        this.getChildren().add(productTable);
    }
    
    private void initializeTableColumns() {
        nameCol = new TableColumn<>("Product Name");
        priceCol = new TableColumn<>("Price ($)");
        stockCol = new TableColumn<>("Stock");
        actionCol = new TableColumn<>("Add to Cart");
        
        setupColumnProperties();
        setupActionColumn();
    }
    
    private void setupColumnProperties() {
        nameCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getName()));
        
        priceCol.setCellValueFactory(cellData -> 
            new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        
        stockCol.setCellValueFactory(cellData -> 
            new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        
        nameCol.setPrefWidth(250);
        priceCol.setPrefWidth(100);
        stockCol.setPrefWidth(100);
        actionCol.setPrefWidth(150);
    }
    
    private void setupActionColumn() {
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
                
                addBtn.setOnAction(e -> handleAddToCart());
            }
            
            private void handleAddToCart() {
                Product product = getTableView().getItems().get(getIndex());
                String quantity = quantityField.getText().trim();
                
                if (addToCartHandler != null) {
                    addToCartHandler.accept(product, quantity);
                } else {
                    showDefaultAlert("Error", "Cart handler not set", Alert.AlertType.ERROR);
                }
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : container);
            }
        });
    }
    
    private void setupStatusLabel() {
        Label statusLabel = new Label("Enter quantity and click 'Add' to add items to cart");
        statusLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12px;");
        this.getChildren().add(statusLabel);
    }
    
    private void showDefaultAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    // Public API methods
    public void setProducts(ObservableList<Product> products) {
        this.products.setAll(products);
        productTable.refresh();
    }
    
    public void setAddToCartHandler(BiConsumer<Product, String> handler) {
        this.addToCartHandler = handler;
    }
    
    public void clearProducts() {
        products.clear();
        productTable.setItems(products);
    }
    
    // Getters
    public TableView<Product> getProductTable() { return productTable; }
    public TextField getSearchField() { return searchField; }
    public Button getSearchBtn() { return searchBtn; }
    public Button getBackBtn() { return backBtn; }
    public ObservableList<Product> getProducts() { return products; }
}