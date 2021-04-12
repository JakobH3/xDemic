package application;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Device {
	java.util.Random random = new java.util.Random();
	
	private ArrayList<Malware> malwareList = new ArrayList<Malware>(); // list of all malware active on the device
	private ArrayList<Malware> patchedMalwareList = new ArrayList<Malware>(); // list of which malware have been patched
	private double resistance; // probability of a device resisting malware
	private int timeToPatch; // number of time units until the malware is patched
	
	private double x=-1;
	private double y=-1;
	private double size;
	private Circle c;
	
	public Device(double resistance, int timeToPatch) {
		this.resistance=resistance;
		this.timeToPatch=timeToPatch;
	}
	
	public void setResistance(double resistance) {
		this.resistance=resistance;
	}
	
	public double getResistance() {
		return resistance;
	}
	
	public void setTimeToPatch(int timeToPatch) {
		this.timeToPatch=timeToPatch;
	}
	
	public ArrayList<Malware> getMalwareList() {
		return malwareList;
	}
	
	public ArrayList<Malware> getPatchedMalwareList() {
		return patchedMalwareList;
	}
	
	public int getTimeToPatch() {
		return timeToPatch;
	}
	
	public boolean isInfected() {
		return malwareList.isEmpty()?false:true;
	}
	
	public ArrayList<Malware> getMalware() {
		return malwareList;
	}
	
	public void infect(Malware malware) {
		// make sure device is not already infected
		if(!malwareList.contains(malware) && !patchedMalwareList.contains(malware)) {
			malwareList.add(malware);
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
		if(malwareList.size()==0) {
			System.out.println("|  Device is not infected.");
		} else {
			System.out.println("|  Device is infected with:");
			for(int i=0; i<malwareList.size(); i++) {
				System.out.println("|  - " + (malwareList.get(i)).getName());
			}
		}
		System.out.println("==================================================\n");
	}
	
	public void draw() {
		c.setCenterX(x);
		c.setCenterY(y);
		
		c.setRadius(size);
		
		if(isInfected()) {
			c.setFill(Color.RED);
		} else if(isPatched()) {
			c.setFill(Color.GREEN);
		} else {
			c.setFill(Color.WHITE);
		}
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setSize(double size) {
		this.size = size;
	}
	
	public Circle getC() {
		return c;
	}

	public void setC(Circle c) {
		this.c = c;
	}
}