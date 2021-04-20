package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
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
	    		    
					if(e.getX() > 0 && e.getX() < paneSize) {
						tempLine.setEndX(e.getX());
					}
					if(e.getY() > 0 && e.getY() < paneSize) {
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
					update();
	            } else if (e.isControlDown()) {
	                // make connection
	            	for(int j=0; j<mainView.getSimulation().getDeviceList().size(); j++) {
	            		for(int k=0; k<mainView.getSimulation().getDeviceList().size(); k++) {
		            		if(mainView.getSimulation().getDeviceList().get(j).getX() == squareSize/2 + squareSize * (int) (e.getX()/squareSize) && mainView.getSimulation().getDeviceList().get(j).getY() == squareSize/2 + squareSize * (int) (e.getY()/squareSize)) {
		            			if(mainView.getSimulation().getDeviceList().get(k).getX() == squareSize/2 + squareSize * (int) (device.getX()/squareSize) && mainView.getSimulation().getDeviceList().get(k).getY() == squareSize/2 + squareSize * (int) (device.getY()/squareSize)) {
		            				if(mainView.getSimulation().getDeviceList().get(k) != mainView.getSimulation().getDeviceList().get(j)) mainView.getSimulation().getConnectionList().add(new Connection(mainView.getSimulation().getDeviceList().get(k), mainView.getSimulation().getDeviceList().get(j)));
		            			}
		            		}
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
					/*for(int j=0; j<mainView.getSimulation().getDeviceList().size(); j++) {
						mainView.getSimulation().getDeviceList().get(j).setSelected(false);
					}
					device.setSelected(true);
					device.draw();
					mainView.draw();*/
				} else if(e.getButton() == MouseButton.SECONDARY) {
					// TODO open menu to infect, patch, or delete
					ContextMenu contextMenu = new ContextMenu();
					MenuItem menuItem;
					for(int j=0; j<mainView.getSimulation().getMalwareList().size(); j++) {
						int id = j;
						if(device.getMalwareList().contains(mainView.getSimulation().getMalwareList().get(id))) {
							menuItem = new MenuItem("Patch " + mainView.getSimulation().getMalwareList().get(id).getName());
							menuItem.setOnAction((ee) -> {
								device.patch(mainView.getSimulation().getMalwareList().get(id));
								mainView.draw();
							});
							contextMenu.getItems().add(menuItem);
						} else if(!device.getMalwareList().contains(mainView.getSimulation().getMalwareList().get(id)) && !device.getPatchedMalwareList().contains(mainView.getSimulation().getMalwareList().get(id))) {
							menuItem = new MenuItem("Infect with " + mainView.getSimulation().getMalwareList().get(id).getName());
							menuItem.setOnAction((ee) -> {
								device.infect(mainView.getSimulation().getMalwareList().get(id));
								mainView.draw();
							});
							contextMenu.getItems().add(menuItem);
						}
					}
					menuItem = new MenuItem("Delete");
					menuItem.setOnAction((ee) -> {
						for(int k=0; k<mainView.getSimulation().getConnectionList().size(); k++) {
							if(mainView.getSimulation().getConnectionList().get(k).getConnection().contains(device)) {
								mainView.getSimulation().getConnectionList().remove(k);
								k = k-1;
							}
						}
						mainView.getSimulation().getDeviceList().remove(device);
						mainView.draw();
					});
					contextMenu.getItems().add(menuItem);
					contextMenu.show(c, e.getScreenX(), e.getScreenY());
				} else if(e.isShiftDown()) {
					// TODO add to selection
					/*device.setSelected(true);
					device.draw();*/
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
		// TODO find some way of showing directionality
		for(int i=0; i<mainView.getSimulation().getConnectionList().size(); i++) {
			Device device1 = mainView.getSimulation().getConnectionList().get(i).getConnection().get(0);
			Device device2 = mainView.getSimulation().getConnectionList().get(i).getConnection().get(1);
			
			Line line = new Line();
			line.startXProperty().bind(device1.getC().centerXProperty());
			line.startYProperty().bind(device1.getC().centerYProperty());

			line.endXProperty().bind(device2.getC().centerXProperty());
			line.endYProperty().bind(device2.getC().centerYProperty());
		    
			line.setStroke(new LinearGradient(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0, Color.LIGHTGRAY), new Stop(0.4, Color.LIGHTGRAY), new Stop(0.6, Color.TEAL)}));
			for(int j=0; j<mainView.getSimulation().getConnectionList().size(); j++) {
				if(mainView.getSimulation().getConnectionList().get(j).getConnection().get(0) == device2 && mainView.getSimulation().getConnectionList().get(j).getConnection().get(1) == device1) line.setStroke(Color.TEAL);
			}
			line.setStrokeWidth(3);
			line.setStrokeLineCap(StrokeLineCap.ROUND);
			
			// TODO add ability to delete connections
			// line.setCursor(Cursor.HAND);
		    
			sandbox.getChildren().add(line);
			line.toBack();
		}
	}
	
	public void add() {
		if(mainView.editing()) {
			VBox options = new VBox(10);
			options.getStyleClass().add("options");
			
			double resistanceDefault=50, timeToPatchDefault=100, quantityDefault=1;
			
			Text resistanceLabel = new Text("Resistance to infection is ");
			Text resistanceValue = new Text(String.format("%.1f", resistanceDefault));
			Slider resistance = new Slider(0, 100, resistanceDefault);
			resistance.valueProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue <? extends Number > observable, Number oldValue, Number newValue) {
					resistanceValue.setText(String.format("%.1f", newValue) + "%");
				}
			});
			
			Text timeToPatchLabel = new Text("Patches will be applied after ");
			Text timeToPatchValue = new Text(String.format("%.0f", timeToPatchDefault));
			Text timeToPatchLabel2 = new Text(" frames following patch release");
			Slider timeToPatch = new Slider(0, 3600, timeToPatchDefault);
			timeToPatch.setMajorTickUnit(1);
			timeToPatch.setMinorTickCount(0);
			timeToPatch.setSnapToTicks(true);
			timeToPatch.valueProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue <? extends Number > observable, Number oldValue, Number newValue) {
					timeToPatchValue.setText(String.format("%.0f", newValue));
				}
			});
			
			Text quantityLabel = new Text("Quantity of devices to be added is ");
			Text quantityValue = new Text(String.format("%.0f", quantityDefault));
			Slider quantity = new Slider(1, 100, quantityDefault);
			quantity.setMajorTickUnit(1);
			quantity.setMinorTickCount(0);
			quantity.setSnapToTicks(true);
			quantity.valueProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue <? extends Number > observable, Number oldValue, Number newValue) {
					quantityValue.setText(String.format("%.0f", newValue));
				}
			});
			
			Button apply = new Button("Apply");
			apply.setOnAction((e) -> {
				for(int i=0; i<(int) quantity.getValue(); i++) {
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
			
			options.getChildren().addAll(new HBox(resistanceLabel, resistanceValue), resistance, new HBox(timeToPatchLabel, timeToPatchValue, timeToPatchLabel2), timeToPatch, new HBox(quantityLabel, quantityValue), quantity, new HBox(apply, cancel));
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