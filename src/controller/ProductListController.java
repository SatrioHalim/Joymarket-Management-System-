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
    private List<Product> dummyProducts;
    private ProductRepository productRepo;

    // Constructor tanpa repository
    public ProductListController(ProductListView view) {
        this.view = view;
        this.products = FXCollections.observableArrayList();
        this.dummyProducts = createDummyProducts();
        
        setupEventHandlers();
        loadProducts();
    }

    // Constructor dengan repository (untuk backward compatibility)
    public ProductListController(ProductListView view, ProductRepository productRepo) {
        this.view = view;
        this.products = FXCollections.observableArrayList();
        this.dummyProducts = createDummyProducts();
        this.productRepo = productRepo;
        
        setupEventHandlers();
        loadProducts();
    }

    private List<Product> createDummyProducts() {
        List<Product> dummyList = new ArrayList<>();

        // Create dummy products with realistic data - SESUAI MODEL ANDA (tanpa description)
        dummyList.add(new Product(1, "HP Laptop", 1499.99, 15, "Electronics"));
        dummyList.add(new Product(2, "iPhone 15 Pro", 1099.99, 25, "Electronics"));
        dummyList.add(new Product(3, "Samsung 4K TV", 699.99, 10, "Electronics"));
        dummyList.add(new Product(4, "Wireless Mouse", 29.99, 50, "Accessories"));
        dummyList.add(new Product(5, "Mechanical Keyboard", 89.99, 20, "Accessories"));
        dummyList.add(new Product(6, "Monitor 27\"", 249.99, 12, "Electronics"));
        dummyList.add(new Product(7, "Gaming Chair", 299.99, 8, "Furniture"));
        dummyList.add(new Product(8, "Webcam HD", 59.99, 30, "Accessories"));
        dummyList.add(new Product(9, "External SSD 1TB", 129.99, 25, "Storage"));
        dummyList.add(new Product(10, "Bluetooth Headphones", 199.99, 18, "Audio"));

        return dummyList;
    }

    private void setupEventHandlers() {
        view.getSearchBtn().setOnAction(e -> searchProducts());
        view.getSearchField().setOnAction(e -> searchProducts());
        
        // Setup back button handler
        view.getBackBtn().setOnAction(e -> {
            showInfo("Returning to dashboard...");
            // TODO: Add navigation logic here
        });
    }

    private void loadProducts() {
        try {
        	List<Product> pl = productRepo.getAllProducts();
            products.setAll(pl);
            view.setProducts(products);
            System.out.println("Loaded " + pl.size() + " products");
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
            
            // Search in dummy products - hanya berdasarkan nama dan kategori
            for (Product product : dummyProducts) {
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

    public void handleAddToCart(Product product, int quantity) {
        try {
            // Validasi quantity
            if (quantity <= 0) {
                showError("Quantity must be greater than 0");
                return;
            }

            // Check stock availability (dummy check)
            if (quantity > product.getStock()) {
                showError("Insufficient stock! Available: " + product.getStock());
                return;
            }

            // Add to cart logic (dummy)
            showAlert("Added to Cart", 
                "Added " + quantity + " of " + product.getName() + " to cart\n" +
                "Total: $" + String.format("%.2f", (product.getPrice() * quantity)),
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
    
    // Helper method untuk debugging
    public void printProducts() {
        System.out.println("Current products in controller:");
        for (Product p : dummyProducts) {
            System.out.println(p.getId() + " - " + p.getName() + " - $" + p.getPrice());
        }
    }
}