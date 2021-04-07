 package application;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Tools extends ToolBar {
	private MainView mainView;
	
	public Tools(MainView mainView) {
		this.mainView=mainView;
		
		Button addMalware = new Button("New Malware");
		addMalware.setOnAction(this::handleMalware);
		
		Button addDevice = new Button("New Device");
		addDevice.setOnAction(this::handleDevice);
        
        Button start = new Button("Start");
        start.setOnAction(this::handleStart);
		
		Button stop = new Button("Stop");
		stop.setOnAction(this::handleStop);
		
		Button example = new Button("Load Example");
        example.setOnAction(this::handleExample);
        
        Button clear = new Button("Clear");
        clear.setOnAction(this::handleClear);
		
		getItems().addAll(addMalware, addDevice, start, stop, example, clear);
		setId("toolbar");
	}
	
	private void handleMalware(ActionEvent actionEvent) {
        mainView.getMalware().add();
    }
	
	private void handleDevice(ActionEvent actionEvent) {
        mainView.getEnvironment().add();
    }
	
	private void handleStart(ActionEvent actionEvent) {
        mainView.getSimulator().start();
    }
	
	private void handleStop(ActionEvent actionEvent) {
        mainView.getSimulator().stop();
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
}