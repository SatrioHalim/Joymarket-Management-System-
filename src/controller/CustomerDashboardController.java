package controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Customer;
import repository.UserRepository;
import view.CartView;
import view.CheckoutView;
import view.CustomerDashboardView;
import view.ProductListView;

public class CustomerDashboardController {
	private CustomerDashboardView view;
    private UserRepository userRepo;
    private Customer customer;
    
    public CustomerDashboardController(CustomerDashboardView view, Customer customer) {
        this.view = view;
        this.userRepo = new UserRepository();
        this.customer = customer;
        
        setupEventHandlers();
        loadDefaultView();
    }
    
    private void setupEventHandlers() {
        // Products Button - Show Product List
        view.getViewProductsBtn().setOnAction(e -> showProductListView());
        
        // Cart Button - Show Cart
        view.getViewCartBtn().setOnAction(e -> showCartView());
        
        // Checkout Button - Show Checkout
        view.getCheckoutBtn().setOnAction(e -> {
            showCheckoutView();
        });
        
        // Logout Button - Go back to Login
        view.getLogoutBtn().setOnAction(e -> {
            SceneManager.changeToLogin();
        });
    }
    
    private void loadDefaultView() {
        VBox defaultContent = new VBox(20);
        defaultContent.setAlignment(Pos.CENTER);
        defaultContent.setPadding(new Insets(50));
        
        Label welcomeMsg = new Label("Welcome " + customer.getFullName());
        welcomeMsg.setFont(Font.font("System", FontWeight.BOLD, 24));
        welcomeMsg.setStyle("-fx-text-fill: #333333;");
        
        Label instruction = new Label("Select a menu from the left sidebar to get started");
        instruction.setStyle("-fx-text-fill: #666666; -fx-font-size: 14px;");
        
        // Stats boxes
        VBox statsBox = new VBox(15);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.setMaxWidth(400);
        
        // Product stat
        VBox productStat = createStatBox("Available Products", "0");
        // Cart stat
        VBox cartStat = createStatBox("Items in Cart", "0");
        // Balance stat
        VBox balanceStat = createStatBox("Your Balance", "$0.00");
        
        statsBox.getChildren().addAll(productStat, cartStat, balanceStat);
        
        defaultContent.getChildren().addAll(welcomeMsg, instruction, statsBox);
        
        view.getContentArea().getChildren().clear();
        view.getContentArea().getChildren().add(defaultContent);
    }
    
    private void showProductListView() {
        try {
            ProductListView productListView = new ProductListView();
            // Setup controller for product list
            ProductListController productListController = new ProductListController(productListView, userRepo);
            
            // Handle back button
            productListView.getBackBtn().setOnAction(e -> loadDefaultView());
            
            view.getContentArea().getChildren().clear();
            view.getContentArea().getChildren().add(productListView);
            
        } catch (Exception e) {
            showAlert("Error", "Failed to load products: " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    private void showCartView() {
        try {
            CartView cartView = new CartView();
            // Setup controller for cart
            CartController cartController = new CartController(cartView, userRepo);
            
            // Handle back button
            cartView.getBackBtn().setOnAction(e -> loadDefaultView());
            
            // Handle checkout button
            cartView.getCheckoutBtn().setOnAction(e -> showCheckoutView());
            
            view.getContentArea().getChildren().clear();
            view.getContentArea().getChildren().add(cartView);
            
        } catch (Exception e) {
            showAlert("Error", "Failed to load cart: " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    private void showCheckoutView() {
        try {
            CheckoutView checkoutView = new CheckoutView();
            // Setup controller for checkout
            CheckoutController checkoutController = new CheckoutController(checkoutView, userRepo);
            
            // Handle back button
            checkoutView.getBackBtn().setOnAction(e -> showCartView());
            
            view.getContentArea().getChildren().clear();
            view.getContentArea().getChildren().add(checkoutView);
            
        } catch (Exception e) {
            showAlert("Error", "Failed to load checkout: " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    private VBox createStatBox(String title, String value) {
        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(15, 30, 15, 30));
        box.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #cccccc; -fx-border-width: 1px;");
        
        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        valueLabel.setStyle("-fx-text-fill: #333333;");
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 13px;");
        
        box.getChildren().addAll(valueLabel, titleLabel);
        return box;
    }
    
    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    // Method to update user info (called from SceneManager)
    public void updateUserInfo(String fullname, double balance) {
        view.getWelcomeLabel().setText("Welcome, " + fullname);
        view.getBalanceLabel().setText(String.format("$%.2f", balance));
    }
	
}
