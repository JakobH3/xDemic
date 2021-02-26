package xDemic;

import java.util.ArrayList;

public class Device {
	private int ID; //identifier for the device
	private ArrayList<Malware> malwareList; //list of all malware active on the device
	
	public Device() {
		// CONSTRUCTOR
	}
	
	public int getID() {
		return ID;
	}
	
	public ArrayList<Malware> getMalware() {
		return malwareList;
	}
	
	public boolean isInfected() {
		return malwareList.isEmpty()?false:true;
	}
	
	public void infect(Malware malware) {
		malwareList.add(malware);
		System.out.println("Device " + ID + " Infected!");
	}
	
	public void patch(Malware malware) {
		malwareList.remove(malware);
		System.out.println("Device " + ID + " Patched!");
	}
}