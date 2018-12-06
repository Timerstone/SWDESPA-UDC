package views;

import models.Models;

public class Checker implements Runnable
{
	private Hub hub;
	private Models model;
	private boolean running;
	
	public Checker(Hub hub, Models model)
	{
		this.hub = hub;
		this.model = model;
		this.running = true;
	}
	
	public void turnOff()
	{
		this.running = false;
	}
	
	public void turnOn()
	{
		this.running = true;
	}
	
	@Override
	public void run() 
	{
		while(running)
		{
			if(!model.checkUpdates(hub.getModule().getUser()))
			{
				model.setUpdated(hub.getModule().getUser());
				hub.update();
			}
		}
	}
}
