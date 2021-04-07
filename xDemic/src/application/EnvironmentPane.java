package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class EnvironmentPane extends Pane {
	private MainView mainView;
	
	private Pane sandbox;
	private Pane configWindow;
	
	private int paneSize=600;
	private int gridSize=0;
	private int gridSizeOld=0;
	private int squareSize;

	public EnvironmentPane(MainView mainView) {
		this.mainView = mainView;
		
		sandbox = new Pane();
		sandbox.setPrefWidth(paneSize);
		sandbox.setPrefHeight(paneSize);
		getChildren().add(sandbox);
		
		update();
	}
	
	public void update() {
		sandbox.getChildren().clear();
		
		gridSize = 1;
		while(mainView.getSimulation().getDeviceList().size() > (gridSize*gridSize)) {
			gridSize++;
		}
		squareSize=paneSize/gridSize;
		
		//add devices and connections
		for(int i=0; i<mainView.getSimulation().getDeviceList().size(); i++) {
			Device device = mainView.getSimulation().getDeviceList().get(i);
			
			double x;
			double y;
			double temp;
			int col=0;
			int row=0;
			
			if(gridSize == gridSizeOld) {
				if(device.getX() == -1 && device.getY() == -1) {
					col = i%gridSize;
					x = squareSize*col;
					temp = i;
					while(temp >= gridSize) {
						temp=temp-gridSize;
						row++;
					}
					y = squareSize*row;
				} else {
					x = device.getX();
					y = device.getY();
				}
			} else {
				col = i%gridSize;
				x = squareSize*col;
				temp = i;
				while(temp >= gridSize) {
					temp=temp-gridSize;
					row++;
				}
				y = squareSize*row;
			}
			
			double size = squareSize*0.97;
			Rectangle r = new Rectangle();
			
			sandbox.getChildren().add(r);
			
			device.setX(x);
			device.setY(y);
			device.setSize(size);
			device.setR(r);
			
			device.draw();
		}
		
		gridSizeOld = gridSize;
		
		for(int i=0; i<mainView.getSimulation().getConnectionList().size(); i++) {
			Device device1 = mainView.getSimulation().getConnectionList().get(i).getConnection().get(0);
			Device device2 = mainView.getSimulation().getConnectionList().get(i).getConnection().get(1);
			int d1 = mainView.getSimulation().getDeviceList().indexOf(device1);
			int d2 = mainView.getSimulation().getDeviceList().indexOf(device2);
			Line line = new Line(mainView.getSimulation().getDeviceList().get(d1).getX()+(squareSize/2), mainView.getSimulation().getDeviceList().get(d1).getY()+(squareSize/2), mainView.getSimulation().getDeviceList().get(d2).getX()+(squareSize/2), mainView.getSimulation().getDeviceList().get(d2).getY()+(squareSize/2));
			sandbox.getChildren().add(line);
		}
	}
	
	public void add() {
		if(mainView.getState() == MainView.EDITING) {
			configWindow = new Pane();
			
			Scene scene = new Scene(configWindow);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			VBox options = new VBox();
			options.setSpacing(5);
			
			Text resistanceLabel = new Text("Resistance");
			Slider resistance = new Slider(0, 100, 10);
			resistance.setShowTickLabels(true);
			resistance.setShowTickMarks(true);
			
			Text timeToPatchLabel = new Text("Time to Patch");
			Slider timeToPatch = new Slider(0, 100, 10);
			timeToPatch.setShowTickLabels(true);
			timeToPatch.setShowTickMarks(true);
			timeToPatch.setSnapToTicks(true);
			
			Text numberLabel = new Text("Number to add");
			Slider number = new Slider(1, 1000, 1);
			number.setShowTickLabels(true);
			number.setShowTickMarks(true);
			number.setSnapToTicks(true);
			
			Button apply = new Button("Apply");
			apply.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					for(int i=0; i<number.getValue(); i++) {
						mainView.getSimulation().getDeviceList().add(new Device(resistance.getValue(), (int)timeToPatch.getValue()));
					}
					mainView.setCenter(mainView.getOutput());
					mainView.draw();
				}
			});
		
			Button cancel = new Button("Cancel");
			cancel.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					mainView.setCenter(mainView.getOutput());
					update();
				}
			});
			
			options.getChildren().addAll(resistanceLabel, resistance, timeToPatchLabel, timeToPatch, numberLabel, number, apply, cancel);
			configWindow.getChildren().add(options);
			mainView.setCenter(configWindow);
		}
	}
}