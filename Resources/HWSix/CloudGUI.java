import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class CloudGUI extends Application {	
	private int size=10;
	private double points[][];
	
	@Override	
	public void start(Stage primaryStage) throws Exception {
		Pane pane = new CloudPane();
		Scene scene = new Scene(pane, 650, 300);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Point Cloud");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public double[][] getPoints() {
		return points;
	}
	
	public int getSize() {
		return size;
	}
}