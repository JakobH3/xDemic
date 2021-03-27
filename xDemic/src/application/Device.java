package application;

import java.util.ArrayList;

public class Device {
	java.util.Random random = new java.util.Random();
	
	private int Id; //identifier for the device
	private ArrayList<Malware> malwareList = new ArrayList<Malware>(); //list of all malware active on the device
	private ArrayList<Malware> patchedMalwareList = new ArrayList<Malware>(); //list of which malware have been patched
	private int timeToPatch; //number of time units until the malware is patched
	private double resistance; //probability of a device resisting malware
	
	public Device(int Id, int timeToPatch, double resistance) {
		this.Id=Id;
		this.timeToPatch=timeToPatch;
		this.resistance=resistance;
	}
	
	public void setId(int in) {
		Id=in;
	}
	
	public int getId() {
		return Id;
	}
	
	public void setTimeToPatch(int timeToPatch) {
		this.timeToPatch=timeToPatch;
	}
	
	public int getTimeToPatch() {
		return timeToPatch;
	}
	
	public void setResistance(double resistance) {
		this.resistance=resistance;
	}
	
	public double getResistance() {
		return resistance;
	}
	
	public boolean isInfected() {
		return malwareList.isEmpty()?false:true;
	}
	
	public ArrayList<Malware> getMalware() {
		return malwareList;
	}
		
	public void initialInfect(Malware malware) {
		malwareList.add(malware);
	}
	
	public void infect(Malware malware) {
		//make sure device is not already infected
		if(!malwareList.contains(malware) && !patchedMalwareList.contains(malware)) {
			//check if device will resist the attack
			if(resistance < 100*random.nextDouble()) {
				malwareList.add(malware);
				System.out.println("Device infected");
			}
		}
	}
	
	public void patch(Malware malware) {
		if(malwareList.contains(malware)) {
			malwareList.remove(malware);
			patchedMalwareList.add(malware);
		}
		
	}
	
	public boolean isPatched() {
		return (patchedMalwareList.size() > 0);
	}
	
	public void printInfo() {
		System.out.println("== Device Info ===================================");
		System.out.println("|  ID: " + Id);
		if(malwareList.size()==0) {
			System.out.println("|  Device " + Id + " is not infected.");
		} else {
			System.out.println("|  Device " + Id + " is infected with:");
			for(int i=0; i<malwareList.size(); i++) {
				System.out.println("|  - " + (malwareList.get(i)).getName());
			}
		}
		System.out.println("==================================================\n");
	}
}