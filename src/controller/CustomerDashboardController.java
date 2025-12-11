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
import repository.CartRepository;
import repository.ProductRepository;
import repository.UserRepository;
import view.CartView;
import view.CheckoutView;
import view.CustomerDashboardView;
import view.ProductListView;

public class CustomerDashboardController {
    private CustomerDashboardView view;
    private UserRepository userRepo;
    private Customer customer;
    private ProductRepository productRepo;
    private CartRepository cartRepo;
    private double currentBalance;
    
    public CustomerDashboardController(CustomerDashboardView view, Customer customer) {
        this.view = view;
        this.userRepo = new UserRepository();
        this.customer = customer;
        this.productRepo = new ProductRepository();
        this.currentBalance = customer.getBalance();
        this.cartRepo = new CartRepository();
        
        setupEventHandlers();
        loadDefaultView();
        updateBalanceDisplay();
    }
    
    private void setupEventHandlers() {
        view.getViewProductsBtn().setOnAction(e -> showProductListView());
        view.getViewCartBtn().setOnAction(e -> showCartView());
        view.getCheckoutBtn().setOnAction(e -> showCheckoutView());
        view.getLogoutBtn().setOnAction(e -> handleLogout());
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
        
        VBox statsBox = new VBox(15);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.setMaxWidth(400);
        
        // Stats dengan data real
        VBox productStat = createStatBox("Available Products", 
            String.valueOf(getAvailableProductCount()));
        VBox cartStat = createStatBox("Items in Cart", "0"); // TODO: Get from cart
        VBox balanceStat = createStatBox("Your Balance", 
            String.format("$%.2f", currentBalance));
        
        statsBox.getChildren().addAll(productStat, cartStat, balanceStat);
        defaultContent.getChildren().addAll(welcomeMsg, instruction, statsBox);
        
        view.getContentArea().getChildren().clear();
        view.getContentArea().getChildren().add(defaultContent);
    }
    
    private int getAvailableProductCount() {
        try {
            return productRepo.getAllProducts().size();
        } catch (Exception e) {
            return 0;
        }
    }
    
    private void showProductListView() {
        try {
            ProductListView productListView = new ProductListView();
            ProductListController productListController = new ProductListController(productListView, 
            		productRepo, cartRepo, customer);
            
            
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
            CartController cartController = new CartController(cartView, cartRepo, customer); // UPDATED
            
            // Load cart items
            cartController.loadCartItems();
            
            cartView.getBackBtn().setOnAction(e -> loadDefaultView());
            cartView.getCheckoutBtn().setOnAction(e -> {
                double totalAmount = cartController.getTotalAmount();
                showCheckoutView(totalAmount);
            });
            
            view.getContentArea().getChildren().clear();
            view.getContentArea().getChildren().add(cartView);
            
        } catch (Exception e) {
            showAlert("Error", "Failed to load cart: " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    
    private void showCheckoutView() {
        showCheckoutView(0.0); // Default jika dipanggil langsung
    }
    
    public void showCheckoutView(double totalAmount) {
        try {
            CheckoutView checkoutView = new CheckoutView(totalAmount, currentBalance);
            CheckoutController checkoutController = new CheckoutController(checkoutView, userRepo);
            
            // Pass data yang diperlukan
            checkoutController.setCustomer(customer);
            checkoutController.setDashboardController(this);
            checkoutController.setTotalAmount(totalAmount);
            
            checkoutView.getBackBtn().setOnAction(e -> showCartView());
            checkoutView.getPlaceOrderBtn().setOnAction(e -> {
                handleCheckout(checkoutController, totalAmount);
            });
            
            view.getContentArea().getChildren().clear();
            view.getContentArea().getChildren().add(checkoutView);
            
        } catch (Exception e) {
            showAlert("Error", "Failed to load checkout: " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    private void handleCheckout(CheckoutController checkoutController, double totalAmount) {
        try {
            // Validasi saldo cukup
            if (totalAmount > currentBalance) {
                showAlert("Error", "Insufficient balance!", AlertType.ERROR);
                return;
            }
            
            // Proses checkout
            boolean success = checkoutController.processCheckout();
            
            if (success) {
                // Update balance
                double newBalance = currentBalance - totalAmount;
                updateCustomerBalance(newBalance);
                
                // Show success message
                showAlert("Success", 
                    "Checkout successful!\n" +
                    "Total: $" + String.format("%.2f", totalAmount) + "\n" +
                    "New Balance: $" + String.format("%.2f", newBalance),
                    AlertType.INFORMATION);
                
                // Kembali ke default view
                loadDefaultView();
            }
            
        } catch (Exception e) {
            showAlert("Error", "Checkout failed: " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    private void handleLogout() {
        // TODO: Save any pending changes
        SceneManager.changeToLogin();
    }
    
    // Method untuk update balance dari controller lain
    public void updateCustomerBalance(double newBalance) {
        try {
            this.currentBalance = newBalance;
            this.customer.setBalance(newBalance);
            
            // Update di database
            userRepo.updateCustomerBalance(customer.getId(), newBalance);
            
            // Update display
            updateBalanceDisplay();
            
        } catch (Exception e) {
            showAlert("Error", "Failed to update balance: " + e.getMessage(), AlertType.ERROR);
        }
    }
    
    private void updateBalanceDisplay() {
        view.getBalanceLabel().setText(String.format("$%.2f", currentBalance));
    }
    
    public double getCurrentBalance() {
        return currentBalance;
    }
    
    public Customer getCustomer() {
        return customer;
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
}