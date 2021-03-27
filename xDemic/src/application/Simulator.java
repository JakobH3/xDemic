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

    public Simulator(MainView mainView) {
        this.mainView = mainView;
        this.simulation = this.mainView.getSimulation();
        this.timeline = new Timeline(new KeyFrame(Duration.millis(1000/frameRate), this::doStep));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void doStep(ActionEvent actionEvent) {
        simulation.step();
        mainView.draw();
    }

    public void start() {
		timeline.play();
		mainView.setState(MainView.SIMULATING);
    }

    public void stop() {
        timeline.stop();
        mainView.setState(MainView.EDITING);
    }
    
    public int getFrameRate() {
    	return frameRate;
    }
    
    public void setFrameRate(int frameRate) {
    	this.frameRate = frameRate;
    }
}