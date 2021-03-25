package application;

public class Example extends Simulation {
	public Example() {
		this.getDeviceList().add(new Device(0, Integer.MAX_VALUE, 10));
		this.getDeviceList().add(new Device(1, Integer.MAX_VALUE, 20));
		this.getDeviceList().add(new Device(2, Integer.MAX_VALUE, 30));
		
		this.getMalwareList().add(new Malware("test", 20, Integer.MAX_VALUE));
		
		this.getConnectionList().add(new Connection(this.getDeviceList().get(0), this.getDeviceList().get(1)));
		
		this.getDeviceList().get(0).infect(this.getMalwareList().get(0));
	}
}
