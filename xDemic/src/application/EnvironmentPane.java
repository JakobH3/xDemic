package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Line;

public class EnvironmentPane extends Pane {
	private MainView mainView;
	
	private double padding;
	private double hGap;
	private double vGap;
	private int column;
	
	private TilePane devices;
	
	private double startX;
	private double startY;
	private double endX;
	private double endY;
	
	public EnvironmentPane(MainView mainView) {
		this.mainView = mainView;
		
		devices = new TilePane();
		devices.setTileAlignment(Pos.CENTER);
		devices.setPrefWidth(600);
		
		// TODO configure sizes so that it is scalable
		padding = 10;
		hGap = 10;
		vGap = 10;
		
		devices.setPadding(new Insets(padding));
		devices.setHgap(hGap);
		devices.setVgap(vGap);
		this.getChildren().add(devices);
		
		update();
	}
	
	public void update() {
		devices.getChildren().clear();
		for(int i=0; i<mainView.getSimulation().getDeviceList().size(); i++) {
			devices.getChildren().add(new DeviceBox(mainView.getSimulation().getDeviceList().get(i)));
		}
		for(int i=0; i<mainView.getSimulation().getConnectionList().size(); i++) {
//			startX = 10;
//			startY = 10;
//			endX = 10;
//			endY = 10;
//			this.getChildren().add(new Line(startX, startY, endX, endY));
		}
	}
}