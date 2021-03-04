package application;

import java.util.ArrayList;

public class xDemic {
	private static final int NUM_OF_MALWARE=1;
	private static final int NUM_OF_DEVICES=1000;
	private static final int NUM_OF_FRAMES=100;
	
	public xDemic() {
		java.util.Random random = new java.util.Random();
		
		System.out.println("\n> Welcome to xDemic!\n");
		
		ArrayList<Device> deviceList = new ArrayList<Device>();
		ArrayList<Malware> malwareList = new ArrayList<Malware>();
		ArrayList<Frame> frameList = new ArrayList<Frame>();
		
		for(int i=0; i<NUM_OF_DEVICES; i++) {
			deviceList.add(new Device(i, random.nextInt(100)));
		}
		
		System.out.println("== Simulation Info ===============================");
		System.out.println("| Number of devices: " + NUM_OF_DEVICES);
		System.out.println("==================================================\n");
		
		Malware test = new Malware("Test", 20, 10, 10);
		malwareList.add(test);
		test.printInfo();
		
		for(int i=0; i<NUM_OF_FRAMES; i++) {
			frameList.add(new Frame(i, deviceList, malwareList));
		}
		
		System.out.println("");
		
		System.out.println("> Simulation Complete.\n");
		System.out.println("== Simulation Results ============================");
		System.out.println("|  Total infected: " + frameList.get(NUM_OF_FRAMES-1).checkInfected() + "%");
		for(int i=0; i<NUM_OF_MALWARE; i++) {
			System.out.println("|  - " + malwareList.get(i).getName() + ": " + frameList.get(NUM_OF_FRAMES-1).checkInfected(malwareList.get(i)) + "%");
		}
		System.out.println("==================================================\n");
	}
}