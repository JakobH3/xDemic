package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DeviceBox extends Rectangle {
	Device device;
	
	public DeviceBox(Device device) {
		this.device = device;
		
		this.setWidth(20);
		this.setHeight(20);
		if(device.isInfected()) {
			this.setFill(Color.RED);
		} else if(device.isPatched()) {
			this.setFill(Color.GREEN);
		} else {
			this.setFill(Color.WHITE);
		}
	}
}