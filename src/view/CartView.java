package view;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.CartItem;

public class CartView extends VBox {
    private TableView<CartItem> cartTable;
    private TableColumn<CartItem, String> productCol;
    private TableColumn<CartItem, Double> priceCol;
    private TableColumn<CartItem, Integer> quantityCol;
    private TableColumn<CartItem, Double> totalCol;
    private TableColumn<CartItem, Void> actionCol;
    
    private Label subtotalLabel;
    private Label taxLabel;
    private Label totalLabel;
    
    private Button updateCartBtn;
    private Button checkoutBtn;
    private Button clearCartBtn;
    private Button backBtn;
    
    public CartView() {
        initializeUI();
    }
    
    private void initializeUI() {
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #ffffff;");
        
        setupHeader();
        setupCartTable();
        setupSummaryPanel();
        setupButtons();
    }
    
    private void setupHeader() {
        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setSpacing(15);
        
        backBtn = new Button("← Back");
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #333333; -fx-font-size: 14px;");
        
        Label title = new Label("Shopping Cart");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));
        title.setStyle("-fx-text-fill: #333333;");
        
        headerBox.getChildren().addAll(backBtn, title);
        this.getChildren().add(headerBox);
    }
    
    private void setupCartTable() {
        cartTable = new TableView<>();
        cartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        cartTable.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1px;");
        
        // Initialize columns
        productCol = new TableColumn<>("Product");
        productCol.setPrefWidth(250);
        
        priceCol = new TableColumn<>("Price ($)");
        priceCol.setPrefWidth(100);
        
        quantityCol = new TableColumn<>("Quantity");
        quantityCol.setPrefWidth(120);
        
        totalCol = new TableColumn<>("Total ($)");
        totalCol.setPrefWidth(100);
        
        actionCol = new TableColumn<>("Action");
        actionCol.setPrefWidth(100);
        
        cartTable.getColumns().addAll(productCol, priceCol, quantityCol, totalCol, actionCol);
        this.getChildren().add(cartTable);
    }
    
    private void setupSummaryPanel() {
        VBox summaryBox = new VBox(10);
        summaryBox.setPadding(new Insets(15));
        summaryBox.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #cccccc; -fx-border-width: 1px;");
        summaryBox.setPrefWidth(350);
        summaryBox.setAlignment(Pos.TOP_RIGHT);
        
        HBox subtotalRow = createSummaryRow("Subtotal:", subtotalLabel = new Label("$0.00"));
        HBox taxRow = createSummaryRow("Tax (10%):", taxLabel = new Label("$0.00"));
        
        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: #cccccc;");
        
        HBox totalRow = new HBox();
        totalRow.setAlignment(Pos.CENTER_LEFT);
        Label totalText = new Label("Total:");
        totalText.setFont(Font.font("System", FontWeight.BOLD, 16));
        totalText.setStyle("-fx-text-fill: #333333;");
        
        totalLabel = new Label("$0.00");
        totalLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        totalLabel.setStyle("-fx-text-fill: #333333;");
        
        HBox.setHgrow(totalLabel, Priority.ALWAYS);
        totalRow.getChildren().addAll(totalText, totalLabel);
        
        summaryBox.getChildren().addAll(subtotalRow, taxRow, separator, totalRow);
        this.getChildren().add(summaryBox);
    }
    
    private void setupButtons() {
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        
        clearCartBtn = new Button("Clear Cart");
        clearCartBtn.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: #333333; " +
                            "-fx-border-color: #cccccc; -fx-border-width: 1px;");
        
        updateCartBtn = new Button("Update Cart");
        updateCartBtn.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: #333333; " +
                             "-fx-border-color: #cccccc; -fx-border-width: 1px;");
        
        checkoutBtn = new Button("Proceed to Checkout");
        checkoutBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; " +
                           "-fx-font-weight: bold; -fx-padding: 8 16;");
        
        buttonBox.getChildren().addAll(clearCartBtn, updateCartBtn, checkoutBtn);
        this.getChildren().add(buttonBox);
    }
    
    private HBox createSummaryRow(String labelText, Label valueLabel) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setSpacing(20);
        
        Label label = new Label(labelText);
        label.setStyle("-fx-text-fill: #666666; -fx-font-size: 14px;");
        label.setPrefWidth(80);
        
        valueLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 14px;");
        HBox.setHgrow(valueLabel, Priority.ALWAYS);
        
        row.getChildren().addAll(label, valueLabel);
        return row;
    }
    
    // Public API methods
    public void setCartItems(ObservableList<CartItem> items) {
        cartTable.setItems(items);
    }
    
    public void updateSummary(double subtotal, double tax, double total) {
        subtotalLabel.setText(String.format("$%.2f", subtotal));
        taxLabel.setText(String.format("$%.2f", tax));
        totalLabel.setText(String.format("$%.2f", total));
    }
    
    // Getters for columns (needed by controller)
    public TableView<CartItem> getCartTable() { return cartTable; }
    public TableColumn<CartItem, String> getProductCol() { return productCol; }
    public TableColumn<CartItem, Double> getPriceCol() { return priceCol; }
    public TableColumn<CartItem, Integer> getQuantityCol() { return quantityCol; }
    public TableColumn<CartItem, Double> getTotalCol() { return totalCol; }
    public TableColumn<CartItem, Void> getActionCol() { return actionCol; }
    
    // Button getters
    public Button getUpdateCartBtn() { return updateCartBtn; }
    public Button getCheckoutBtn() { return checkoutBtn; }
    public Button getClearCartBtn() { return clearCartBtn; }
    public Button getBackBtn() { return backBtn; }
}