package application;

import javafx.scene.layout.BorderPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

public class MainView extends BorderPane {
	private Simulation simulation;
	private Simulator simulator;
	
	public MainView() {
		ToolBar topPane = new ToolBar();
		
		VBox leftPane = new VBox(3);
		leftPane.setMinWidth(400);
		leftPane.setMaxWidth(400);
		
		Pane centerPane = new Pane();
		centerPane.setMinWidth(600);
		centerPane.getStyleClass().add("pane");
		
		Pane rightPane = new Pane();
		rightPane.setMinWidth(600);
		rightPane.setMaxWidth(600);
		
		Pane bottomPane = new Pane();
		bottomPane.setMinHeight(10);
		bottomPane.setId("bottom");
		
		this.setTop(topPane);
		this.setLeft(leftPane);
		this.setCenter(centerPane);
		this.setRight(rightPane);
		this.setBottom(bottomPane);
		
		this.simulation = new Simulation();
		this.simulator = new Simulator(this);
	}

	public void draw() {
		System.out.println("Updating...");
		// TODO draw based on the data available
		// TODO give each class "simulation" to obtain the data
		System.out.println("Done!");
	}
	
	public Simulation getSimulation() {
        return simulation;
    }
	
	public Simulator getSimulator() {
        return simulator;
    }
}