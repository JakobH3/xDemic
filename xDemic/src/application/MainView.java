package application;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MainView extends BorderPane {
	private Simulation simulation;
	private Simulator simulator;
	
	private Tools topPane;
	private Pane malware;
	private Pane output;
	private Pane environment;
	private Pane bottomPane;
	
	public static final boolean EDITING = false;
    public static final boolean SIMULATING = true;
    
    private boolean state = EDITING;
	
	public MainView() {
		this.simulation = new Simulation();
		this.simulator = new Simulator(this);
		
		topPane = new Tools(this);
		
		malware = new Pane();
		malware.setMinWidth(400);
		malware.setMaxWidth(400);
		
		output = new Pane();
		output.setMinWidth(600);
		output.getStyleClass().add("pane");
		
		environment = new Pane();
		environment.setMinWidth(600);
		environment.setMaxWidth(600);
		
		bottomPane = new Pane();
		bottomPane.setId("bottom");
		
		this.setTop(topPane);
		this.setLeft(malware);
		this.setCenter(output);
		this.setRight(environment);
		this.setBottom(bottomPane);
		
		System.out.println("Welcome to xDemic!");
	}

	public void draw() {
		// TODO draw based on the data available
		// TODO give each class "mainView" to obtain the data
		malware.getChildren().clear();
		output.getChildren().clear();
		environment.getChildren().clear();
		
		malware.getChildren().add(new MalwarePane(this));
		// TODO output.getChildren().add(new OutputPane(this));
		environment.getChildren().add(new EnvironmentPane(this));
		
		System.out.println("GUI Refreshed");
	}
	
	public Simulation getSimulation() {
        return simulation;
    }
	
	public Simulator getSimulator() {
        return simulator;
    }
	
	public void setState(boolean state) {
		this.state = state;
	}
	
	public boolean getState() {
		return state;
	}
}