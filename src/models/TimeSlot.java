package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeSlot extends Slots
{
	private Doctor doctor;
	private boolean isReserved;
	
	public TimeSlot(LocalTime timeStart, LocalTime timeEnd, LocalDate date, Doctor doctor, boolean isReserved)
	{
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.date = date;
		this.doctor = doctor;
		this.isReserved = isReserved;
		this.isDone = false;
	}
	
	public void setDoctor(Doctor doctor)
	{
		this.doctor = doctor;
	}
	
	public void setReserved(boolean isReserved)
	{
		this.isReserved = isReserved;
	}
	
	public Doctor getDoctor()
	{
		return doctor;
	}
	
	public boolean isReserved()
	{
		return isReserved;
	}
}
