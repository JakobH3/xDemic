package application;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Tools extends VBox {
	private MainView mainView;
	
	public Tools(MainView mainView) {
		this.mainView=mainView;
		
		// create menu bar
		MenuBar menu = new MenuBar();
			Menu file = new Menu("File");
				Menu add = new Menu("New");
					MenuItem malware = new MenuItem("Malware");
					malware.setOnAction(this::handleMalware);
					MenuItem device = new MenuItem("Device");
					device.setOnAction(this::handleDevice);
				add.getItems().addAll(malware, device);
				
				Menu load = new Menu("Load");
					MenuItem ex = new MenuItem("Example 1");
					ex.setOnAction(this::handleExample);
				load.getItems().addAll(ex);
					
				MenuItem clear = new MenuItem("Clear");
				clear.setOnAction(this::handleClear);
			file.getItems().addAll(add, load, clear);
			
			Menu help = new Menu("Help");
				MenuItem helpDevices = new MenuItem("Devices");
				helpDevices.setOnAction(this::handleHelp);
			help.getItems().addAll(helpDevices);
		menu.getMenus().addAll(file, help);
		
		// create tool bar
		ToolBar tools = new ToolBar();
        
        Button start = new Button();
        start.setOnAction(this::handleStart);
        start.setGraphic(makeImage(new Image("file:play.png")));
		
		Button stop = new Button();
		stop.setOnAction(this::handleStop);
		stop.setGraphic(makeImage(new Image("file:pause.png")));
		
		Button config = new Button();
		config.setOnAction(this::handleConfig);
		config.setGraphic(makeImage(new Image("file:config.png")));
		
		tools.getItems().addAll(start, stop, config);
		
		getChildren().addAll(menu, tools);
	}
	
	private void handleMalware(ActionEvent actionEvent) {
        mainView.getMalware().add();
    }
	
	private void handleDevice(ActionEvent actionEvent) {
        mainView.getEnvironment().add();
    }
	
	private void handleExample(ActionEvent actionEvent) {
		if(mainView.getState()==MainView.EDITING) {
			mainView.getSimulation().loadExample();
			mainView.draw();
		}
	}
	
	private void handleClear(ActionEvent actionEvent) {
		mainView.getSimulation().clear();
		mainView.draw();
	}
	
	private void handleHelp(ActionEvent actionEvent) {
		VBox helpList = new VBox();
		helpList.setSpacing(10);
		helpList.getChildren().add(new Text("Shift drag to move devices"));
		helpList.getChildren().add(new Text("Ctrl drag to connect devices"));
		
		Scene helpScene = new Scene(helpList, 400, 400);
		
		Stage helpStage = new Stage();
		helpStage.setTitle("xDemic Help");
		helpStage.setScene(helpScene);
		helpStage.show();
	}
	
	private void handleStart(ActionEvent actionEvent) {
        mainView.getSimulator().start();
    }
	
	private void handleStop(ActionEvent actionEvent) {
        mainView.getSimulator().stop();
    }
	
	private void handleConfig(ActionEvent actionEvent) {
		// create config window
		System.out.println("Config window");
	}
	
	private ImageView makeImage(Image image) {
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(9);
		imageView.setFitHeight(10);
		return imageView;
	}
}