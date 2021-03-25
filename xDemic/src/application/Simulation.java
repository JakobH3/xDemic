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
		//
	}
	
	public void decentralized() {
		//
	}
	
	public void interact(ArrayList<Object> devices) {
		ArrayList<Malware> d1Malware = ((Device) devices.get(0)).getMalware();
		ArrayList<Malware> d2Malware = ((Device) devices.get(1)).getMalware();
		
		//transmission from device1 to device2
		for(int i=0; i<d1Malware.size(); i++) {
			//if device2 is not infected with device1's selected malware
			if(!d2Malware.contains(d1Malware.get(i))) {
				//if device2 is infected based on probability of device1's selected malware
				if(random.nextDouble()*100 <= d1Malware.get(i).getChanceInfection()) {
					//infect device2 with device1's selected malware
					((Device) devices.get(1)).infect(d1Malware.get(i));
				}
			}
		}
	}

	public ArrayList<Device> getDeviceList() {
		return deviceList;
	}

	public ArrayList<Node> getNodeList() {
		return nodeList;
	}

	public ArrayList<Connection> getConnectionList() {
		return connectionList;
	}

	public ArrayList<Malware> getMalwareList() {
		return malwareList;
	}
}