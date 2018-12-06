package factories;

import java.time.LocalDate;
import java.time.LocalTime;

import models.Client;
import models.Doctor;
import models.ReservedSlot;
import models.Slots;
import models.TimeSlot;

public class SlotFactory
{
	public Slots makeSlot(String type, LocalTime timeStart, LocalTime timeEnd, LocalDate date, Doctor doctor, Client client, boolean isReserved)
	{
		switch(type.toUpperCase())
		{
			case "TIMESLOT": 
				TimeSlot timeslot = new TimeSlot(timeStart, timeEnd, date, doctor, false);
				return timeslot;
			case "RESERVEDSLOT":
				ReservedSlot reservedslot = new ReservedSlot(timeStart, timeEnd, date, doctor, client);
				return reservedslot;
		}
		
		return null;
	}
}
