package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class RegisterView extends VBox{
	private TextField fullnameTf;
    private TextField emailTf;
    private PasswordField passwordTf;
    private TextField phoneTf;
    private TextField addressTf;
    private Button submitButton;
    private Button backToLoginButton;    
    public RegisterView() {
        // Styling utama VBox
    	
        this.setAlignment(Pos.CENTER);
        this.setSpacing(15);
        this.setPadding(new Insets(10));
                
        // Title
        Label titleLbl = new Label("Registration Page");
        titleLbl.setFont(Font.font("System", FontWeight.BOLD, 32));
        titleLbl.setStyle("-fx-text-fill: #2c3e50;");
        
        // Full Name Field
        Label fullnameLabel = new Label("Full Name:");
        fullnameLabel.setFont(Font.font("System", 14));
        fullnameLabel.setStyle("-fx-text-fill: #34495e;");
        
        fullnameTf = new TextField();
        fullnameTf.setPromptText("Enter your full name");
        fullnameTf.setPrefWidth(400);
        fullnameTf.setMaxWidth(400);
        fullnameTf.setPrefHeight(40);
        fullnameTf.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #bdc3c7;");
        
        
        // Email Field
        Label emailLabel = new Label("Email:");
        emailLabel.setFont(Font.font("System", 14));
        emailLabel.setStyle("-fx-text-fill: #34495e;");
        
        emailTf = new TextField();
        emailTf.setPromptText("Enter your email");
        emailTf.setPrefWidth(400);
        emailTf.setMaxWidth(400);
        emailTf.setPrefHeight(40);
        emailTf.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #bdc3c7;");
        
        
        // Password Field
        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(Font.font("System", 14));
        passwordLabel.setStyle("-fx-text-fill: #34495e;");
        
        passwordTf = new PasswordField();
        passwordTf.setPromptText("Enter your password (min. 6 characters)");
        passwordTf.setPrefWidth(400);
        passwordTf.setMaxWidth(400);
        passwordTf.setPrefHeight(40);
        passwordTf.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #bdc3c7;");
        
        // Phone Field
        Label phoneLabel = new Label("Phone Number:");
        phoneLabel.setFont(Font.font("System", 14));
        phoneLabel.setStyle("-fx-text-fill: #34495e;");
        
        phoneTf = new TextField();
        phoneTf.setPromptText("Enter your phone number");
        phoneTf.setPrefWidth(400);
        phoneTf.setMaxWidth(400);
        phoneTf.setPrefHeight(40);
        phoneTf.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #bdc3c7;");
       
        
        // Address Field (bisa juga menggunakan TextArea untuk multi-line)
        Label addressLabel = new Label("Address:");
        addressLabel.setFont(Font.font("System", 14));
        addressLabel.setStyle("-fx-text-fill: #34495e;");
        
        addressTf = new TextField();
        addressTf.setPromptText("Enter your address");
        addressTf.setPrefWidth(400);
        addressTf.setMaxWidth(400);
        addressTf.setPrefHeight(40);
        addressTf.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #bdc3c7;");
        
        
        // Button Container
        VBox buttonContainer = new VBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPrefWidth(400);
        buttonContainer.setMaxWidth(400);
        
        // Submit Button
        submitButton = new Button("Submit");
        submitButton.setPrefWidth(300);
        submitButton.setPrefHeight(30);
        submitButton.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        
        // Back to Login Button
        backToLoginButton = new Button("Back to Login");
        backToLoginButton.setPrefWidth(300);
        backToLoginButton.setPrefHeight(30);
        backToLoginButton.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        
        buttonContainer.getChildren().addAll(submitButton, backToLoginButton);
        
        // Tambahkan semua komponen ke VBox utama
        this.getChildren().addAll(
            titleLbl,
            fullnameLabel, fullnameTf,
            emailLabel, emailTf,
            passwordLabel, passwordTf,
            phoneLabel, phoneTf,
            addressLabel, addressTf,
            buttonContainer
        );
    }
    
    // Getter methods
    public TextField getFullnameTf() {
        return fullnameTf;
    }
    
    public TextField getEmailTf() {
        return emailTf;
    }
    
    public PasswordField getPasswordTf() {
        return passwordTf;
    }
    
    public TextField getPhoneTf() {
        return phoneTf;
    }
    
    public TextField getAddressTf() {
        return addressTf;
    }
    
    public Button getSubmitButton() {
        return submitButton;
    }
    
    public Button getBackToLoginButton() {
        return backToLoginButton;
    }

	
    
}
