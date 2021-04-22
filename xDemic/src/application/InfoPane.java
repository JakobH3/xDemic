package application;

import java.awt.Insets;
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
	
	public void update() 
	{
		GridPane tempPane = new GridPane();
		ArrayList<Device> temp = mainView.getSimulation().getDeviceList();
		int numDevices = mainView.getSimulation().getDeviceList().size();
		int numNodes = mainView.getSimulation().getNodeList().size();
		int numConnections = mainView.getSimulation().getConnectionList().size();
		int frameRate = mainView.getSimulator().getFrameRate();
		double perInfVal = mainView.getSimulation().calculatePercentInf(temp);
		
		
		ArrayList<Malware> malList = mainView.getSimulation().getMalwareList();
		
		pane.getChildren().clear();
		pane.setPrefHeight(mainView.getHeight()*1/3);
		
		Text numDeviceText = new Text(" Number of Devices: " + numDevices);
		Text numNodesText = new Text(" Number of Nodes " + numNodes);
		Text numConnectionsText = new Text(" Number of Connections " + numConnections);
		Text frameRateText = new Text(" Frame Rate: " + frameRate);
		
		Text perInf = new Text(" Percentage Infected: " + perInfVal + "%");
		
		tempPane.add(numDeviceText,0,0);
		tempPane.add(numNodesText,0,1);
		tempPane.add(numConnectionsText,0,2);
		tempPane.add(frameRateText,0,3);
		
		
		
		tempPane.add(perInf,1,0);
		
		
		
		
		for(int i = 0; i < malList.size(); i++)
		{
			double tempVal = mainView.getSimulation().calculatePercentRecovered(temp, malList.get(i));
			Text tempText = new Text(" Percent of Devices Recovered From " + malList.get(i).getName() + " is " + tempVal + "%");
			tempPane.add(tempText,1,i+1);
		}
		
		pane.getChildren().add(tempPane);
		
		
	}
}