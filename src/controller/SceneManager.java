package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.UserRepository;
import view.LoginView;

public class SceneManager {
private static Stage primaryStage;
	
	public static void setStage(Stage stage) {
		primaryStage = stage;
	}
	public static void changeToLogin() {
		UserRepository userRepo = new UserRepository();
		LoginView view = new LoginView();
		LoginController controller = new LoginController(view, userRepo);
		Scene scene = new Scene(view,400,400);
		primaryStage.setScene(scene);
	}
}
