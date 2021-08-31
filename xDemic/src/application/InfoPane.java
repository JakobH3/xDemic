package application;

import javafx.scene.layout.Pane;

public class InfoPane extends Pane {
	MainView mainView;
	
	public InfoPane(MainView mainView) {
		this.mainView = mainView;
		
		setMinHeight(200);
	}
	
	public void update() {
		setPrefHeight(mainView.getHeight()*1/3);
		// TODO may want to change infopane to extend scrollpane or some other method,
		//      then put a vbox or hbox inside it
		
		// TODO display mobility rates
		// TODO display frames per second
		// TODO display total devices
		// TODO display list of malware with % infected and % patched
		// TODO display number of nodes
		// TODO display number of connections
		
		// TODO if something specific is selected, display related info
		// ** this will probably come later ** //
	}
}