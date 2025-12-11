package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CheckoutView extends VBox {
	private Label orderSummaryLabel;
    private Label itemsTotalLabel;
    private Label promoDiscountLabel;
    private Label taxLabel;
    private Label grandTotalLabel;
    private Label balanceLabel;
    private Label balanceStatusLabel;
    
    private TextField promoCodeField;
    private Button applyPromoBtn;
    private Button placeOrderBtn;
    private Button backBtn;
    
    private TextArea shippingAddressArea;
    private TextField phoneField;
    private TextField emailField;
    
    public CheckoutView(double totalAmount, double currentBalance) {
        this.setSpacing(20);
        this.setPadding(new Insets(25));
        this.setStyle("-fx-background-color: #ffffff;");
        
        // Header
        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setSpacing(15);
        
        backBtn = new Button("← Back");
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #333333; -fx-font-size: 14px;");
        
        Label title = new Label("Checkout");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));
        title.setStyle("-fx-text-fill: #333333;");
        
        headerBox.getChildren().addAll(backBtn, title);
        
        // Main content - two columns
        HBox mainContent = new HBox(30);
        mainContent.setAlignment(Pos.TOP_LEFT);
        
        // Left: Order Summary
        VBox orderSummaryBox = new VBox(15);
        orderSummaryBox.setPrefWidth(400);
        
        Label summaryTitle = new Label("Order Summary");
        summaryTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        summaryTitle.setStyle("-fx-text-fill: #333333;");
        
        orderSummaryLabel = new Label("No items in cart");
        orderSummaryLabel.setWrapText(true);
        orderSummaryLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 14px;");
        orderSummaryLabel.setPrefHeight(80);
        orderSummaryLabel.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-padding: 10;");
        
        // Promo Code Section
        HBox promoBox = new HBox(10);
        promoBox.setAlignment(Pos.CENTER_LEFT);
        
        promoCodeField = new TextField();
        promoCodeField.setPromptText("Enter promo code");
        promoCodeField.setPrefWidth(200);
        promoCodeField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-width: 1px;");
        
        applyPromoBtn = new Button("Apply");
        applyPromoBtn.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: #333333; -fx-border-color: #cccccc; -fx-border-width: 1px;");
        
        promoBox.getChildren().addAll(promoCodeField, applyPromoBtn);
        
        // Calculations Box
        VBox calcBox = new VBox(10);
        calcBox.setPadding(new Insets(15));
        calcBox.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #cccccc; -fx-border-width: 1px;");
        
        HBox itemsRow = createCalcRow("Items Total:", itemsTotalLabel = new Label("$0.00"));
        HBox promoRow = createCalcRow("Promo Discount:", promoDiscountLabel = new Label("$0.00"));
        HBox taxRow = createCalcRow("Tax (10%):", taxLabel = new Label("$0.00"));
        
        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: #cccccc;");
        
        HBox totalRow = new HBox();
        totalRow.setAlignment(Pos.CENTER_LEFT);
        Label totalText = new Label("Grand Total:");
        totalText.setFont(Font.font("System", FontWeight.BOLD, 16));
        totalText.setStyle("-fx-text-fill: #333333;");
        
        grandTotalLabel = new Label("$0.00");
        grandTotalLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        grandTotalLabel.setStyle("-fx-text-fill: #333333;");
        HBox.setHgrow(grandTotalLabel, Priority.ALWAYS);
        
        totalRow.getChildren().addAll(totalText, grandTotalLabel);
        
        calcBox.getChildren().addAll(itemsRow, promoRow, taxRow, separator, totalRow);
        
        orderSummaryBox.getChildren().addAll(summaryTitle, orderSummaryLabel, promoBox, calcBox);
        
        // Right: Customer Info & Payment
        VBox customerBox = new VBox(15);
        customerBox.setPrefWidth(400);
        
        // Customer Information
        VBox customerInfoBox = new VBox(10);
        customerInfoBox.setPadding(new Insets(15));
        customerInfoBox.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #cccccc; -fx-border-width: 1px;");
        
        Label customerTitle = new Label("Customer Information");
        customerTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        customerTitle.setStyle("-fx-text-fill: #333333;");
        
        Label emailTitle = new Label("Email:");
        emailTitle.setStyle("-fx-text-fill: #666666; -fx-font-size: 14px;");
        
        emailField = new TextField();
        emailField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-width: 1px;");
        
        Label phoneTitle = new Label("Phone:");
        phoneTitle.setStyle("-fx-text-fill: #666666; -fx-font-size: 14px;");
        
        phoneField = new TextField();
        phoneField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-width: 1px;");
        
        Label addressTitle = new Label("Shipping Address:");
        addressTitle.setStyle("-fx-text-fill: #666666; -fx-font-size: 14px;");
        
        shippingAddressArea = new TextArea();
        shippingAddressArea.setPromptText("Enter your shipping address...");
        shippingAddressArea.setPrefHeight(100);
        shippingAddressArea.setWrapText(true);
        shippingAddressArea.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-width: 1px;");
        
        customerInfoBox.getChildren().addAll(customerTitle, emailTitle, emailField, phoneTitle, phoneField, addressTitle, shippingAddressArea);
        
        // Balance Information
        VBox balanceBox = new VBox(10);
        balanceBox.setPadding(new Insets(15));
        balanceBox.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #cccccc; -fx-border-width: 1px;");
        
        Label balanceTitle = new Label("Balance Information");
        balanceTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        balanceTitle.setStyle("-fx-text-fill: #333333;");
        
        HBox balanceRow = new HBox();
        balanceRow.setAlignment(Pos.CENTER_LEFT);
        Label currentBalanceText = new Label("Current Balance:");
        currentBalanceText.setStyle("-fx-text-fill: #666666; -fx-font-size: 14px;");
        
        balanceLabel = new Label("$0.00");
        balanceLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        balanceLabel.setStyle("-fx-text-fill: #333333;");
        HBox.setHgrow(balanceLabel, Priority.ALWAYS);
        
        balanceRow.getChildren().addAll(currentBalanceText, balanceLabel);
        
        balanceStatusLabel = new Label("Balance status will be displayed here");
        balanceStatusLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 13px;");
        
        balanceBox.getChildren().addAll(balanceTitle, balanceRow, balanceStatusLabel);
        
        // Place Order Button
        placeOrderBtn = new Button("Place Order");
        placeOrderBtn.setPrefWidth(400);
        placeOrderBtn.setPrefHeight(45);
        placeOrderBtn.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: #333333; -fx-font-weight: bold; -fx-font-size: 16px; -fx-border-color: #cccccc; -fx-border-width: 1px;");
        
        customerBox.getChildren().addAll(customerInfoBox, balanceBox, placeOrderBtn);
        
        mainContent.getChildren().addAll(orderSummaryBox, customerBox);
        
        // Warning message
        Label warningLabel = new Label("Note: Please review your order before placing. Orders cannot be cancelled after placement.");
        warningLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12px; -fx-font-style: italic;");
        warningLabel.setWrapText(true);
        
        this.getChildren().addAll(headerBox, mainContent, warningLabel);
    }
    
    private HBox createCalcRow(String labelText, Label valueLabel) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        Label label = new Label(labelText);
        label.setStyle("-fx-text-fill: #666666; -fx-font-size: 14px;");
        label.setPrefWidth(120);
        valueLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 14px;");
        HBox.setHgrow(valueLabel, Priority.ALWAYS);
        row.getChildren().addAll(label, valueLabel);
        return row;
    }
    
    public void setOrderSummary(String summary) {
        orderSummaryLabel.setText(summary);
    }
    
    public void updateTotals(double itemsTotal, double discount, double tax, double grandTotal) {
        itemsTotalLabel.setText(String.format("$%.2f", itemsTotal));
        promoDiscountLabel.setText(String.format("-$%.2f", discount));
        taxLabel.setText(String.format("$%.2f", tax));
        grandTotalLabel.setText(String.format("$%.2f", grandTotal));
    }
    
    public void setBalance(double balance) {
        balanceLabel.setText(String.format("$%.2f", balance));
    }
    
    public void setBalanceStatus(String status, boolean isSufficient) {
        balanceStatusLabel.setText(status);
        if (isSufficient) {
            balanceStatusLabel.setStyle("-fx-text-fill: #006600; -fx-font-size: 13px;");
        } else {
            balanceStatusLabel.setStyle("-fx-text-fill: #990000; -fx-font-size: 13px;");
        }
    }
    
    public void setCustomerInfo(String email, String phone, String address) {
        emailField.setText(email);
        phoneField.setText(phone);
        shippingAddressArea.setText(address);
    }
    
    // Getters
    public TextField getPromoCodeField() { return promoCodeField; }
    public Button getApplyPromoBtn() { return applyPromoBtn; }
    public Button getPlaceOrderBtn() { return placeOrderBtn; }
    public Button getBackBtn() { return backBtn; }
    public TextArea getShippingAddressArea() { return shippingAddressArea; }
    public TextField getPhoneField() { return phoneField; }
    public TextField getEmailField() { return emailField; }
}
