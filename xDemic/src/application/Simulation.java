package application;

import java.util.ArrayList;
import java.util.Random;

public class Simulation {
	private ArrayList<Device> deviceList;
	private ArrayList<Device> nodeList;
	private ArrayList<Connection> connectionList;
	private ArrayList<Malware> malwareList;
	private double deviceMobilityFactor=1;	// max of 1
	private double centralMobilityFactor=1;	// max of 1
	private double nodeMobilityFactor=1;	// max of 1
	
	Random random = new Random();
	
	public Simulation() {
		deviceList = new ArrayList<Device>();
		nodeList = new ArrayList<Device>();
		connectionList = new ArrayList<Connection>();
		malwareList = new ArrayList<Malware>();
	}
	
	public void step() {
		// TODO update the data which will then be drawn
		for(int i=0; i<deviceList.size(); i++) {
			int k=0;
			int l=0;
			for(int j=0; j<connectionList.size(); j++) {
				if(connectionList.get(j).getConnection().get(0) == deviceList.get(i)) k++;
				if(connectionList.get(j).getConnection().get(1) == deviceList.get(i)) l++;
			}
			//a node has more than one input and more than one output
			if(k>1 && l>1) nodeList.add(deviceList.get(i));
		}
		
		if(nodeList.size()==0) distributed();
		else if(nodeList.size()==1) centralized();
		else decentralized();
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
	
	public void clear() {
		deviceList.clear();
		nodeList.clear();
		connectionList.clear();
		malwareList.clear();
		deviceMobilityFactor = 1;
		centralMobilityFactor = 1;
		nodeMobilityFactor = 1;
	}
	
	public double calculatePercentInf(ArrayList<Device> deviceList) 
	{
		double percent = 0;
		int numInfected = 0;
		
		for(int i = 0; i < deviceList.size(); i++) {
			if(deviceList.get(i).isInfected()) {
			 numInfected++;	
			}
		}
		
		percent = 100 * (numInfected / deviceList.size());
		
		return percent;
	}
	
	public double calculatePercentRecovered(ArrayList<Device> deviceList, Malware mal) //this returns the percentage of devices recovered from a specific malware 
	{
		double percent = 0;
		int numRecovered = 0;
		
		for(int i = 0; i < deviceList.size(); i++) 
		{
			if(deviceList.get(i).getPatchedMalwareList().indexOf(mal) > 0) {
				numRecovered++;
			}
		}
		percent = 100 * (numRecovered / deviceList.size());
		
		return percent;
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
		clear();
		for(int i=0; i<100; i++) {
			deviceList.add(new Device(i, Integer.MAX_VALUE));
		}
		
		malwareList.add(new Malware("Example Malware", 100, Integer.MAX_VALUE));
		
		for(int i=0; i<deviceList.size()-1; i++) {
			connectionList.add(new Connection(deviceList.get(i), deviceList.get(i+1)));
		}
		
		deviceList.get(0).infect(malwareList.get(0));
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