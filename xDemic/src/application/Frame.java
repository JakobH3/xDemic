package application;

import java.util.ArrayList;

public class Frame {
	private java.util.Random random = new java.util.Random();
	
	private int ID;
	private ArrayList<Device> deviceList = new ArrayList<Device>();
	private ArrayList<Malware> malwareList = new ArrayList<Malware>();
	
	public Frame(int iID, ArrayList<Device> iDeviceList, ArrayList<Malware> iMalwareList) {
		ID=iID;
		deviceList=iDeviceList;
		malwareList=iMalwareList;
		
		if(ID==0) {
			initialize();
		} else {
			//see if any devices can be patched
			checkPatches();
			//interact pre-determined number of times
			for(int i=0; i<deviceList.size(); i++) {
				//choose two random devices to interact
				interact(deviceList.get(random.nextInt(deviceList.size())), deviceList.get(random.nextInt(deviceList.size())));
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
					deviceList.get(j).infect(malwareList.get(i));
				}
			}
		}
		//print the initialization status
		System.out.println("> Initialization Complete.\n");
		System.out.println("== Initialization Results ========================");
		System.out.println("|  Total infected: " + checkInfected() + "%");
		for(int i=0; i<malwareList.size(); i++) {
			System.out.println("|  - " + malwareList.get(i).getName() + ": " + checkInfected(malwareList.get(i)) + "%");
		}
		System.out.println("==================================================\n");
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
	
	public void checkPatches() {
		for(int i=0; i<malwareList.size(); i++) {
			for(int j=0; j<deviceList.size(); j++) {
				if(deviceList.get(j).getTimeToPatch() == ID-malwareList.get(i).getPatchRelease()) {
					deviceList.get(j).patch(malwareList.get(i));
				}
			}
		}
	}
	
	public void interact(Device device1, Device device2) {
		ArrayList<Malware> d1Malware = device1.getMalware();
		ArrayList<Malware> d2Malware = device2.getMalware();
		
		//transmission from device1 to device2
		for(int i=0; i<d1Malware.size(); i++) {
			//if device2 is not infected with device1's selected malware
			if(!d2Malware.contains(d1Malware.get(i))) {
				//if device2 is infected based on probability of device1's selected malware
				if(random.nextDouble()*100 <= d1Malware.get(i).getChanceInfection()) {
					//infect device2 with device1's selected malware
					device2.infect(d1Malware.get(i));
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
					device1.infect(d2Malware.get(i));
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
		System.out.println("# | " + bar);
	}
	
	public void printFrame() {
		System.out.println("== Frame Results =================================");
		System.out.println("|  Total infected: " + checkInfected() + "%");
		for(int i=0; i<malwareList.size(); i++) {
			System.out.println("|  - " + malwareList.get(i).getName() + ": " + checkInfected(malwareList.get(i)) + "%");
		}
		System.out.println("==================================================\n");
	}
}