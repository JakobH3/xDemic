package application;

import java.util.ArrayList;
import java.util.Random;

public class Simulation {
	private MainView mainView;
	
	private ArrayList<Device> deviceList;
	private ArrayList<Device> nodeList;
	private ArrayList<Connection> connectionList;
	private ArrayList<Malware> malwareList;
	
	private double ddMobility=1; // max of 1
	private double dnMobility=1; // max of 1
	private double nnMobility=1; // max of 1
	
	Random random = new Random();
	
	public Simulation(MainView mainView) {
		this.mainView = mainView;
		
		deviceList = new ArrayList<Device>();
		nodeList = new ArrayList<Device>();
		connectionList = new ArrayList<Connection>();
		malwareList = new ArrayList<Malware>();
	}
	
	public void step() {
		// determine number of nodes
		update();
		
		// determine if a device can be patched
		for(int i=0; i<deviceList.size(); i++) {
			for(int j=0; j<malwareList.size(); j++) {
				if(mainView.getSimulator().getFrame() > deviceList.get(i).getTimeToPatch() + malwareList.get(j).getPatchRelease()) {
					deviceList.get(i).patch(malwareList.get(j));
				}
			}
		}
		
		for(int i=0; i<connectionList.size(); i++) {
			interact(connectionList.get(i).getConnection());
		}
	}
	
	public void update() {
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
	}
	
	public void reset() {
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
	
	public void generateRandom() {
		reset();
		int deviceSize = random.nextInt(99)+1;
		for(int i=0; i<deviceSize; i++) {
			deviceList.add(new Device(random.nextDouble()*100, random.nextInt(3600)));
		}
		malwareList.add(new Malware("Example", random.nextDouble()*100, random.nextInt(3600)));
		for(int i=0; i<deviceList.size()-1; i++) {
			connectionList.add(new Connection(deviceList.get(i), deviceList.get(i+1)));
		}
		deviceList.get(0).infect(malwareList.get(0));
		ddMobility = random.nextDouble();
		dnMobility = random.nextDouble();
		nnMobility = random.nextDouble();
	}
	
	public double calculatePercentInfected() {
		int count = 0;
		
		for(int i = 0; i < deviceList.size(); i++) {
			if(deviceList.get(i).isInfected()) {
				count++;	
			}
		}
		return deviceList.size() > 0 ? 100.0*count/deviceList.size() : 0;
	}
	
	public double calculatePercentInfected(Malware malware) {
		int count = 0;
		
		for(int i = 0; i < deviceList.size(); i++) {
			if(deviceList.get(i).getMalwareList().contains(malware)) {
				count++;	
			}
		}
		return deviceList.size() > 0 ? 100.0*count/deviceList.size() : 0;
	}
	
	public double calculatePercentPatched() {
		int count = 0;
		
		for(int i = 0; i < deviceList.size(); i++) {
			if(deviceList.get(i).isPatched()) {
				count++;	
			}
		}
		return deviceList.size() > 0 ? 100.0*count/deviceList.size() : 0;
	}
	
	public double calculatePercentPatched(Malware malware) {
		int count = 0;
		
		for(int i = 0; i < deviceList.size(); i++) {
			if(deviceList.get(i).getPatchedMalwareList().contains(malware)) {
				count++;	
			}
		}
		return deviceList.size() > 0 ? 100.0*count/deviceList.size() : 0;
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
	
	public double getDdMobility() {
		return ddMobility;
	}

	public void setDdMobility(double ddMobility) {
		this.ddMobility = ddMobility;
	}

	public double getDnMobility() {
		return dnMobility;
	}

	public void setDnMobility(double dnMobility) {
		this.dnMobility = dnMobility;
	}

	public double getNnMobility() {
		return nnMobility;
	}

	public void setNnMobility(double nnMobility) {
		this.nnMobility = nnMobility;
	}
}