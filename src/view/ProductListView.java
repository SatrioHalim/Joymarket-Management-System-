package view;

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
    
    public ProductListView() {
        System.out.println("ProductListView constructor called");
        
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #ffffff;");
        
        // Header
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
        productTable.setItems(products);
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        productTable.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1px;");
        
        // Initialize columns
        nameCol = new TableColumn<>("Product Name");
        priceCol = new TableColumn<>("Price ($)");
        stockCol = new TableColumn<>("Stock");
        actionCol = new TableColumn<>("Add to Cart");
        
        // Setup columns
        setupTableColumns();
        
        productTable.getColumns().addAll(nameCol, priceCol, stockCol, actionCol);
        
        // Status message
        Label statusLabel = new Label("Enter quantity and click 'Add' to add items to cart");
        statusLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12px;");
        
        this.getChildren().addAll(headerBox, searchBox, productTable, statusLabel);
        
        System.out.println("ProductListView initialized successfully");
    }
    
    private void setupTableColumns() {
        System.out.println("Setting up table columns...");
        
        try {
            // Gunakan SimpleProperty untuk menghindari error PropertyValueFactory
            nameCol.setCellValueFactory(cellData -> {
                Product product = cellData.getValue();
                return new SimpleStringProperty(product.getName());
            });
            
            priceCol.setCellValueFactory(cellData -> {
                Product product = cellData.getValue();
                return new SimpleDoubleProperty(product.getPrice()).asObject();
            });
            
            stockCol.setCellValueFactory(cellData -> {
                Product product = cellData.getValue();
                return new SimpleIntegerProperty(product.getStock()).asObject();
            });
            
            // Set widths
            nameCol.setPrefWidth(250);
            priceCol.setPrefWidth(100);
            stockCol.setPrefWidth(100);
            actionCol.setPrefWidth(150);
            
            // Setup action column
            setupActionColumn();
            
            System.out.println("Table columns setup completed");
            
        } catch (Exception e) {
            System.err.println("Error in setupTableColumns: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw untuk melihat stack trace lengkap
        }
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
                
                addBtn.setOnAction(e -> {
                    try {
                        Product product = getTableView().getItems().get(getIndex());
                        String qtyText = quantityField.getText();
                        
                        // Basic validation
                        if (qtyText.isEmpty()) {
                            showAlert("Error", "Please enter quantity", Alert.AlertType.ERROR);
                            return;
                        }
                        
                        int quantity = Integer.parseInt(qtyText);
                        if (quantity <= 0) {
                            showAlert("Error", "Quantity must be positive", Alert.AlertType.ERROR);
                            return;
                        }
                        
                        // For now, just show confirmation
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Added to Cart");
                        alert.setHeaderText(null);
                        alert.setContentText("Added " + quantity + " x " + product.getName() + " to cart\nTotal: $" + String.format("%.2f", product.getPrice() * quantity));
                        alert.showAndWait();
                        
                    } catch (NumberFormatException ex) {
                        showAlert("Error", "Please enter a valid number", Alert.AlertType.ERROR);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        showAlert("Error", "Failed to add to cart: " + ex.getMessage(), Alert.AlertType.ERROR);
                    }
                });
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : container);
            }
            
            private void showAlert(String title, String message, Alert.AlertType type) {
                Alert alert = new Alert(type);
                alert.setTitle(title);
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
            }
        });
    }
    
    // Methods to populate table
    public void setProducts(ObservableList<Product> products) {
        try {
            System.out.println("Setting products to table, count: " + products.size());
            this.products.setAll(products);
            productTable.setItems(this.products);
            productTable.refresh();
            
            // Debug: print product details
            for (Product p : products) {
                System.out.println("Product: " + p.getName() + ", Price: " + p.getPrice() + ", Stock: " + p.getStock());
            }
            
        } catch (Exception e) {
            System.err.println("Error in setProducts: " + e.getMessage());
            e.printStackTrace();
        }
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