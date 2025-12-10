package controller;

import javafx.scene.control.Alert;
import repository.UserRepository;
import view.CheckoutView;

public class CheckoutController {
	private CheckoutView view;
	private UserRepository userRepo;

	public CheckoutController(CheckoutView view, UserRepository userRepo) {
		this.view = view;
		this.userRepo = userRepo;

		setupEventHandlers();
		loadCheckoutData();
	}

	private void setupEventHandlers() {
		view.getApplyPromoBtn().setOnAction(e -> applyPromoCode());
		view.getPlaceOrderBtn().setOnAction(e -> placeOrder());
	}

	private void loadCheckoutData() {
		try {
			// Load cart items for order summary
			String orderSummary = "Product 1 x 2 = $20.00\nProduct 2 x 1 = $15.00\nProduct 3 x 3 = $30.00";
			view.setOrderSummary(orderSummary);

			// Calculate totals
			double itemsTotal = 65.00;
			double discount = 0.00;
			double tax = 6.50;
			double grandTotal = 71.50;

			view.updateTotals(itemsTotal, discount, tax, grandTotal);

			// Set user balance
			double balance = 100.00; // Get from user
			view.setBalance(balance);

			// Check if balance is sufficient
			boolean isSufficient = balance >= grandTotal;
			String status = isSufficient ? "Sufficient balance for this order"
					: "Insufficient balance. Please top up your account";
			view.setBalanceStatus(status, isSufficient);

			// Set customer info
			view.setCustomerInfo("customer@example.com", "081234567890", "123 Main St, City, Country");

		} catch (Exception e) {
			showError("Failed to load checkout data: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void applyPromoCode() {
		String promoCode = view.getPromoCodeField().getText().trim();
		if (promoCode.isEmpty()) {
			showAlert("Error", "Please enter a promo code", Alert.AlertType.WARNING);
			return;
		}

		try {
			// Validate promo code logic
			// promoRepo.validatePromoCode(promoCode);

			showAlert("Info", "Promo code feature coming soon!", Alert.AlertType.INFORMATION);
		} catch (Exception e) {
			showError("Failed to apply promo code: " + e.getMessage());
		}
	}

	private void placeOrder() {
		Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
		confirm.setTitle("Confirm Order");
		confirm.setHeaderText("Place Order");
		confirm.setContentText("Are you sure you want to place this order?");

		confirm.showAndWait().ifPresent(response -> {
			if (response == javafx.scene.control.ButtonType.OK) {
				try {
					// Place order logic
					// orderRepo.createOrder(userId, cartItems, shippingAddress, etc);

					showAlert("Success", "Order placed successfully!", Alert.AlertType.INFORMATION);
					// Redirect to order confirmation or dashboard

				} catch (Exception e) {
					showError("Failed to place order: " + e.getMessage());
				}
			}
		});
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
