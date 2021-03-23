/* 
 * JavaFX Notes
 * --module-path "D:\Documents\GitHub\xDemic\javafx-sdk-15.0.1\lib" --add-modules javafx.controls,javafx.fxml
 * This needs to be added to the run config arguments
 */

package application;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;


public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		ArrayList<Device> deviceList = new ArrayList<Device>();
		ArrayList<Malware> malwareList = new ArrayList<Malware>();
		
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,1600,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.getIcons().add(new Image("file:logo.png"));
			stage.setScene(scene);
			stage.show();
			
			System.out.println("\n> Welcome to xDemic!\n");
			
			for(int i=0; i<100; i++) {
				deviceList.add(new Device(i, Integer.MAX_VALUE, 0));
			}
			malwareList.add(new Malware("Test", 10, 10, Integer.MAX_VALUE));
			
			//TODO make xDemic execute on button press in GUI
			new xDemic(deviceList, malwareList);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
}