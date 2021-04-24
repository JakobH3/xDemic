package application;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;

public class InfoPane extends HBox {
	private MainView mainView;
	
	ScrollPane simulationStatsScroll, environmentStatsScroll;
	VBox simulationStats, environmentStats;
	
	public InfoPane(MainView mainView) {
		this.mainView = mainView;
		
		simulationStatsScroll  = new ScrollPane();
		simulationStats = new VBox();
		simulationStatsScroll.setContent(simulationStats);
		
		environmentStatsScroll  = new ScrollPane();
		environmentStats = new VBox();
		environmentStatsScroll.setContent(environmentStats);
		
		setMinHeight(200);
		getChildren().addAll(simulationStatsScroll, environmentStatsScroll);
	}
	
	public void update() {
		simulationStats.getChildren().clear();
		environmentStats.getChildren().clear();
		
		setPrefHeight(mainView.getHeight()*1/3);
		
		Text devices = new Text(mainView.getSimulation().getDeviceList().size() + " devices added");
		Text nodes = new Text(mainView.getSimulation().getNodeList().size() + " nodes present");
		Text connections = new Text(mainView.getSimulation().getConnectionList().size() + " connections made");
		Text frameRate = new Text("Simulation playing at " + mainView.getSimulator().getFrameRate() + " frames per second");
		Text currentFrame = new Text("Current frame is " + mainView.getSimulator().getFrame());
		Text ddMobility = new Text(String.format("Device to device mobility: %.2f", mainView.getSimulation().getDdMobility()));
		Text dnMobility = new Text(String.format("Device to node mobility: %.2f", mainView.getSimulation().getDnMobility()));
		Text nnMobility = new Text(String.format("Node to node mobility: %.2f", mainView.getSimulation().getNnMobility()));
		
		simulationStats.getChildren().addAll(devices, nodes, connections, frameRate, currentFrame, ddMobility, dnMobility, nnMobility);
		
		Text totalInfected = new Text(String.format("%.2f%% infected", mainView.getSimulation().calculatePercentInfected()));
		Text totalPatched = new Text(String.format("%.2f%% patched", mainView.getSimulation().calculatePercentPatched()));
		environmentStats.getChildren().addAll(totalInfected, totalPatched);
		for(int i=0; i<mainView.getSimulation().getMalwareList().size(); i++) {
			// determine infected and patched for each malware
			Text infected = new Text(String.format("%.2f%% infected with %s", mainView.getSimulation().calculatePercentInfected(mainView.getSimulation().getMalwareList().get(i)), mainView.getSimulation().getMalwareList().get(i).getName()));
			Text patched = new Text(String.format("%.2f%% patched from %s", mainView.getSimulation().calculatePercentPatched(mainView.getSimulation().getMalwareList().get(i)), mainView.getSimulation().getMalwareList().get(i).getName()));
			environmentStats.getChildren().addAll(infected, patched);
		}
	}
}