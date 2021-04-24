package application;

import javafx.scene.layout.BorderPane;

public class MainView extends BorderPane {
	private Simulation simulation;
	private Simulator simulator;
	
	private Tools tools;
	private MalwarePane malware;
	private OutputPane output;
	private EnvironmentPane environment;
	private InfoPane info;
	
	public static final boolean EDITING = false;
    public static final boolean SIMULATING = true;
    
    private boolean state = EDITING;
	
	public MainView() {
		this.simulation = new Simulation(this);
		this.simulator = new Simulator(this);
		
		tools = new Tools(this);
		tools.setId("menu");
		
		malware = new MalwarePane(this);
		malware.setId("malware");
		
		output = new OutputPane(this);
		output.setId("output");
		
		environment = new EnvironmentPane(this);
		environment.setId("environment");
		
		info = new InfoPane(this);
		info.setId("info");
		
		this.setTop(tools);
		this.setLeft(malware);
		this.setCenter(output);
		this.setRight(environment);
		this.setBottom(info);
		
		System.out.println("Welcome to xDemic!");
		
		widthProperty().addListener((l) -> {draw();});
		heightProperty().addListener((l) -> {draw();});
	}

	public void draw() {
		simulation.update();
		malware.update();
		output.update();
		environment.update();
		info.update();
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
	
	public Tools getTools() {
		return tools;
	}

	public MalwarePane getMalware() {
		return malware;
	}

	public OutputPane getOutput() {
		return output;
	}

	public EnvironmentPane getEnvironment() {
		return environment;
	}

	public InfoPane getInfo() {
		return info;
	}
}