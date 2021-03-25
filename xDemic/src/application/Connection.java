package application;

import java.util.ArrayList;

public class Connection {
	private ArrayList<Device> list = new ArrayList<Device>();
	
	Connection(Device device1, Device device2) {
		list.add(device1);
		list.add(device2);
	}
	
	public ArrayList<Device> getConnection() {
		return list;
	}
}