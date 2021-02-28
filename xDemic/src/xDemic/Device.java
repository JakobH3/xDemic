package xDemic;

import java.util.ArrayList;

public class Device {
	private int ID; //identifier for the device
	private ArrayList<Malware> malwareList = new ArrayList<Malware>(); //list of all malware active on the device
	
	public Device(int iID) {
		ID=iID;
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
		//System.out.println(">  Device " + ID + " infected with " + malware.getName() + "!\n");
	}
	
	public void patch(Malware malware) {
		if(malwareList.remove(malware)) {
			//System.out.println(">  Device " + ID + " patched " + malware.getName() + "!\n");
		} else {
			//System.out.println(">  Device " + ID + " is not infected with " + malware.getName() + ".\n");
		}
		
	}
	
	public void printInfo() {
		System.out.println("== Device Info =========================");
		System.out.println("|  ID: " + ID);
		if(malwareList.size()==0) {
			System.out.println("|  Device " + ID + " is not infected.");
		} else {
			System.out.println("|  Device " + ID + " is infected with:");
			for(int i=0; i<malwareList.size(); i++) {
				System.out.println("|  - " + (malwareList.get(i)).getName());
			}
		}
		System.out.println("========================================\n");
	}
}