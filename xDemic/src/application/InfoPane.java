package application;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class InfoPane extends Pane {
	private MainView mainView;
	private GridPane pane;
	
	public InfoPane(MainView mainView) {
		this.mainView = mainView;
		pane  = new GridPane();
		setMinHeight(200);
		getChildren().add(pane);
	}

	public void update() {
		setPrefHeight(mainView.getHeight()*1/3);
		// TODO may want to change infopane to extend scrollpane or some other method,
		//      then put a vbox or hbox inside it
		
		// TODO display mobility rates
		// TODO display frames per second
		// TODO display total devices
		// TODO display list of malware with % infected and % patched
		// TODO display number of nodes
		// TODO display number of connections
		
		// TODO if something specific is selected, display related info
		// ** this will probably come later ** //
		
		int numDevices = mainView.getSimulation().getDeviceList().size();
		GridPane tempPane = new GridPane();
		ArrayList<Device> temp = mainView.getSimulation().getDeviceList();
		temp.addAll(mainView.getSimulation().getNodeList());
		double perInfVal = mainView.getSimulation().calculatePercentInf(temp);
		ArrayList<Malware> malList = mainView.getSimulation().getMalwareList();
		
		pane.getChildren().clear();
		pane.setPrefHeight(mainView.getHeight()*1/3);
		
		Text numDeviceText = new Text("Percentage Infected: " + perInfVal + "%");
		
		Text perInf = new Text(" Number of Devices: " + numDevices);
		tempPane.add(perInf,1,0);
		tempPane.add(numDeviceText,0,0);
		
		
		for(int i = 0; i < malList.size(); i++)
		{
			double tempVal = mainView.getSimulation().calculatePercentRecovered(temp, malList.get(i));
			Text tempText = new Text("Percent of Devices Recovered From " + malList.get(i).getName() + " is " + tempVal + "%");
			tempPane.add(tempText,0,i + 1);
		}
		
		pane.getChildren().add(tempPane);
	}
}