package application;

import javafx.scene.layout.Pane;

public class OutputPane extends Pane {
	MainView mainView;
	
	public OutputPane(MainView mainView) {
		this.mainView = mainView;
		
		setMinWidth(400);
		setMinHeight(400);
	}
	
	public void update() {
		setPrefWidth(mainView.getWidth()*2/5);
		setPrefHeight(mainView.getHeight()*2/3-mainView.getTools().getHeight());
	}
}