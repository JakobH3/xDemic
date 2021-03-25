package application;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Tools extends ToolBar {
	private MainView mainView;
	
	public Tools(MainView mainView) {
		this.mainView=mainView;
		
		Button example = new Button("Load Example");
        example.setOnAction(this::handleExample);
        Button start = new Button("Start");
        start.setOnAction(this::handleStart);
        Button stop = new Button("Stop");
        stop.setOnAction(this::handleStop);

        this.getItems().addAll(example, start, stop);
	}
	
	private void handleExample(ActionEvent actionEvent) {
		this.mainView.getSimulation().loadExample();
	}
	
	private void handleStart(ActionEvent actionEvent) {
        this.mainView.getSimulator().start();
    }
	
	private void handleStop(ActionEvent actionEvent) {
        this.mainView.getSimulator().stop();
    }
}