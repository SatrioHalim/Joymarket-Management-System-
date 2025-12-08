package main;

import controller.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("JoyMarket System");
		SceneManager.setStage(primaryStage);
		SceneManager.changeToLogin();
		primaryStage.show();
	}

}
