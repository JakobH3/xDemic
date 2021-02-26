package xDemic;

import java.util.ArrayList;

public class xDemic {
	public static void main(String args[]) {
		ArrayList<Device> deviceList = new ArrayList<Device>();
		ArrayList<Malware> malwareList = new ArrayList<Malware>();
		
		System.out.println("== Welcome to xDemic! ==\n");
		
		Malware testMW1 = new Malware();
		malwareList.add(testMW1);
		testMW1.printInfo();
		
		Malware testMW2 = new Malware("test2", 90, 6);
		malwareList.add(testMW2);
		testMW2.printInfo();
		
		Device testD1 = new Device(0);
		testD1.infect(testMW1);
		testD1.infect(testMW2);
		testD1.printInfo();
	}
}