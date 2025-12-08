package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginView extends VBox{
	private TextField emailTf;
	private PasswordField passwordTf;
	private Button loginButton;
	private Button goToRegister;
	
	public LoginView() {
		// TODO Auto-generated constructor stub
		this.setAlignment(Pos.CENTER);
		this.setSpacing(10);
		
		Label titleLbl = new Label("Login Page");
		emailTf = new TextField();
		emailTf.setPromptText("Email");
		emailTf.maxWidth(200);
		
		passwordTf = new PasswordField();
		passwordTf.setPromptText("Password");
		passwordTf.maxWidth(200);

		loginButton = new Button("Login");
		goToRegister = new Button("Go To Register");
		
		this.getChildren().addAll(titleLbl, emailTf, passwordTf, loginButton, goToRegister);
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
