package xDemic;

import java.util.ArrayList;

public class xDemic {
	public static void main(String args[]) {
		System.out.println("> Welcome to xDemic!\n");
		
		ArrayList<Device> deviceList = new ArrayList<Device>();
		ArrayList<Malware> malwareList = new ArrayList<Malware>();
		ArrayList<Frame> frameList = new ArrayList<Frame>();
		
//THIS SECTION WILL BE REPLACED WITH INPUTS FROM USER
		/*Malware testMW = new Malware();
		malwareList.add(testMW);
		testMW.printInfo();
		
		Malware testMW1 = new Malware("test1");
		malwareList.add(testMW1);
		testMW1.printInfo();*/
		
		Malware testMW2 = new Malware("test2", 5, 2);
		malwareList.add(testMW2);
		testMW2.printInfo();
		
		for(int i=0; i<1000; i++) {
			deviceList.add(new Device(i));
		}
		
		for(int i=0; i<100; i++) {
			frameList.add(new Frame(i, deviceList, malwareList));
		}
	}
	
	
}