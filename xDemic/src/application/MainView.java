package application;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;

public class MainView extends BorderPane {
	private Simulation simulation;
	private Simulator simulator;
	
	Tools topPane;
	VBox leftPane;
	Pane centerPane;
	Pane rightPane;
	VBox bottomPane;
	
	public MainView() {
		topPane = new Tools(this);
		
		leftPane = new VBox(3);
		leftPane.setMinWidth(400);
		leftPane.setMaxWidth(400);
		
		centerPane = new Pane();
		centerPane.setMinWidth(600);
		centerPane.getStyleClass().add("pane");
		
		rightPane = new Pane();
		rightPane.setMinWidth(600);
		rightPane.setMaxWidth(600);
		
		bottomPane = new VBox(1);
		bottomPane.setMinHeight(300);
		bottomPane.setMaxHeight(300);
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
		this.bottomPane.getChildren().add(new Text("Updating..."));
		// TODO give each class "simulation" to obtain the data
		System.out.println("Done!");
		this.bottomPane.getChildren().add(new Text("Done!"));
	}
	
	public Simulation getSimulation() {
        return simulation;
    }
	
	public Simulator getSimulator() {
        return simulator;
    }
}