package xDemic;

import java.util.ArrayList;

public class Computer 
{
	private boolean isInfected;
	private ArrayList<Virus> viruses;
	
	public Computer()
	{
		
	}
	
	public void applyPatch(Virus virus) 
	{
		System.out.println("applying the patch to fix the virus");
		
	}
	
	public void addVirus(Virus virus) 
	{
		System.out.println("This computer has been infected!");
	}
	
}
