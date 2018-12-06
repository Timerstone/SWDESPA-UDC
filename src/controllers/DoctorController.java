package controllers;

import java.time.LocalDate;
import java.time.LocalTime;

import doctortools.AddSlot;
import doctortools.EditSlot;
import doctortools.RemoveSlot;
import models.TimeSlot;

public class DoctorController extends Controllers
{
	private AddSlot addSlot;
	private EditSlot editSlot;
	private RemoveSlot removeSlot;
	
	public void setAddSlot(AddSlot addSlot)
	{
		this.addSlot = addSlot;
	}
	
	public void setEditSlot(EditSlot editSlot)
	{
		this.editSlot = editSlot;
	}
	
	public void setRemoveSlot(RemoveSlot removeSlot)
	{
		this.removeSlot = removeSlot;
	}
	
	public void addTimeSlot(LocalTime timeStart, LocalTime timeEnd, LocalDate date)
	{
		if(timeStart.isAfter(timeEnd) || timeStart.equals(timeEnd))
		{
			addSlot.setErrorMsg("Error: Invalid time input.");
		}
		else if(model.checkTimeSlot(timeStart, timeEnd, date))
		{
			addSlot.setErrorMsg("Error: Conflicts another time slot.");
		}
		else
		{
			model.addTimeSlot(timeStart, timeEnd, date, user);
		}
	}
	
	public void updateTimeSlot(TimeSlot slot, LocalTime timeStart, LocalTime timeEnd, LocalDate date)
	{
		System.out.println(slot.getTimeStart() + " " + slot.getTimeEnd());
		System.out.println(timeStart + " " + timeEnd);
		if(timeStart.equals(timeEnd) || timeStart.equals(timeEnd))
		{
			editSlot.setErrorMsg("Error: Invalid time input.");
		}
		else if(model.checkTimeSlot(timeStart, timeEnd, date, slot))
		{
			editSlot.setErrorMsg("Error: Conflicts another time slot.");
		}
		else
		{
			editSlot.cancel();
			model.updateTimeSlot(slot, timeStart, timeEnd, date);
		}
	}
	
	public void removeTimeSlot(TimeSlot slot)
	{
		model.removeTimeSlot(slot);
		removeSlot.cancel();
	}
}
