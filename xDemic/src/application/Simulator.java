package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class Simulator {
	private MainView mainView;
    private Simulation simulation;
    private Timeline timeline;
    private int frameRate=60;
    private int frame=0;

    public Simulator(MainView mainView) {
        this.mainView = mainView;
        this.simulation = this.mainView.getSimulation();
        this.timeline = new Timeline(new KeyFrame(Duration.millis(1000/frameRate), this::doStep));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void doStep(ActionEvent actionEvent) {
    	simulation.step();
        mainView.draw();
        frame++;
    }

    public void start() {
		System.out.println("> Simulator started at " + frameRate + " frames per second.");
		timeline.play();
		mainView.setState(MainView.SIMULATING);
    }

    public void stop() {
		System.out.println("> Simulator stopped.");
        timeline.stop();
        mainView.setState(MainView.EDITING);
    }
    
    public void reset() {
    	this.timeline = new Timeline(new KeyFrame(Duration.millis(1000/frameRate), this::doStep));
    	this.timeline.setCycleCount(Timeline.INDEFINITE);
    	frame=0;
    }
    
    public int getFrameRate() {
    	return frameRate;
    }
    
    public void setFrameRate(int frameRate) {
    	this.frameRate = frameRate;
    }
    
    public int getFrame() {
    	return frame;
    }
}