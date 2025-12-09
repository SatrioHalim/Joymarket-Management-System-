package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LoginView extends VBox{
	private TextField emailTf;
	private PasswordField passwordTf;
	private Button loginButton;
	private Button goToRegister;
	
	public LoginView() {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(15); // Spacing lebih besar
        this.setPadding(new Insets(20)); // Padding di sekitar VBox
        
        // Title dengan font besar dan margin bawah
        Label titleLbl = new Label("Login Page");
        titleLbl.setFont(Font.font("System", FontWeight.BOLD, 28));
        VBox.setMargin(titleLbl, new Insets(0, 0, 30, 0)); // Margin bawah lebih besar
        
        // Container untuk Email
        VBox emailContainer = new VBox(5); // Spacing kecil antar anak
        emailContainer.setAlignment(Pos.CENTER_LEFT);
        emailContainer.setPrefWidth(400);
        emailContainer.setMaxWidth(400);
        
        Label emailLabel = new Label("Input Email");
        emailLabel.setFont(Font.font("System", FontWeight.MEDIUM, 14));
        
        emailTf = new TextField();
        emailTf.setPrefWidth(400);
        emailTf.setMaxWidth(400);
        emailTf.setPrefHeight(35); // Tinggi field
        emailTf.setPromptText("Enter your email");
        
        emailContainer.getChildren().addAll(emailLabel, emailTf);
        
        // Container untuk Password
        VBox passwordContainer = new VBox(5); // Spacing kecil antar anak
        passwordContainer.setAlignment(Pos.CENTER_LEFT);
        passwordContainer.setPrefWidth(400);
        passwordContainer.setMaxWidth(400);
        
        Label passwordLabel = new Label("Input Password");
        passwordLabel.setFont(Font.font("System", FontWeight.MEDIUM, 14));
        
        passwordTf = new PasswordField();
        passwordTf.setPrefWidth(400);
        passwordTf.setMaxWidth(400);
        passwordTf.setPrefHeight(35); // Tinggi field
        passwordTf.setPromptText("Enter your password");
        
        passwordContainer.getChildren().addAll(passwordLabel, passwordTf);
        
        // Container untuk Button
        VBox buttonContainer = new VBox(10); // Spacing antar button
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPrefWidth(400);
        buttonContainer.setMaxWidth(400);
        
        loginButton = new Button("Login");
        loginButton.setPrefWidth(400);
        loginButton.setPrefHeight(40);
        loginButton.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        
        goToRegister = new Button("Go To Register");
        goToRegister.setPrefWidth(400);
        goToRegister.setPrefHeight(40);
        goToRegister.setStyle("-fx-font-size: 14px;");
        
        buttonContainer.getChildren().addAll(loginButton, goToRegister);
        
        this.getChildren().addAll(titleLbl, emailContainer, passwordContainer, buttonContainer);
    }

	// Getter
	public TextField getEmailTf() {
		return this.emailTf;
	}

	public PasswordField getPasswordTf() {
		return this.passwordTf;
	}

	public Button getLoginButton() {
		return this.loginButton;
	}

	public Button getGoToRegister() {
		return this.goToRegister;
	}
	
	
}
