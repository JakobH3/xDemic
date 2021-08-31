package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Main extends Application {	
	@Override
	public void start(Stage stage) {
		try {
			MainView mainView = new MainView();
			
			Scene scene = new Scene(mainView, 1200, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			stage.getIcons().add(new Image("file:logo.png"));
			stage.setTitle("xDemic Malware Simulation");
			stage.setScene(scene);
			stage.show();
			
			mainView.draw();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}