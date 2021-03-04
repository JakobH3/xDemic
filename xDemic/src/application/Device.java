package application;

import java.util.ArrayList;

public class Device {
	java.util.Random random = new java.util.Random();
	
	private int ID; //identifier for the device
	private ArrayList<Malware> malwareList = new ArrayList<Malware>(); //list of all malware active on the device
	private ArrayList<Malware> patchedMalwareList = new ArrayList<Malware>(); //list of which malware have been patched
	private int timeToPatch=Integer.MAX_VALUE;
	private double resistance=0; //probability of a device resisting malware
	
	public Device(int iID) {
		ID=iID;
	}
	
	public Device(int iID, int iTimeToPatch, double iResistance) {
		ID=iID;
		timeToPatch=iTimeToPatch;
		resistance=iResistance;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getTimeToPatch() {
		return timeToPatch;
	}
	
	public double getResistance() {
		return resistance;
	}
	
	public ArrayList<Malware> getMalware() {
		return malwareList;
	}
	
	public boolean isInfected() {
		return malwareList.isEmpty()?false:true;
	}
	
	public void initialInfect(Malware malware) {
		malwareList.add(malware);
		//System.out.println(">  Device " + ID + " infected with " + malware.getName() + "!\n");
	}
	
	public void infect(Malware malware) {
		//make sure device is not already infected
		if(!malwareList.contains(malware) && !patchedMalwareList.contains(malware)) {
			//check if device will resist the attack
			if(resistance < 100*random.nextDouble()) {
				malwareList.add(malware);
				//System.out.println(">  Device " + ID + " infected with " + malware.getName() + "!\n");
			}
		}
	}
	
	public void patch(Malware malware) {
		if(malwareList.contains(malware)) {
			malwareList.remove(malware);
			patchedMalwareList.add(malware);
			//System.out.println(">  Device " + ID + " patched from " + malware.getName() + "!\n");
		}
		
	}
	
	public void printInfo() {
		System.out.println("== Device Info ===================================");
		System.out.println("|  ID: " + ID);
		if(malwareList.size()==0) {
			System.out.println("|  Device " + ID + " is not infected.");
		} else {
			System.out.println("|  Device " + ID + " is infected with:");
			for(int i=0; i<malwareList.size(); i++) {
				System.out.println("|  - " + (malwareList.get(i)).getName());
			}
		}
		System.out.println("==================================================\n");
	}
}