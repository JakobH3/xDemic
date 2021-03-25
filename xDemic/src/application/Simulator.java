package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class Simulator {
    private MainView mainView;
    private Simulation simulation;
    private Timeline timeline;

    public Simulator(MainView mainView) {
        this.mainView = mainView;
        this.simulation = this.mainView.getSimulation();
        this.timeline = new Timeline(new KeyFrame(Duration.millis(500), this::doStep));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void doStep(ActionEvent actionEvent) {
        this.simulation.step();
        this.mainView.draw();
    }

    public void start() {
    	if(simulation.getDeviceList().size() > 1
    			&& simulation.getConnectionList().size() > 0
    			&& simulation.getMalwareList().size() > 0) {
    		this.timeline.play();
    	} else {
    		// TODO show an error message
    	}
    }

    public void stop() {
        this.timeline.stop();
    }
}