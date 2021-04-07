package application;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MainView extends BorderPane {
	private Simulation simulation;
	private Simulator simulator;
	
	private Tools toolbar;
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
		
		toolbar = new Tools(this);
		
		malware = new MalwarePane(this);
		
		output = new Pane();
		output.setMinWidth(600);
		
		environment = new EnvironmentPane(this);
		
		results = new Pane();
		results.setMinHeight(200);
		
		this.setTop(toolbar);
		this.setLeft(malware);
		this.setCenter(output);
		this.setRight(environment);
		this.setBottom(results);
		
		System.out.println("Welcome to xDemic!");
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
	
	public boolean getState() {
		return state;
	}
	
	public Tools getToolbar() {
		return toolbar;
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