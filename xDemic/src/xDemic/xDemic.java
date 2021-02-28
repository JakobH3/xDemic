package xDemic;

import java.util.ArrayList;

public class xDemic {
	private static final int NUM_OF_DEVICES=1000;
	private static final int NUM_OF_FRAMES=1000;
	
	public static void main(String args[]) {
		System.out.println("> Welcome to xDemic!\n");
		
		ArrayList<Device> deviceList = new ArrayList<Device>();
		ArrayList<Malware> malwareList = new ArrayList<Malware>();
		ArrayList<Frame> frameList = new ArrayList<Frame>();
		
//THIS SECTION WILL BE REPLACED WITH INPUTS FROM USER
		Malware testMW = new Malware("Malware", 5, 2, 30);
		malwareList.add(testMW);
		testMW.printInfo();
		
		for(int i=0; i<NUM_OF_DEVICES; i++) {
			deviceList.add(new Device(i));
		}
		
		for(int i=0; i<NUM_OF_FRAMES; i++) {
			frameList.add(new Frame(i, deviceList, malwareList));
		}
	}
}