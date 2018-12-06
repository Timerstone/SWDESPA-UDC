package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservedSlot extends Slots
{
	private Client client;
	private Doctor doctor;
	
	public ReservedSlot(LocalTime timeStart, LocalTime timeEnd, LocalDate date, Doctor doctor, Client client)
	{
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.date = date;
		this.client = client;
		this.doctor = doctor;
		this.isDone = false;
	}
	
	public void setClient(Client client) 
	{
		this.client = client;
	}
	public void setDoctor(Doctor doctor) 
	{
		this.doctor = doctor;
	}
	
	public Doctor getDoctor() 
	{
		return doctor;
	}
	
	public Client getClient() 
	{
		return client;
	}
}
