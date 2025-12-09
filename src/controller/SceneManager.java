package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.UserRepository;
import view.LoginView;
import view.RegisterView;

public class SceneManager {
private static Stage primaryStage;
	
	public static void setStage(Stage stage) {
		primaryStage = stage;
	}
	
	public static void changeToLogin() {
		UserRepository userRepo = new UserRepository();
		LoginView view = new LoginView();
		LoginController controller = new LoginController(view, userRepo);
		Scene scene = new Scene(view,900,700);
		primaryStage.setScene(scene);
	}
	public static void changeToRegister() {
		UserRepository userRepo = new UserRepository();
		RegisterView view = new RegisterView();
		RegisterController controller = new RegisterController(view, userRepo);
		Scene scene = new Scene(view,900,700);
		primaryStage.setScene(scene);
	}
	
}
