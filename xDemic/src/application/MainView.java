package application;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainView extends BorderPane {
	private Simulation simulation;
	private Simulator simulator;
	
	private VBox tools;
	private MalwarePane malware;
	private Pane output;
	private EnvironmentPane environment;
	private Pane results;
	
	public static final boolean EDITING = false;
    public static final boolean SIMULATING = true;
    
    private boolean state = EDITING;
	
	public MainView() {
		this.simulation = new Simulation();
		this.simulator = new Simulator(this);
		
		tools = new Tools(this);
		tools.setId("menu");
		
		malware = new MalwarePane(this);
		malware.setId("malware");
		
		output = new Pane();
		output.setId("output");
		
		environment = new EnvironmentPane(this);
		environment.setId("environment");
		
		results = new Pane();
		results.setId("results");
		
		this.setTop(tools);
		this.setLeft(malware);
		this.setCenter(output);
		this.setRight(environment);
		this.setBottom(results);
		
		System.out.println("Welcome to xDemic!");
		
		widthProperty().addListener((l) -> {draw();});
		heightProperty().addListener((l) -> {draw();});
	}

	public Pane getResults() {
		return results;
	}

	public void draw() {		
		malware.update();
		// TODO output.update();
		environment.update();
		// TODO results.update();
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
	
	public boolean editing() {
		if(state == EDITING) return true;
		else return false;
	}
	
	public VBox getTools() {
		return tools;
	}

	public MalwarePane getMalware() {
		return malware;
	}

	public Pane getOutput() {
		return output;
	}

	public EnvironmentPane getEnvironment() {
		return environment;
	}
}