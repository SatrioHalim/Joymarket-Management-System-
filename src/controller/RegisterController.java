package controller;

import javafx.scene.control.Alert;
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
		String confirmPassword = view.getConfirmPasswordTf().getText().trim();
		String phone = view.getPhoneTf().getText().trim();
		String address = view.getAddressTf().getText().trim();
		String gender = view.getSelectedGender();
		
		boolean inputValidation = true;
	
		// Validasi Full Name
        if (fullname.isEmpty()) {
        	Alert alert = new Alert(Alert.AlertType.ERROR, "Fullname can't be empty!");
			alert.show();
        	inputValidation = false;
        	this.view.getFullnameTf().clear();
        } else if (fullname.length() < 3) {
        	Alert alert = new Alert(Alert.AlertType.ERROR, "Fullname at least 3 characters!");
			alert.show();
        	inputValidation = false;
        	this.view.getFullnameTf().clear();
        }
        
        // Validasi Email
        if (email.isEmpty()) {
        	Alert alert = new Alert(Alert.AlertType.ERROR, "Email can't be empty!");
			alert.show();
        	inputValidation = false;
        } else if (!email.endsWith("@gmail.com")) {
        	Alert alert = new Alert(Alert.AlertType.ERROR, "Email must end with @gmail.com!");
			alert.show();
        	inputValidation = false;
        	this.view.getEmailTf().clear();
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
        	this.view.getPasswordTf().clear();
        }
        
        // Validasi confirm password
        if (!confirmPassword.equals(password)) {
        	Alert alert = new Alert(Alert.AlertType.ERROR, "Confirm Password doesn't match!");
			alert.show();
        	inputValidation = false;
        	this.view.getConfirmPasswordTf().clear();
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
        	this.view.getPhoneTf().clear();
        } else if (phone.length() < 10 || phone.length() > 15) {
        	Alert alert = new Alert(Alert.AlertType.ERROR, "Phone number must be 10-15 digits!");
			alert.show();
        	inputValidation = false;
        	this.view.getPhoneTf().clear();
        }
        
        // Validasi Address
        if (address.isEmpty()) {
        	Alert alert = new Alert(Alert.AlertType.ERROR, "Address can't be empty!");
			alert.show();
        	inputValidation = false;
        } 

		if (inputValidation) {
			boolean registerStatus = userRepository.registerCustomer(fullname, email, password, phone, address,gender);
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
    
	// Regex iseng2 buat ngecek format email
//    private boolean isValidEmail(String email) {
//        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
//        return email.matches(emailRegex);
//    }
	
}
