package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import repository.UserRepository;
import view.RegisterView;

public class RegisterController {
	private RegisterView view;
	private UserRepository userRepository;
	
	public RegisterController(RegisterView view, UserRepository userRepository) {
		super();
		this.view = view;
		this.userRepository = userRepository;
		
		view.getBackToLoginButton().setOnAction(e -> SceneManager.changeToLogin());
		view.getSubmitButton().setOnAction(e -> handleRegister());
	}
	
	public void handleRegister() {
		String fullname = view.getFullnameTf().getText().trim();
		String email = view.getEmailTf().getText().trim();
		String password = view.getPasswordTf().getText().trim();
		String phone = view.getPhoneTf().getText().trim();
		String address = view.getAddressTf().getText().trim();
		
		boolean inputValidation = true;
	
		// Validasi Full Name
        if (fullname.isEmpty()) {
        	Alert alert = new Alert(Alert.AlertType.ERROR, "Fullname can't be empty!");
			alert.show();
        	inputValidation = false;
        } else if (fullname.length() < 3) {
        	Alert alert = new Alert(Alert.AlertType.ERROR, "Fullname at least 3 characters!");
			alert.show();
        	inputValidation = false;
        }
        
        // Validasi Email
        if (email.isEmpty()) {
        	Alert alert = new Alert(Alert.AlertType.ERROR, "Email can't be empty!");
			alert.show();
        	inputValidation = false;
        } else if (!isValidEmail(email)) {
        	Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid email Format!");
			alert.show();
        	inputValidation = false;
        }
        
        // Validasi Password
        if (password.isEmpty()) {
        	Alert alert = new Alert(Alert.AlertType.ERROR, "Password can't be empty!");
			alert.show();
        	inputValidation = false;
        } else if (password.length() < 6) {
        	Alert alert = new Alert(Alert.AlertType.ERROR, "Password at least 6 characters!");
			alert.show();
        	inputValidation = false;
        }
        
        // Validasi Phone
        if (phone.isEmpty()) {
        	Alert alert = new Alert(Alert.AlertType.ERROR, "Phone number can't be empty!");
			alert.show();
        	inputValidation = false;
        } else if (!phone.matches("\\d+")) {
        	Alert alert = new Alert(Alert.AlertType.ERROR, "Phone must contain number!");
			alert.show();
        	inputValidation = false;
        } else if (phone.length() < 10 || phone.length() > 15) {
        	Alert alert = new Alert(Alert.AlertType.ERROR, "Phone number must be 10-15 digits!");
			alert.show();
        	inputValidation = false;
        }
        
        // Validasi Address
        if (address.isEmpty()) {
        	Alert alert = new Alert(Alert.AlertType.ERROR, "Address can't be empty!");
			alert.show();
        	inputValidation = false;
        } 

		if (inputValidation) {
			boolean registerStatus = userRepository.registerCustomer(fullname, email, password, phone, address);
			if(registerStatus) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION, "Success!");
				alert.show();
				SceneManager.changeToLogin();
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR, "Registration failed. Email might already exist.");
				alert.show();
				view.getEmailTf().clear();
				view.getPasswordTf().clear();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Please check your input again!");
			alert.show();
		}
		
	}
    
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }
	
}
