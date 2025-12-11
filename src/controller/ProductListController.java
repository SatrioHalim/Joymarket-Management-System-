package controller;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Product;
import repository.ProductRepository;
import view.ProductListView;

public class ProductListController {
    private ProductListView view;
    private ObservableList<Product> products;
    private ProductRepository productRepo;

    public ProductListController(ProductListView view, ProductRepository productRepo) {
        this.view = view;
        this.products = FXCollections.observableArrayList();
        this.productRepo = productRepo;
        
        setupEventHandlers();
        loadProducts();
    }

    private void setupEventHandlers() {
        view.getSearchBtn().setOnAction(e -> searchProducts());
        view.getSearchField().setOnAction(e -> searchProducts());
        view.getBackBtn().setOnAction(e -> handleBack());
        
        // Setup add to cart handler di view
        view.setAddToCartHandler(this::handleAddToCart);
    }
    
    private void handleBack() {
        showInfo("Returning to dashboard...");
        // TODO: Add navigation logic here
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
            // Validasi input kosong
            if (quantityText == null || quantityText.trim().isEmpty()) {
                showError("Please enter quantity");
                return;
            }
            
            // Validasi format number
            int quantity;
            try {
                quantity = Integer.parseInt(quantityText.trim());
            } catch (NumberFormatException e) {
                showError("Please enter a valid number");
                return;
            }
            
            // Validasi quantity positif
            if (quantity <= 0) {
                showError("Quantity must be greater than 0");
                return;
            }

            // Check stock availability
            if (quantity > product.getStock()) {
                showError("Insufficient stock! Available: " + product.getStock());
                return;
            }

            // Add to cart logic
            double total = product.getPrice() * quantity;
            showAlert("Added to Cart", 
                "Added " + quantity + " of " + product.getName() + " to cart\n" +
                "Total: $" + String.format("%.2f", total),
                Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            showError("Failed to add to cart: " + e.getMessage());
        }
    }
    
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
}