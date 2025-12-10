package controller;

import javafx.scene.control.Alert;
import repository.UserRepository;
import view.ProductListView;

public class ProductListController {
	private ProductListView view;
	private UserRepository userRepo;

	public ProductListController(ProductListView view, UserRepository userRepo) {
		this.view = view;
		this.userRepo = userRepo;

		setupEventHandlers();
		loadProducts();
	}

	private void setupEventHandlers() {
		view.getSearchBtn().setOnAction(e -> searchProducts());

		// Search on enter key press
		view.getSearchField().setOnAction(e -> searchProducts());
	}

	private void loadProducts() {
		try {
			// Load products from database
			// view.getProductTable().setItems(productRepo.getAllProducts());

			// For now, show message
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Info");
			alert.setHeaderText(null);
			alert.setContentText("Product list feature will be implemented after database setup");
			alert.showAndWait();

		} catch (Exception e) {
			showError("Failed to load products: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void searchProducts() {
		String keyword = view.getSearchField().getText().trim();
		if (keyword.isEmpty()) {
			loadProducts();
			return;
		}

		try {
			// Search products logic
			// view.getProductTable().setItems(productRepo.searchProducts(keyword));

			showAlert("Search", "Search feature coming soon!", Alert.AlertType.INFORMATION);
		} catch (Exception e) {
			showError("Search failed: " + e.getMessage());
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
}
