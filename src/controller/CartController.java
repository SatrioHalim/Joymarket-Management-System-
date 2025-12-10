package controller;

import javafx.scene.control.Alert;
import repository.UserRepository;
import view.CartView;

public class CartController {
	private CartView view;
	private UserRepository userRepo;

	public CartController(CartView view, UserRepository userRepo) {
		this.view = view;
		this.userRepo = userRepo;

		setupEventHandlers();
		loadCartItems();
	}

	private void setupEventHandlers() {
		view.getUpdateCartBtn().setOnAction(e -> updateCart());
		view.getClearCartBtn().setOnAction(e -> clearCart());
	}

	private void loadCartItems() {
		try {
			// Load cart items from database
			// view.getCartTable().setItems(cartRepo.getCartItems(userId));

			// For now, show empty message
			updateSummary(0, 0, 0);

		} catch (Exception e) {
			showError("Failed to load cart: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void updateCart() {
		try {
			// Update cart quantities logic
			showAlert("Info", "Cart update feature coming soon!", Alert.AlertType.INFORMATION);
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
					// Clear cart logic
					loadCartItems();
					showAlert("Success", "Cart cleared successfully", Alert.AlertType.INFORMATION);
				} catch (Exception e) {
					showError("Failed to clear cart: " + e.getMessage());
				}
			}
		});
	}

	private void updateSummary(double subtotal, double tax, double total) {
		view.updateSummary(subtotal, tax, total);
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
