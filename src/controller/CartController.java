package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.CartItem;
import model.Customer;
import repository.CartRepository;
import view.CartView;

public class CartController {
    private CartView view;
    private CartRepository cartRepo;
    private Customer customer;
    private CustomerDashboardController dashboardController;
    private ObservableList<CartItem> cartItems;
    
    public CartController(CartView view, CartRepository cartRepo, Customer customer) {
        this.view = view;
        this.cartRepo = cartRepo;
        this.customer = customer;
        this.cartItems = FXCollections.observableArrayList();
        
        setupEventHandlers();
        loadCartItems();
    }
    
    private void setupEventHandlers() {
        view.getUpdateCartBtn().setOnAction(e -> updateCart());
        view.getClearCartBtn().setOnAction(e -> clearCart());
        view.getCheckoutBtn().setOnAction(e -> handleCheckout());
    }
    
    public void loadCartItems() {
        try {
            // Load cart items from database
            cartItems.setAll(cartRepo.getCartItems(customer.getId()));
            view.setCartItems(cartItems);
            
            // Update summary
            updateCartSummary();
            
            // Set up table columns
            setupTableColumns();
            
        } catch (Exception e) {
            showError("Failed to load cart: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void setupTableColumns() {
        // Setup cell value factories untuk tabel
        view.getProductCol().setCellValueFactory(cellData -> 
            javafx.beans.binding.Bindings.createStringBinding(
                () -> cellData.getValue().getProductName()
            )
        );
        
        view.getPriceCol().setCellValueFactory(cellData -> 
            javafx.beans.binding.Bindings.createObjectBinding(
                () -> cellData.getValue().getProductPrice()
            )
        );
        
        view.getQuantityCol().setCellValueFactory(cellData -> 
            javafx.beans.binding.Bindings.createObjectBinding(
                () -> cellData.getValue().getCount()
            )
        );
        
        view.getTotalCol().setCellValueFactory(cellData -> 
            javafx.beans.binding.Bindings.createObjectBinding(
                () -> cellData.getValue().getSubtotal()
            )
        );
        
        // Setup quantity column cell factory
        view.getQuantityCol().setCellFactory(col -> new javafx.scene.control.TableCell<CartItem, Integer>() {
            private final javafx.scene.control.TextField quantityField = new javafx.scene.control.TextField();
            
            {
                quantityField.setPrefWidth(60);
                quantityField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-width: 1px;");
                
                // Only allow numbers
                quantityField.textProperty().addListener((obs, oldVal, newVal) -> {
                    if (!newVal.matches("\\d*")) {
                        quantityField.setText(newVal.replaceAll("[^\\d]", ""));
                    }
                });
                
                // On enter/click out, update quantity
                quantityField.focusedProperty().addListener((obs, oldVal, newVal) -> {
                    if (!newVal) { // Lost focus
                        updateItemQuantity();
                    }
                });
            }
            
            private void updateItemQuantity() {
                CartItem item = getTableView().getItems().get(getIndex());
                String qtyText = quantityField.getText();
                
                if (qtyText.isEmpty()) {
                    quantityField.setText(String.valueOf(item.getCount()));
                    return;
                }
                
                try {
                    int newQuantity = Integer.parseInt(qtyText);
                    
                    if (newQuantity <= 0) {
                        // Remove item if quantity is 0 or negative
                        removeCartItem(item.getId());
                        return;
                    }
                    
                    // Check stock availability
                    if (newQuantity > item.getProductStock()) {
                        showError("Insufficient stock! Available: " + item.getProductStock());
                        quantityField.setText(String.valueOf(item.getCount()));
                        return;
                    }
                    
                    // Update in database
                    boolean success = cartRepo.updateCartQuantity(item.getId(), newQuantity);
                    
                    if (success) {
                        item.setCount(newQuantity);
                        getTableView().refresh();
                        updateCartSummary();
                    } else {
                        showError("Failed to update quantity");
                    }
                    
                } catch (NumberFormatException e) {
                    showError("Please enter a valid number");
                    quantityField.setText(String.valueOf(item.getCount()));
                }
            }
            
            @Override
            protected void updateItem(Integer quantity, boolean empty) {
                super.updateItem(quantity, empty);
                if (empty || quantity == null) {
                    setGraphic(null);
                } else {
                    quantityField.setText(quantity.toString());
                    setGraphic(quantityField);
                }
            }
        });
        
        // Setup action column cell factory (Remove button)
        view.getActionCol().setCellFactory(col -> new javafx.scene.control.TableCell<CartItem, Void>() {
            private final javafx.scene.control.Button removeBtn = new javafx.scene.control.Button("Remove");
            
            {
                removeBtn.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: #333333; " +
                                 "-fx-border-color: #cccccc; -fx-border-width: 1px;");
                
                removeBtn.setOnAction(e -> {
                    CartItem item = getTableView().getItems().get(getIndex());
                    removeCartItem(item.getId());
                });
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : removeBtn);
            }
        });
    }
    
    private void updateCart() {
        try {
            // Update all quantities from table
            for (CartItem item : cartItems) {
                // Quantity already updated through cell factory when focus lost
                // So we just need to refresh
            }
            
            showAlert("Success", "Cart updated successfully", Alert.AlertType.INFORMATION);
            
        } catch (Exception e) {
            showError("Failed to update cart: " + e.getMessage());
        }
    }
    
    private void clearCart() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Clear Cart");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to clear all items from your cart?");
        
        confirm.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                try {
                    boolean success = cartRepo.clearCart(customer.getId());
                    
                    if (success) {
                        cartItems.clear();
                        view.setCartItems(cartItems);
                        updateCartSummary();
                        showAlert("Success", "Cart cleared successfully", Alert.AlertType.INFORMATION);
                    } else {
                        showError("Failed to clear cart");
                    }
                } catch (Exception e) {
                    showError("Failed to clear cart: " + e.getMessage());
                }
            }
        });
    }
    
    private void removeCartItem(int cartItemId) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Remove Item");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to remove this item from cart?");
        
        confirm.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                try {
                    boolean success = cartRepo.removeFromCart(cartItemId);
                    
                    if (success) {
                        loadCartItems(); // Reload cart items
                        showAlert("Success", "Item removed from cart", Alert.AlertType.INFORMATION);
                    } else {
                        showError("Failed to remove item");
                    }
                } catch (Exception e) {
                    showError("Failed to remove item: " + e.getMessage());
                }
            }
        });
    }
    
    private void updateCartSummary() {
        double subtotal = cartItems.stream()
            .mapToDouble(CartItem::getSubtotal)
            .sum();
        
        double tax = subtotal * 0.10; // 10% tax
        double total = subtotal + tax;
        
        view.updateSummary(subtotal, tax, total);
    }
    
    private void handleCheckout() {
        if (cartItems.isEmpty()) {
            showError("Your cart is empty. Add some items first!");
            return;
        }
        
        double totalAmount = getTotalAmount();
        
        if (dashboardController != null) {
            dashboardController.showCheckoutView(totalAmount);
        } else {
            showError("Cannot proceed to checkout. Please try again.");
        }
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public void setDashboardController(CustomerDashboardController controller) {
        this.dashboardController = controller;
    }
    
    public double getTotalAmount() {
        double subtotal = cartItems.stream()
            .mapToDouble(CartItem::getSubtotal)
            .sum();
        
        double tax = subtotal * 0.10;
        return subtotal + tax;
    }
    
    // Getter for cart items (for testing or other uses)
    public ObservableList<CartItem> getCartItems() {
        return cartItems;
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
}