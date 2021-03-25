package application;

import java.util.ArrayList;
import java.util.Random;

public class Simulation {
	private ArrayList<Device> deviceList;
	private ArrayList<Device> nodeList;
	private ArrayList<Connection> connectionList;
	private ArrayList<Malware> malwareList;
	private double deviceMobilityFactor; // max of 1
	private double centralMobilityFactor; // max of 1
	private double nodeMobilityFactor; // max of 1
	
	Random random = new Random();
	
	public Simulation() {
		deviceList = new ArrayList<Device>();
		nodeList = new ArrayList<Device>();
		connectionList = new ArrayList<Connection>();
		malwareList = new ArrayList<Malware>();
	}
	
	public void step() {
		// TODO update the data which will then be drawn
		if(nodeList.size()==0) {
			distributed();
		} else if(nodeList.size()==1) {
			centralized();
		} else {
			decentralized();
		}
	}
	
	public void distributed() {
		for(int i=0; i<connectionList.size(); i++) {
			if(random.nextDouble() < deviceMobilityFactor) {
				interact(connectionList.get(i).getConnection());
			}
		}
	}
	
	public void centralized() {
		// TODO
	}
	
	public void decentralized() {
		// TODO
	}
	
	public void interact(ArrayList<Device> deviceList) {
		ArrayList<Malware> device1Malware = deviceList.get(0).getMalware();
		ArrayList<Malware> device2Malware = deviceList.get(1).getMalware();
		
		//transmission from device1 to device2
		for(int i=0; i<device1Malware.size(); i++) {
			//if device2 is not infected with device1's selected malware
			if(!device2Malware.contains(device1Malware.get(i))) {
				//if device2 is infected based on probability of device1's selected malware
				if(random.nextDouble()*100 <= device1Malware.get(i).getChanceInfection()) {
					//infect device2 with device1's selected malware
					deviceList.get(1).infect(device1Malware.get(i));
				}
			}
		}
	}
	
	public void loadExample() {
		// TODO Auto-generated method stub
		Example example = new Example();
		this.deviceList = example.getDeviceList();
		this.nodeList = example.getNodeList();
		this.connectionList = example.getConnectionList();
		this.malwareList = example.getMalwareList();
		this.deviceMobilityFactor = example.getDeviceMobilityFactor();
		this.centralMobilityFactor = example.getCentralMobilityFactor();
		this.nodeMobilityFactor = example.getNodeMobilityFactor();
	}
	
	public ArrayList<Device> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(ArrayList<Device> deviceList) {
		this.deviceList = deviceList;
	}

	public ArrayList<Device> getNodeList() {
		return nodeList;
	}

	public void setNodeList(ArrayList<Device> nodeList) {
		this.nodeList = nodeList;
	}

	public ArrayList<Connection> getConnectionList() {
		return connectionList;
	}

	public void setConnectionList(ArrayList<Connection> connectionList) {
		this.connectionList = connectionList;
	}

	public ArrayList<Malware> getMalwareList() {
		return malwareList;
	}

	public void setMalwareList(ArrayList<Malware> malwareList) {
		this.malwareList = malwareList;
	}

	public double getDeviceMobilityFactor() {
		return deviceMobilityFactor;
	}

	public void setDeviceMobilityFactor(double deviceMobilityFactor) {
		this.deviceMobilityFactor = deviceMobilityFactor;
	}

	public double getCentralMobilityFactor() {
		return centralMobilityFactor;
	}

	public void setCentralMobilityFactor(double centralMobilityFactor) {
		this.centralMobilityFactor = centralMobilityFactor;
	}

	public double getNodeMobilityFactor() {
		return nodeMobilityFactor;
	}

	public void setNodeMobilityFactor(double nodeMobilityFactor) {
		this.nodeMobilityFactor = nodeMobilityFactor;
	}
}