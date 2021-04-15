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
	}
}