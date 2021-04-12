package application;

import java.util.ArrayList;
import java.util.Random;

public class Simulation {
	private ArrayList<Device> deviceList;
	private ArrayList<Device> nodeList;
	private ArrayList<Connection> connectionList;
	private ArrayList<Malware> malwareList;
	private double ddMobility=1;	// max of 1
	private double dnMobility=1;	// max of 1
	private double nnMobility=1;	// max of 1
	
	Random random = new Random();
	
	public Simulation() {
		deviceList = new ArrayList<Device>();
		nodeList = new ArrayList<Device>();
		connectionList = new ArrayList<Connection>();
		malwareList = new ArrayList<Malware>();
	}
	
	public void step() {
		// TODO update the data which will then be drawn
		
		// determine which devices behave as nodes
		nodeList.clear();
		for(int i=0; i<deviceList.size(); i++) {
			int output=0;
			int input=0;
			for(int j=0; j<connectionList.size(); j++) {
				if(connectionList.get(j).getConnection().get(0) == deviceList.get(i)) output++;
				if(connectionList.get(j).getConnection().get(1) == deviceList.get(i)) input++;
			}
			// a node has more than one input and more than one output
			if(output > 1 && input > 1) nodeList.add(deviceList.get(i));
		}
		
		for(int i=0; i<connectionList.size(); i++) {
			interact(connectionList.get(i).getConnection());
		}
	}
	
	public void clear() {
		deviceList.clear();
		nodeList.clear();
		connectionList.clear();
		malwareList.clear();
		ddMobility = 1;
		dnMobility = 1;
		nnMobility = 1;
	}
	
	public void interact(ArrayList<Device> deviceList) {		
		Device d1 = deviceList.get(0);
		Device d2 = deviceList.get(1);
		
		if(nodeList.contains(d1) && nodeList.contains(d2)) {
			// nnMobility
			transmit(d1, d2, nnMobility);
		} else if(nodeList.contains(d1) || nodeList.contains(d2)) {
			// dnMobility
			transmit(d1, d2, dnMobility);
		} else {
			// ddMobility
			transmit(d1, d2, ddMobility);
		}
	}
	
	public void transmit(Device d1, Device d2, double mobility) {
		ArrayList<Malware> d1Malware = d1.getMalware();
		ArrayList<Malware> d2Malware = d2.getMalware();
		
		// transmission from d1 to d2
		if(random.nextDouble() <= mobility) {
			for(int i=0; i<d1Malware.size(); i++) {
				// if device2 is not infected with device1's selected malware
				if(!d2Malware.contains(d1Malware.get(i))) {
					// if device2 is infected based on probability of device1's selected malware
					if(random.nextDouble()*100 <= d1Malware.get(i).getChanceInfection()) {
						// if device2 can't resist infection
						if(!(random.nextDouble()*100 <= d2.getResistance())) {
							// infect device2 with device1's selected malware
							d2.infect(d1Malware.get(i));
						}
					}
				}
			}
		}
	}
	
	public void loadExample() {
		clear();
		for(int i=0; i<100; i++) {
			deviceList.add(new Device(i, Integer.MAX_VALUE));
		}
		
		malwareList.add(new Malware("Example Malware", 10, Integer.MAX_VALUE));
		
		for(int i=0; i<deviceList.size()-1; i++) {
			connectionList.add(new Connection(deviceList.get(i), deviceList.get(i+1)));
		}
		
		deviceList.get(0).infect(malwareList.get(0));
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
	
	public double calculatePercentRecovered(ArrayList<Device> deviceList, Malware mal) // this returns the percentage of devices recovered from a specific malware 
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
}