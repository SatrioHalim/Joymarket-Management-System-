package controller;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Customer;
import model.Product;
import repository.CartRepository;
import repository.ProductRepository;
import view.ProductListView;

public class ProductListController {
    private ProductListView view;
    private ObservableList<Product> products;
    private ProductRepository productRepo;
    private CartRepository cartRepo;
    private Customer customer;
    
    public ProductListController(ProductListView view, ProductRepository productRepo, 
                                CartRepository cartRepo, Customer customer) {
        this.view = view;
        this.products = FXCollections.observableArrayList();
        this.productRepo = productRepo;
        this.cartRepo = cartRepo;
        this.customer = customer;
        
        setupEventHandlers();
        loadProducts();
    }
    
    private void setupEventHandlers() {
        view.getSearchBtn().setOnAction(e -> searchProducts());
        view.getSearchField().setOnAction(e -> searchProducts());
        view.getBackBtn().setOnAction(e -> handleBack());
        view.setAddToCartHandler(this::handleAddToCart);
    }
    
    private void handleBack() {
        // Navigation handled by parent controller
        showInfo("Returning to dashboard...");
    }
    
    private void loadProducts() {
        try {
            List<Product> productList = productRepo.getAllProducts();
            products.setAll(productList);
            view.setProducts(products);
            System.out.println("Loaded " + productList.size() + " products");
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error loading products: " + e.getMessage());
        }
    }
    
    private void searchProducts() {
        String keyword = view.getSearchField().getText().trim().toLowerCase();
        
        if (keyword.isEmpty()) {
            loadProducts();
            return;
        }
        
        try {
            List<Product> searchResults = new ArrayList<>();
            List<Product> allProducts = productRepo.getAllProducts();
            
            for (Product product : allProducts) {
                if (product.getName().toLowerCase().contains(keyword) ||
                    product.getCategory().toLowerCase().contains(keyword)) {
                    searchResults.add(product);
                }
            }
            
            products.setAll(searchResults);
            view.setProducts(products);
            
            if (searchResults.isEmpty()) {
                showInfo("No products found for: " + keyword);
            }
        } catch (Exception e) {
            showError("Search failed: " + e.getMessage());
        }
    }
    
    public void handleAddToCart(Product product, String quantityText) {
        try {
            // Validate input
            if (quantityText == null || quantityText.trim().isEmpty()) {
                showError("Please enter quantity");
                return;
            }
            
            // Parse quantity
            int quantity;
            try {
                quantity = Integer.parseInt(quantityText.trim());
            } catch (NumberFormatException e) {
                showError("Please enter a valid number");
                return;
            }
            
            // Validate quantity
            if (quantity <= 0) {
                showError("Quantity must be greater than 0");
                return;
            }
            
            // Check stock availability
            if (quantity > product.getStock()) {
                showError("Insufficient stock! Available: " + product.getStock());
                return;
            }
            
            // Add to cart in database
            boolean success = cartRepo.addToCart(
                customer.getId(), 
                product.getId(), 
                quantity
            );
            
            if (success) {
                // Update stock in database (reserve stock)
                productRepo.updateProductStock(product.getId(), product.getStock() - quantity);
                
                // Show success message
                double total = product.getPrice() * quantity;
                showAlert("Success", 
                    "✓ Added to Cart Successfully\n" +
                    "Product: " + product.getName() + "\n" +
                    "Quantity: " + quantity + "\n" +
                    "Price: $" + String.format("%.2f", product.getPrice()) + "\n" +
                    "Subtotal: $" + String.format("%.2f", total),
                    Alert.AlertType.INFORMATION);
                
                // Refresh product list to show updated stock
                loadProducts();
            } else {
                showError("Failed to add item to cart. Please try again.");
            }
            
        } catch (Exception e) {
            showError("Failed to add to cart: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Helper methods
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showError(String message) {
        showAlert("Error", message, Alert.AlertType.ERROR);
    }
    
    private void showInfo(String message) {
        showAlert("Info", message, Alert.AlertType.INFORMATION);
    }
    
    // Getters
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}