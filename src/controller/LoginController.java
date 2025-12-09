package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import model.User;
import repository.UserRepository;
import view.LoginView;

public class LoginController {
	private LoginView view;
	private UserRepository userRepository;
	
	public LoginController(LoginView view, UserRepository userRepository) {
		super();
		this.view = view;
		this.userRepository = userRepository;
		
		view.getLoginButton().setOnAction(e -> handleLogin());
		view.getGoToRegister().setOnAction(e -> SceneManager.changeToRegister());
	}
	public void handleLogin() {
		String email = view.getEmailTf().getText().trim();
		String password = view.getPasswordTf().getText().trim();
		
		User user = userRepository.loginUser(email, password);
		if(user != null) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION, "Success!");
			alert.show();
			SceneManager.changeToRegister();
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid credentials!");
			alert.show();
			view.getEmailTf().clear();
			view.getPasswordTf().clear();
		}
	}
}
