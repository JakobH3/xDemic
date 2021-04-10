package application;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;

public class EnvironmentPane extends Pane {
	private MainView mainView;
	
	private Pane sandbox;
	
	private int paneSize=600;
	private int gridSize=0;
	private int gridSizeOld=0;
	private int squareSize=0;
	private int squareSizeOld=0;
	
	private Line tempLine;
	private Device tempDevice;

	public EnvironmentPane(MainView mainView) {
		this.mainView = mainView;
		
		setMinWidth(400);
		setMinHeight(400);
		
		sandbox = new Pane();
		getChildren().add(sandbox);
	}
	
	public void update() {
		sandbox.getChildren().clear();
		
		if(mainView.getWidth()*2/5 > mainView.getHeight()*2/3-mainView.getTools().getHeight()) paneSize = (int) (mainView.getHeight()*2/3-mainView.getTools().getHeight());
		else paneSize = (int) mainView.getWidth()*2/5;
		if(paneSize < 400) paneSize = 400;
		setPrefWidth(paneSize);
		setPrefHeight(paneSize);
		
		// determine appropriate grid
		gridSize = 1;
		while(mainView.getSimulation().getDeviceList().size() > (gridSize*gridSize)) {
			gridSize++;
		}
		squareSize=paneSize/gridSize;
		
		// add devices
		for(int i=0; i<mainView.getSimulation().getDeviceList().size(); i++) {
			Device device = mainView.getSimulation().getDeviceList().get(i);
			double x, y;
			
			// determine position of device
			if(gridSize == gridSizeOld) {
				if((device.getX() == -1 && device.getY() == -1) || squareSize != squareSizeOld) {
					x = calcX(i);
					y = calcY(i);
				} else {
					x = device.getX();
					y = device.getY();
				}
			} else {
				x = calcX(i);
				y = calcY(i);
			}
			
			double size = squareSize*0.4;
			Circle c = new Circle();
			c.setCursor(Cursor.HAND);
			
			// device selected
		    c.setOnMousePressed((e) -> {
		    	if (e.isShiftDown()) {
		    		// prepare to move device
		    		c.setStrokeWidth(5);
		    		c.setStroke(Color.BLACK);
		    		c.toFront();
		    	} else if (e.isControlDown()) {
	            	// prepare to make connection
	            	tempDevice = device;
	            	tempLine = new Line();
	            	sandbox.getChildren().add(tempLine);
	            }
		    });
		    
		    // device dragged
			c.setOnMouseDragged((e) -> {
				if (e.isShiftDown()) {
					// move device within the bounds
					if(e.getX() > squareSize/2 && e.getX() < paneSize-squareSize/2) {
						c.setCenterX(e.getX());
						device.setX(e.getX());
					}
					if(e.getY() > squareSize/2 && e.getY() < paneSize-squareSize/2) {
						c.setCenterY(e.getY());
						device.setY(e.getY());
					}
					c.setOpacity(1);
	            } else if (e.isControlDown()) {
	                // show new connection
	            	tempLine.startXProperty().bind(tempDevice.getC().centerXProperty());
	            	tempLine.startYProperty().bind(tempDevice.getC().centerYProperty());
	    		    
					if(e.getX() > squareSize/2 && e.getX() < paneSize-squareSize/2) {
						tempLine.setEndX(e.getX());
					}
					if(e.getY() > squareSize/2 && e.getY() < paneSize-squareSize/2) {
						tempLine.setEndY(e.getY());
					}
	    		    
					tempLine.setStroke(Color.BLACK);
					tempLine.setStrokeWidth(5);
					tempLine.setStrokeLineCap(StrokeLineCap.ROUND);
	            }
			});
			
			// device dropped
			c.setOnMouseReleased((e) -> {
				if (e.isShiftDown()) {
					// move device into nearest grid
					c.setStrokeWidth(0);
					device.setX(squareSize/2 + squareSize * (int) (device.getX()/squareSize));
					device.setY(squareSize/2 + squareSize * (int) (device.getY()/squareSize));
					device.draw();
	            } else if (e.isControlDown()) {
	                // make connection
	            	for(int j=0; j<mainView.getSimulation().getDeviceList().size(); j++) {
	            		if(mainView.getSimulation().getDeviceList().get(j).getX() == squareSize/2 + squareSize * (int) (e.getX()/squareSize) && mainView.getSimulation().getDeviceList().get(j).getY() == squareSize/2 + squareSize * (int) (e.getY()/squareSize)) {
	            			if(device != mainView.getSimulation().getDeviceList().get(j)) mainView.getSimulation().getConnectionList().add(new Connection(device, mainView.getSimulation().getDeviceList().get(j)));
	            		}
	            	}
	            	sandbox.getChildren().remove(tempLine);
	            	update();
	            }
			});
			
			// device clicked
			c.setOnMouseClicked((e) -> {
				if(e.getButton() == MouseButton.PRIMARY) {
					// TODO clear previous selection and select
				} else if(e.getButton() == MouseButton.SECONDARY) {
					// TODO open menu to infect or patch
					/*ContextMenu modify = new ContextMenu();
					modify.setAnchorX(e.getX());
					modify.setAnchorY(e.getY());
					modify.getItems().add(new MenuItem("Test"));*/
				} else if(e.isShiftDown()) {
					// TODO add to selection
				}
			});
			
			// TODO create KeyPress to check for delete then delete selected items
			
			device.setX(x);
			device.setY(y);
			device.setSize(size);
			device.setC(c);
			
			sandbox.getChildren().add(c);
			
			device.draw();
		}
		
		gridSizeOld = gridSize;
		squareSizeOld = squareSize;
		
		// add connections
		for(int i=0; i<mainView.getSimulation().getConnectionList().size(); i++) {
			Device device1 = mainView.getSimulation().getConnectionList().get(i).getConnection().get(0);
			Device device2 = mainView.getSimulation().getConnectionList().get(i).getConnection().get(1);
			
			Line line = new Line();
			line.startXProperty().bind(device1.getC().centerXProperty());
			line.startYProperty().bind(device1.getC().centerYProperty());

			line.endXProperty().bind(device2.getC().centerXProperty());
			line.endYProperty().bind(device2.getC().centerYProperty());
		    
			line.setStroke(Color.LIGHTGRAY);
			line.setStrokeWidth(2);
			line.setStrokeLineCap(StrokeLineCap.ROUND);
			
			// TODO add ability to delete connections
			// line.setCursor(Cursor.HAND);
		    
			sandbox.getChildren().add(line);
			line.toBack();
		}
	}
	
	public void add() {
		if(mainView.editing()) {
			VBox options = new VBox();
			options.getStyleClass().add("options");
			
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
			Slider number = new Slider(1, 10, 1);
			number.setShowTickLabels(true);
			number.setShowTickMarks(true);
			number.setMajorTickUnit(1);
			number.setMinorTickCount(0);
			number.setSnapToTicks(true);
			
			Button apply = new Button("Apply");
			apply.setOnAction((e) -> {
				for(int i=0; i<number.getValue(); i++) {
					mainView.getSimulation().getDeviceList().add(new Device(resistance.getValue(), (int)timeToPatch.getValue()));
				}
				mainView.setCenter(mainView.getOutput());
				mainView.draw();
			});
		
			Button cancel = new Button("Cancel");
			cancel.setOnAction((e) -> {
				mainView.setCenter(mainView.getOutput());
				mainView.draw();
			});
			
			options.getChildren().addAll(resistanceLabel, resistance, timeToPatchLabel, timeToPatch, numberLabel, number, apply, cancel);
			mainView.setCenter(options);
		}
	}
	
	private double calcX(int i) {
		int col = i%gridSize;
		return squareSize/2 + squareSize*col;
	}
	
	private double calcY(int i) {
		int row=0;
		while(i >= gridSize) {
			i=i-gridSize;
			row++;
		}
		return squareSize/2 + squareSize*row;
	}
}