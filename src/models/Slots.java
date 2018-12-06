package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Slots 
{
	protected LocalTime timeStart;
	protected LocalTime timeEnd;
	protected LocalDate date;
	protected boolean isDone;
	
	public void setTimeStart(LocalTime timeStart) 
	{
		this.timeStart = timeStart;
	}
	
	public void setTimeEnd(LocalTime timeEnd) 
	{
		this.timeEnd = timeEnd;
	}
	
	public void setDone(boolean isDone) 
	{
		this.isDone = isDone;
	}
	
	public void setDate(LocalDate date) 
	{
		this.date = date;
	}
	
	public LocalTime getTimeStart() 
	{
		return timeStart;
	}
	
	public LocalTime getTimeEnd()
	{
		return timeEnd;
	}
	
	public LocalDate getDate() 
	{
		return date;
	}
	
	public boolean isDone() 
	{
		return isDone;
	}
		
}
