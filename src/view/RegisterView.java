package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class RegisterView extends VBox{
	private TextField fullnameTf;
    private TextField emailTf;
    private PasswordField passwordTf;
    private PasswordField confirmPasswordTf;
    private TextField phoneTf;
    private TextField addressTf;
    private Button submitButton;
    private Button backToLoginButton;    
    private ToggleGroup genderGroup;
    private RadioButton maleRadio;
    private RadioButton femaleRadio;
    
    public RegisterView() {
        // Styling utama VBox
    	
        this.setAlignment(Pos.CENTER);
        this.setSpacing(15);
        this.setPadding(new Insets(10));
                
        // Title
        Label titleLbl = new Label("Registration Page");
        titleLbl.setFont(Font.font("System", FontWeight.BOLD, 32));
        titleLbl.setStyle("-fx-text-fill: #2c3e50;");
        VBox.setMargin(titleLbl, new Insets(0, 0, 10, 0));
        
        // Container buat form
        VBox formContainer = new VBox(15);
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setMaxWidth(500);
        formContainer.setMinWidth(300);
        
        // Full Name Field
        HBox fullnameRow = new HBox(10);
        fullnameRow.setAlignment(Pos.CENTER_LEFT);
        
        Label fullnameLabel = new Label("Full Name:");
        fullnameLabel.setFont(Font.font("System", FontWeight.MEDIUM, 14));
        fullnameLabel.setPrefWidth(120); 
        
        fullnameTf = new TextField();
        fullnameTf.setPromptText("Enter your full name");
        fullnameTf.setPrefWidth(250);
        fullnameTf.setPrefHeight(35);
        fullnameTf.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #bdc3c7;");
        HBox.setHgrow(fullnameTf, Priority.ALWAYS);
        
        fullnameRow.getChildren().addAll(fullnameLabel, fullnameTf);
        
        // Email Field
        HBox emailRow = new HBox(10);
        emailRow.setAlignment(Pos.CENTER_LEFT);
        
        Label emailLabel = new Label("Email:");
        emailLabel.setFont(Font.font("System", FontWeight.MEDIUM, 14));
        emailLabel.setPrefWidth(120);
        
        emailTf = new TextField();
        emailTf.setPromptText("Enter your email");
        emailTf.setPrefWidth(250);
        emailTf.setPrefHeight(35);
        emailTf.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #bdc3c7;");
        HBox.setHgrow(emailTf, Priority.ALWAYS);
        
        emailRow.getChildren().addAll(emailLabel, emailTf);
        
        // Password Field
        HBox passwordRow = new HBox(10);
        passwordRow.setAlignment(Pos.CENTER_LEFT);
        
        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(Font.font("System", FontWeight.MEDIUM, 14));
        passwordLabel.setPrefWidth(120);
        
        passwordTf = new PasswordField();
        passwordTf.setPromptText("Enter your password");
        passwordTf.setPrefWidth(250);
        passwordTf.setPrefHeight(35);
        passwordTf.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #bdc3c7;");
        HBox.setHgrow(passwordTf, Priority.ALWAYS);
        
        passwordRow.getChildren().addAll(passwordLabel, passwordTf);
        
     // Password Field
        HBox confirmPasswordRow = new HBox(10);
        confirmPasswordRow.setAlignment(Pos.CENTER_LEFT);
        
        Label confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordLabel.setFont(Font.font("System", FontWeight.MEDIUM, 14));
        confirmPasswordLabel.setPrefWidth(120);
        
        confirmPasswordTf = new PasswordField();
        confirmPasswordTf.setPromptText("Confirm Password here");
        confirmPasswordTf.setPrefWidth(250);
        confirmPasswordTf.setPrefHeight(35);
        confirmPasswordTf.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #bdc3c7;");
        HBox.setHgrow(confirmPasswordTf, Priority.ALWAYS);
        
        confirmPasswordRow.getChildren().addAll(confirmPasswordLabel, confirmPasswordTf);
        
        // Phone Field
        HBox phoneRow = new HBox(10);
        phoneRow.setAlignment(Pos.CENTER_LEFT);
        
        Label phoneLabel = new Label("Phone:");
        phoneLabel.setFont(Font.font("System", FontWeight.MEDIUM, 14));
        phoneLabel.setPrefWidth(120);
        
        phoneTf = new TextField();
        phoneTf.setPromptText("Enter your phone number");
        phoneTf.setPrefWidth(250);
        phoneTf.setPrefHeight(35);
        phoneTf.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #bdc3c7;");
        HBox.setHgrow(phoneTf, Priority.ALWAYS);
        
        phoneRow.getChildren().addAll(phoneLabel, phoneTf);
        
        // Gender Radio Button
        HBox genderRow = new HBox(10);
        genderRow.setAlignment(Pos.CENTER_LEFT);
        
        Label genderLabel = new Label("Gender:");
        genderLabel.setFont(Font.font("System", FontWeight.MEDIUM, 14));
        genderLabel.setPrefWidth(120);
        
        // ToggleGroup biar cuman satu yang bisa dipilih
        genderGroup = new ToggleGroup();
        
        maleRadio = new RadioButton("Male");
        maleRadio.setToggleGroup(genderGroup);
        maleRadio.setUserData("Male");
        
        femaleRadio = new RadioButton("Female");
        femaleRadio.setToggleGroup(genderGroup);
        femaleRadio.setUserData("Female");
        
        // Set default selection (optional)
        maleRadio.setSelected(true);
        
        // HBox untuk RadioButton
        HBox radioContainer = new HBox(15);
        radioContainer.setAlignment(Pos.CENTER_LEFT);
        radioContainer.getChildren().addAll(maleRadio, femaleRadio);
        HBox.setHgrow(radioContainer, Priority.ALWAYS);
        
        genderRow.getChildren().addAll(genderLabel, radioContainer);
        
        // Address Field
        HBox addressRow = new HBox(10);
        addressRow.setAlignment(Pos.CENTER_LEFT);
        
        Label addressLabel = new Label("Address:");
        addressLabel.setFont(Font.font("System", FontWeight.MEDIUM, 14));
        addressLabel.setPrefWidth(120);
        
        addressTf = new TextField();
        addressTf.setPromptText("Enter your address");
        addressTf.setPrefWidth(250);
        addressTf.setPrefHeight(35);
        addressTf.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #bdc3c7;");
        HBox.setHgrow(addressTf, Priority.ALWAYS);
        
        addressRow.getChildren().addAll(addressLabel, addressTf);
        
        
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
        formContainer.getChildren().addAll(
        		fullnameRow,
        		emailRow,
                passwordRow,
                confirmPasswordRow,
                phoneRow,
                genderRow,
                addressRow,
                buttonContainer
        );
        this.getChildren().addAll(titleLbl, formContainer);
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

	public ToggleGroup getGenderGroup() {
		return genderGroup;
	}

	public void setGenderGroup(ToggleGroup genderGroup) {
		this.genderGroup = genderGroup;
	}

	public RadioButton getMaleRadio() {
		return maleRadio;
	}

	public void setMaleRadio(RadioButton maleRadio) {
		this.maleRadio = maleRadio;
	}

	public RadioButton getFemaleRadio() {
		return femaleRadio;
	}

	public void setFemaleRadio(RadioButton femaleRadio) {
		this.femaleRadio = femaleRadio;
	}
	public String getSelectedGender() {
        RadioButton selectedRadio = (RadioButton) genderGroup.getSelectedToggle();
        if (selectedRadio != null) {
            return selectedRadio.getText();
        }
        return "Male";
    }

	public PasswordField getConfirmPasswordTf() {
		return confirmPasswordTf;
	}

	public void setConfirmPasswordTf(PasswordField confirmPasswordTf) {
		this.confirmPasswordTf = confirmPasswordTf;
	}
	
    
}
