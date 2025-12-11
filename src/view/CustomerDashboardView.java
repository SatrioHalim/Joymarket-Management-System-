package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Customer;

public class CustomerDashboardView extends BorderPane{
	private Button viewProductsBtn;
    private Button viewCartBtn;
    private Button checkoutBtn;
    private Button logoutBtn;
    private Label welcomeLabel;
    private Label balanceLabel;
    private StackPane contentArea;
    private Customer customer;
    
    public CustomerDashboardView(Customer customer) {
        // Left Sidebar Navigation - PUTIH
    	this.customer = customer;
        VBox sidebar = createSidebar();
        
        // Top Header - ABU-ABU MUDA
        HBox header = createHeader();
        
        // Center Content Area
        contentArea = new StackPane();
        contentArea.setPadding(new Insets(20));
        contentArea.setStyle("-fx-background-color: #ffffff;");
        
        // Set layout
        this.setLeft(sidebar);
        this.setTop(header);
        this.setCenter(contentArea);
        
        // Background utama
        this.setStyle("-fx-background-color: #f0f0f0;");
        
        // Set default content
        setDefaultContent();
    }
    
    private VBox createSidebar() {
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(20, 15, 20, 15));
        sidebar.setPrefWidth(200);
        sidebar.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 0 1 0 0;");
        
        // User Info
        VBox userInfo = new VBox(5);
        userInfo.setPadding(new Insets(0, 0, 20, 0));
        
        Label userTitle = new Label(customer.getFullName());
        userTitle.setFont(Font.font("System", FontWeight.BOLD, 14));
        userTitle.setStyle("-fx-text-fill: #333333;");
        
        welcomeLabel = new Label("Welcome!");
        welcomeLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12px;");
        
        userInfo.getChildren().addAll(userTitle, welcomeLabel);
        
        // Navigation Buttons - styling sederhana
        viewProductsBtn = createSimpleButton("Products");
        viewCartBtn = createSimpleButton("My Cart");
        checkoutBtn = createSimpleButton("Checkout");
        logoutBtn = createSimpleButton("Logout");
        
        sidebar.getChildren().addAll(userInfo, viewProductsBtn, viewCartBtn, checkoutBtn, logoutBtn);
        
        return sidebar;
    }
    
    private HBox createHeader() {
        HBox header = new HBox();
        header.setPadding(new Insets(15, 20, 15, 20));
        header.setStyle("-fx-background-color: #333333;");
        
        // Title
        Label title = new Label("Customer Dashboard");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));
        title.setStyle("-fx-text-fill: #ffffff;");
        
        // Balance display
        HBox balanceBox = new HBox(10);
        balanceBox.setAlignment(Pos.CENTER_RIGHT);
        
        Label balanceTitle = new Label("Balance:");
        balanceTitle.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 14px;");
        
        balanceLabel = new Label(String.format("$%.2f", customer.getBalance()));
        balanceLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        balanceLabel.setStyle("-fx-text-fill: #ffffff;");
        
        balanceBox.getChildren().addAll(balanceTitle, balanceLabel);
        
        HBox.setHgrow(balanceBox, Priority.ALWAYS);
        
        header.getChildren().addAll(title, balanceBox);
        
        return header;
    }
    
    private Button createSimpleButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(170);
        btn.setPrefHeight(40);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-text-fill: #333333; " +
            "-fx-font-size: 14px; " +
            "-fx-border-color: #e0e0e0; " +
            "-fx-border-width: 1px; " +
            "-fx-background-radius: 0px; " +
            "-fx-border-radius: 0px; " +
            "-fx-padding: 0 0 0 15;"
        );
        btn.setOnMouseEntered(e -> {
            btn.setStyle(
                "-fx-background-color: #f5f5f5; " + 
                "-fx-text-fill: #333333; " +
                "-fx-font-size: 14px; " +
                "-fx-border-color: #d0d0d0; " +  
                "-fx-border-width: 1px; " +
                "-fx-background-radius: 0px; " +
                "-fx-border-radius: 0px; " +
                "-fx-padding: 0 0 0 15;"
            );
        });
        
        btn.setOnMouseExited(e -> {
            btn.setStyle(
                "-fx-background-color: #ffffff; " +  
                "-fx-text-fill: #333333; " +
                "-fx-font-size: 14px; " +
                "-fx-border-color: #e0e0e0; " +
                "-fx-border-width: 1px; " +
                "-fx-background-radius: 0px; " +
                "-fx-border-radius: 0px; " +
                "-fx-padding: 0 0 0 15;"
            );
        });
        
        return btn;
    }
    
    private void setDefaultContent() {
        VBox defaultContent = new VBox(20);
        defaultContent.setAlignment(Pos.CENTER);
        defaultContent.setPadding(new Insets(50));
        
        Label welcomeMsg = new Label("Welcome to Customer Dashboard");
        welcomeMsg.setFont(Font.font("System", FontWeight.BOLD, 24));
        welcomeMsg.setStyle("-fx-text-fill: #333333;");
        
        Label instruction = new Label("Select a menu from the left sidebar to get started");
        instruction.setStyle("-fx-text-fill: #666666; -fx-font-size: 14px;");
        
        // Simple separator line
        Separator separator = new Separator();
        separator.setPrefWidth(300);
        separator.setStyle("-fx-background-color: #e0e0e0;");
        
        // Simple info box
        VBox infoBox = new VBox(10);
        infoBox.setPadding(new Insets(20));
        infoBox.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #e0e0e0; -fx-border-width: 1px;");
        infoBox.setMaxWidth(400);
        
        Label infoTitle = new Label("Quick Info");
        infoTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        infoTitle.setStyle("-fx-text-fill: #333333;");
        
        Label info1 = new Label("• Browse products in the Products section");
        Label info2 = new Label("• View your cart in the My Cart section");
        Label info3 = new Label("• Checkout in the Checkout section");
        
        info1.setStyle("-fx-text-fill: #666666;");
        info2.setStyle("-fx-text-fill: #666666;");
        info3.setStyle("-fx-text-fill: #666666;");
        
        infoBox.getChildren().addAll(infoTitle, info1, info2, info3);
        
        defaultContent.getChildren().addAll(welcomeMsg, instruction, separator, infoBox);
        
        contentArea.getChildren().add(defaultContent);
    }
    public void updateBalance(double newBalance) {
        balanceLabel.setText(String.format("$%.2f", newBalance));
    }
    
    // Getters
    public Button getViewProductsBtn() { return viewProductsBtn; }
    public Button getViewCartBtn() { return viewCartBtn; }
    public Button getCheckoutBtn() { return checkoutBtn; }
    public Button getLogoutBtn() { return logoutBtn; }
    public Label getWelcomeLabel() { return welcomeLabel; }
    public Label getBalanceLabel() { return balanceLabel; }
    public StackPane getContentArea() { return contentArea; }
}
