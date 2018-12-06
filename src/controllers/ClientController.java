package controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import clienttools.TakeSlot;
import models.Client;
import models.Doctor;
import models.Slots;
import models.Users;

public class ClientController extends Controllers
{
	private TakeSlot takeSlot;

	public void setTakeSlot(TakeSlot takeSlot) 
	{
		this.takeSlot = takeSlot;
	}

	
	public void addReservation(LocalTime timeStart, LocalTime timeEnd, Users user, Doctor doctor, LocalDate date) 
	{
		ArrayList<Slots> resSlots = new ArrayList<Slots>();
		resSlots = 	model.getReservedSlots(date,doctor);
		ArrayList<Slots> doctorApptSlots = model.getTimeSlots(date, doctor);
		
		boolean isValid = true;
		for (int i=0; i<resSlots.size(); i++)
		{
			LocalTime resTimeStart = resSlots.get(i).getTimeStart();
			LocalTime resTimeEnd = resSlots.get(i).getTimeEnd();
			
			if (timeStart.isAfter(resTimeStart) && timeEnd.isBefore(resTimeEnd))
			{
				isValid = false; 
				System.out.println("is false #1");
				break;
			}
			else if (timeStart.isAfter(resTimeStart) && timeStart.isBefore(resTimeEnd)) 
			{
				isValid = false;
				System.out.println("is false #2");break;
			}
			else if (timeEnd.isAfter(resTimeStart) && timeEnd.isBefore(resTimeEnd))
			{	
				isValid = false;
				System.out.println("is false #3");break;
			}
			else if(timeStart.equals(resTimeStart))
			{
				isValid = false;
				System.out.println("is false #4");break;
			}
		}
		
		boolean isInApptSlot = false;
		for (int i=0; i<doctorApptSlots.size(); i++)
		{
			LocalTime docTimeStart = doctorApptSlots.get(i).getTimeStart();
			LocalTime docTimeEnd = doctorApptSlots.get(i).getTimeEnd();
			
			if(timeStart.isAfter(docTimeStart) && timeEnd.isBefore(docTimeEnd)) {
				isInApptSlot = true;
				break;
			}
			
		}
		
		
		if(timeStart.isAfter(timeEnd) || timeStart.equals(timeEnd))
		{
			takeSlot.setErrorMsg("Error: Invalid time input.");
		}
		else if (doctor.getName().equalsIgnoreCase("")) 
		{
			takeSlot.setErrorMsg("Error: Doctor not found!");
		}
		else if (isValid == false) 
		{
			takeSlot.setErrorMsg("Error: Conflicts with another reservation!");
		}
		else if(isInApptSlot == false) 
		{
			takeSlot.setErrorMsg("Error: Resrvation is not within the doctor's appointment slot!");
		}
		else
		{
			takeSlot.cancel();
			model.addReservationSlot(timeStart, timeEnd, date, doctor, (Client) user);
		}
	}
	
	public void cancelReservation()
	{
		
	}
}
