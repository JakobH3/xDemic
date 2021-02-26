package xDemic;

import java.util.ArrayList;

public class Device {
	private int ID; //identifier for the device
	private ArrayList<Malware> malwareList = new ArrayList<Malware>(); //list of all malware active on the device
	
	public Device(int nextID) {
		ID=nextID;
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
		System.out.println("Device " + ID + " infected!\n");
	}
	
	public void patch(Malware malware) {
		malwareList.remove(malware);
		System.out.println("Device " + ID + " patched!\n");
	}
	
	public void printInfo() {
		System.out.println("ID: " + ID);
		if(malwareList.size()==0) {
			System.out.println("Device " + ID + " is not infected.\n");
		} else {
			System.out.println("Device " + ID + " is infected with:");
			for(int i=0; i<malwareList.size(); i++) {
				System.out.println("\t" + (malwareList.get(i)).getName());
			}
		}
	}
}