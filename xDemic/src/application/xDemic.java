package application;

import java.util.ArrayList;

public class xDemic {
	private ArrayList<Frame> frameList = new ArrayList<Frame>();
	
	public xDemic(ArrayList<Device> deviceList, ArrayList<Malware> malwareList) {
		if(deviceList.size()>0 && malwareList.size()>0 && frameList.size()>0) {
			System.out.println("== Simulation Info ===============================");
			System.out.println("| Number of devices: " + (deviceList.size()));
			System.out.println("==================================================\n");
			
			System.out.println("== Malware Info ==================================");
			for(int i=0; i<malwareList.size(); i++) {
				malwareList.get(i).printInfo();
				System.out.println();
			}
			System.out.println("==================================================\n");
			
			//execute the simulation
			/* TODO run the simulation */
			
			//print simulation results
			System.out.println("> Simulation Complete.\n");
			System.out.println("== Simulation Results ============================");
			System.out.println("|  Total infected: " + frameList.get(frameList.size()-1).checkInfected() + "%");
			for(int i=0; i<malwareList.size(); i++) {
				System.out.println("|  - " + malwareList.get(i).getName() + ": " + frameList.get(frameList.size()-1).checkInfected(malwareList.get(i)) + "%");
			}
			System.out.println("==================================================\n");
		} else {
			if(deviceList.size()<=0) {
				System.out.println("ERROR: No devices created!");
			}
			if(malwareList.size()<=0) {
				System.out.println("ERROR: No malware created!");
			}
		}
	}
	
	public ArrayList<Frame> getFrameList() {
		return frameList;
	}
}