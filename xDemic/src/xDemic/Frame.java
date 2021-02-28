package xDemic;

import java.util.ArrayList;

public class Frame {
	private int ID;
	private ArrayList<Device> deviceList = new ArrayList<Device>();
	private ArrayList<Malware> malwareList = new ArrayList<Malware>();
	
	public Frame(int iID, ArrayList<Device> iDeviceList, ArrayList<Malware> iMalwareList) {
		java.util.Random random = new java.util.Random();
		ID=iID;
		deviceList=iDeviceList;
		malwareList=iMalwareList;
		
		if(ID==0) {
			initialize();
		} else {
			for(int i=0; i<deviceList.size(); i++) {
				//see if any devices have met the time requirement to recover
				recovery();
				//choose two random devices to interact
				interaction(deviceList.get(random.nextInt(deviceList.size())), deviceList.get(random.nextInt(deviceList.size())));
			}
		}
		
		
		progress(checkInfected());
	}
	
	public int getID() {
		return ID;
	}
	
	public void initialize() {
		for(int i=0; i<malwareList.size(); i++) {
			for(int j=0; j<deviceList.size(); j++) {
				if(j*100.0/deviceList.size() < malwareList.get(i).getInitialInfected()) {
					deviceList.get(j).infect(malwareList.get(i), ID);
				}
			}
		}
		//print the initial status
		System.out.println("> Initialization Complete.\n");
		System.out.println("== Initialization Results ==============");
		System.out.println("|  Total infected: " + checkInfected() + "%");
		for(int i=0; i<malwareList.size(); i++) {
			System.out.println("|  - " + malwareList.get(i).getName() + ": " + checkInfected(malwareList.get(i)) + "%");
		}
		System.out.println("========================================\n");
	}
	
	public double checkInfected() {
		int count=0;
		for(int i=0; i<deviceList.size(); i++) {
			if(deviceList.get(i).isInfected()) {
				count++;
			}
		}
		return 100.0*count/deviceList.size();
	}
	
	public double checkInfected(Malware malware) {
		int count=0;
		for(int i=0; i<deviceList.size(); i++) {
			if(deviceList.get(i).getMalware().contains(malware)) {
				count++;
			}
		}
		return 100.0*count/deviceList.size();
	}
	
	public void recovery() {
		for(int i=0; i<malwareList.size(); i++) {
			for(int j=0; j<deviceList.size(); j++) {
				if(deviceList.get(j).getMalware().contains(malwareList.get(i))) {
					if(ID-malwareList.get(i).getRecoveryPeriod() > deviceList.get(j).getTimeInfected(malwareList.get(i))) {
						deviceList.get(j).patch(malwareList.get(i));
					}
				}
			}
		}
	}
	
	public void interaction(Device device1, Device device2) {
		java.util.Random random = new java.util.Random();
		ArrayList<Malware> d1Malware = device1.getMalware();
		ArrayList<Malware> d2Malware = device2.getMalware();
		
		//transmission from device1 to device2
		for(int i=0; i<d1Malware.size(); i++) {
			//if device2 is not infected with device1's selected malware
			if(!d2Malware.contains(d1Malware.get(i))) {
				//if device2 is infected based on probability of device1's selected malware
				if(random.nextDouble()*100 <= d1Malware.get(i).getChanceInfection()) {
					//infect device2 with device1's selected malware
					device2.infect(d1Malware.get(i), ID);
				}
			}
		}
		
		//transmission from device2 to device1
		for(int i=0; i<d2Malware.size(); i++) {
			//if device1 is not infected with device2's selected malware
			if(!d1Malware.contains(d2Malware.get(i))) {
				//if device1 is infected based on probability of device2's selected malware
				if(random.nextDouble()*100 <= d2Malware.get(i).getChanceInfection()) {
					//infect device1 with device2's selected malware
					device1.infect(d2Malware.get(i), ID);
				}
			}
		}
	}
	
	public void progress(double value) {
		String bar;
		
		if(value<5) {
			bar="";
		} else if(value<15) {
			bar="#";
		} else if(value<25) {
			bar="##";
		} else if(value<35) {
			bar="###";
		} else if(value<45) {
			bar="####";
		} else if(value<55) {
			bar="#####";
		} else if(value<65) {
			bar="######";
		} else if(value<75) {
			bar="#######";
		} else if(value<85) {
			bar="########";
		} else if(value<95) {
			bar="#########";
		} else {
			bar="##########";
		}
		System.out.println("Infected: " + value + "%   \t| " + bar);
	}
	
	public void printFrame() {
		System.out.println("== Frame Results =======================");
		System.out.println("|  Total infected: " + checkInfected() + "%");
		for(int i=0; i<malwareList.size(); i++) {
			System.out.println("|  - " + malwareList.get(i).getName() + ": " + checkInfected(malwareList.get(i)) + "%");
		}
		System.out.println("========================================\n");
	}
}