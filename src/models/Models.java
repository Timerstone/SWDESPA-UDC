package models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import factories.SlotFactory;
import factories.UserFactory;
import parsers.DataParsers;
import parsers.DatabaseParser;

public class Models 
{
	private DataParsers parser;
	private UserFactory userFactory;
	private SlotFactory slotFactory;
	private DatabaseParser checker;
	
	public Models()
	{
		parser = new DatabaseParser();
		checker = new DatabaseParser();
		userFactory = new UserFactory();
		slotFactory = new SlotFactory();
	}
	
	public boolean checkLogin(String username, String password)
	{
		return parser.checkLogin(getUser(username, password).getUserID());
	}
	
	public void LogIn(Users user)
	{
		parser.LogIn(user);
	}
	
	public void LogOut(Users user)
	{
		parser.LogOut(user);
	}
	
	public Users getUser(String username, String password)
	{
		String data[] = parser.getUser(username, password).split(",");
		
		if(data != null)
		{
			return userFactory.makeUser(data[0], data[1], data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4]));
		}
		
		return null;
	}
	
	public Users getUser(int userID)
	{
		String data[] = parser.getUser(userID).split(",");
		
		if(data != null)
		{
			return userFactory.makeUser(data[0], data[1], data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4]));
		}
		
		return null;
	}
	
	public Client getClient(int clientID)
	{
		String data[] = parser.getClient(clientID).split(",");
		
		if(data != null)
		{
			return (Client) userFactory.makeUser(data[0], data[1], data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4]));
		}
		
		return null;
	}
	
	public Doctor getDoctor(int doctorID)
	{
		String data[] = parser.getDoctor(doctorID).split(",");
		
		if(data != null)
		{
			return (Doctor) userFactory.makeUser(data[0], data[1], data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4]));
		}
		
		return null;
	}
	
	public ArrayList<Doctor> getDoctors()
	{
		ArrayList<Doctor> doctors = new ArrayList<Doctor>();
		ArrayList<String> list;
		String data[];
		
		list = parser.getDoctors();
		
		for(int i = 0; i < list.size(); i++)
		{
			data = list.get(i).split(",");
			
			Doctor doctor = (Doctor) (Doctor) userFactory.makeUser(data[0], data[1], data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4]));
			doctors.add(doctor);
		}
		
		return doctors;
	}
	
	public boolean checkTimeSlot(LocalTime timeStart, LocalTime timeEnd, LocalDate date)
	{
		ArrayList<Slots> list;
		LocalTime start;
		LocalTime end;
		
		
		list = getTimeSlots(date);
		
		for(int i = 0; i < list.size(); i++)
		{
			start = list.get(i).getTimeStart();
			end = list.get(i).getTimeEnd();
			
			if(start.equals(timeStart) || end.equals(timeEnd))
				return true;
			else if(start.isBefore(timeStart) && end.isAfter(timeStart))
				return true;
			else if(start.isBefore(timeEnd) && end.isAfter(timeEnd))
				return true;
			else if(start.isBefore(timeStart) && end.equals(timeEnd))
				return true;
			else if(start.equals(timeStart) && end.isAfter(timeEnd))
				return true;
		}
		
		return false;
	}
	
	public boolean checkTimeSlot(LocalTime timeStart, LocalTime timeEnd, LocalDate date, TimeSlot slot)
	{		
		ArrayList<Slots> list;
		LocalTime start;
		LocalTime end;
		
		
		list = getTimeSlots(date, slot.getDoctor());
		
		for(int i = 0; i < list.size(); i++)
		{
			start = list.get(i).getTimeStart();
			end = list.get(i).getTimeEnd();
			
			if(!(start.equals(slot.getTimeStart()) && end.equals(slot.getTimeEnd()) && slot.getDate().equals(date))) 
			{
				if(start.equals(timeStart) || end.equals(timeEnd))
				{
					System.out.println("1"); 
					return true;
				}
				else if(start.isBefore(timeStart) && end.isAfter(timeStart))
				{
					System.out.println("2"); 
					return true;
				}
				else if(start.isBefore(timeEnd) && end.isAfter(timeEnd))
				{
					System.out.println("3"); 
					return true;
				}
				else if(start.isBefore(timeStart) && end.equals(timeEnd))
				{
					System.out.println("4"); 
					return true;
				}
				else if(start.equals(timeStart) && end.isAfter(timeEnd))
				{
					System.out.println("5"); 
					return true;
				}
			}
		}
		
		return false;
	}
	
	public ArrayList<Slots> getTimeSlots(LocalDate date, Doctor doctor)
	{	
		ArrayList<Slots> slots = new ArrayList<Slots>();
		ArrayList<Slots> reserved = getReservedSlots(date, doctor);
		ArrayList<String> list;
		LocalTime timeStart;
		LocalTime timeEnd;
		TimeSlot slotA;
		TimeSlot slotB;
		TimeSlot slotC;
		
		list = parser.getTimeSlots(doctor, date);
		for(int i = 0; i < list.size(); i++)
		{
			String data[] = list.get(i).split(",");
			
			slotA = (TimeSlot) slotFactory.makeSlot(data[0], LocalTime.parse(data[1]), LocalTime.parse(data[2]), date, doctor, null, false);
			
			slots.add(slotA);
		}
		
		for(int i = 0; i < list.size(); i++)
		{
			for(int j = 0; j < reserved.size(); j++)
			{
				slotA = (TimeSlot) slots.get(i);
				
				if(slotA.getTimeStart().equals(reserved.get(j).getTimeStart()) && slotA.getTimeEnd().equals(reserved.get(j).getTimeEnd()))
					slotA.setReserved(true);
				else if(slotA.getTimeStart().equals(reserved.get(j).getTimeStart()) && slotA.getTimeEnd().isAfter(reserved.get(j).getTimeEnd()))
				{
					//TS=RS && RE<TE
					timeEnd = slotA.getTimeEnd();
					
					slotA.setTimeEnd(reserved.get(j).getTimeEnd());
					slotA.setReserved(true);
					
					slotB = (TimeSlot) slotFactory.makeSlot("timeslot", slotA.getTimeEnd(), timeEnd, date, slotA.getDoctor(), null, false);
					
					slots.add(slotB);
				}
				else if(slotA.getTimeStart().isBefore(reserved.get(j).getTimeStart()) && slotA.getTimeEnd().equals(reserved.get(j).getTimeEnd()))
				{
					//TS<RS && RE=TE
					timeStart = slotA.getTimeStart();
					
					slotA.setReserved(true);
					slotA.setTimeStart(reserved.get(j).getTimeStart());
					
					slotB = (TimeSlot) slotFactory.makeSlot("timeslot", timeStart, slotA.getTimeStart(), date, slotA.getDoctor(), null, false);
					slotB.setDoctor(doctor);
					
					slots.add(slotB);
				}
				else if(slotA.getTimeStart().isBefore(reserved.get(j).getTimeStart()) && slotA.getTimeEnd().isAfter(reserved.get(j).getTimeEnd()))
				{
					//TS<RS && RE<TE
					timeEnd = slotA.getTimeEnd();
					timeStart = slotA.getTimeStart();
					
					slotA.setReserved(true);
					slotA.setTimeStart(reserved.get(j).getTimeStart());
					slotA.setTimeEnd(reserved.get(j).getTimeEnd());
					
					slotB = (TimeSlot) slotFactory.makeSlot("timeslot", timeStart, reserved.get(j).getTimeStart(), date, slotA.getDoctor(), null, false);
					
					slots.add(slotB);
					
					slotC = (TimeSlot) slotFactory.makeSlot("timeslot", reserved.get(j).getTimeEnd(), timeEnd, date, slotA.getDoctor(), null, false);
					
					slots.add(slotC);
				}
			}
		}
		
		return slots;
	}
	
	public ArrayList<Slots> getTimeSlots(LocalDate date)
	{		
		ArrayList<Slots> slots = new ArrayList<Slots>();
		ArrayList<String> list;
		
		list = parser.getTimeSlots(date);
		for(int i = 0; i < list.size(); i++)
		{
			String data[] = list.get(i).split(",");
			
			Doctor doctor = getDoctor(Integer.parseInt(data[4]));
			
			TimeSlot slot = (TimeSlot) slotFactory.makeSlot(data[0], LocalTime.parse(data[1]), LocalTime.parse(data[2]), date, doctor, null, false);
			slots.add(slot);
		}
		
		return slots;
	}
	
	public ArrayList<Slots> getReservedSlots(LocalDate date, Doctor doctor)
	{		
		ArrayList<Slots> slots = new ArrayList<Slots>();
		ArrayList<String> list;
		
		//@return ArrayList of string = "timeStart,timeEnd,date,clientID,doctorID"
		list = parser.getReservations(doctor, date);
		
		for(int i = 0; i < list.size(); i++)
		{
			String data[] = list.get(i).split(",");
			
			Client client = getClient(Integer.parseInt(data[5]));
			
			ReservedSlot slot = (ReservedSlot) slotFactory.makeSlot(data[0], LocalTime.parse(data[1]), LocalTime.parse(data[2]), date, doctor, client, false);
			slot.setDoctor(doctor);
			slot.setClient(client);
			slots.add(slot);
		}
		
		return slots;
	}
	
	public ArrayList<Slots> getReservedSlots(LocalDate date, Client client)
	{
		/* TODO
		 * - Creates a compilation of reserved slots.
		 * - Searches the database using a specific date and a specific client.
		 * - Returns the list if entries are found.
		 */
		
		
		
		
		return null;
	}
	
	public void addTimeSlot(LocalTime timeStart, LocalTime timeEnd, LocalDate date, Users user)
	{
		/*
		 * - Saves a new time slot entry into the database.
		 * - Creates a time slot object first.
		 * - Pass the object to the data parser.
		 * - Save the object in the database.
		 */
		
		
		TimeSlot timeSlot =  (TimeSlot) slotFactory.makeSlot("timeslot", timeStart, timeEnd, date, (Doctor) user, null, false);
		
		this.parser.saveTimeSlot(timeSlot);
	}
	
	public void addSecretaryReservedSlot(LocalTime timeStart, LocalTime timeEnd, LocalDate date, Doctor doctor, String clientName)
	{
		// FIXME
		String temp[];
		Client tempClient = null;
		ArrayList<String> tempClients = parser.getTemporaryClients();
		
		for(int i = 0; i < tempClients.size(); i++)
		{
			if((temp = tempClients.get(i).split(","))[1].equals(clientName))
			{
				tempClient = (Client) userFactory.makeUser("client", clientName, null, 0, 0);
				tempClient.setClientID(Integer.parseInt(temp[3]));
				break;
			}
		}
		
		ReservedSlot slot = (ReservedSlot) slotFactory.makeSlot("reservedslot", timeStart, timeEnd, date, doctor, tempClient, false);
		parser.saveReservation(slot);
		
	}
	
	public ArrayList<Client> getTemporaryClient()
	{
		ArrayList<String> data = parser.getTemporaryClients();
		ArrayList<Client> tempClients = new ArrayList<Client>();
		String temp[];  
		
		
		for(int i = 0; i <data.size();i++)
		{
			temp = data.get(i).split(",");
			tempClients.add((Client) userFactory.makeUser("client", temp[1], null, Integer.parseInt(temp[3]), Integer.parseInt(temp[4])));
			tempClients.get(tempClients.size() - 1).setClientID(Integer.parseInt(temp[3]));
		}
		
		return tempClients;
	}
	
	
	public void updateTimeSlot(TimeSlot slot, LocalTime timeStart, LocalTime timeEnd, LocalDate date)
	{		
		parser.modifyTimeSlot(slot,  date,  timeStart, timeEnd);
	}
	
	public void updateTimeSlot(TimeSlot slot, LocalDate date)
	{
		parser.modifyTimeSlot(slot, date);
	}
	
	public void removeTimeSlot(TimeSlot slot)
	{
		parser.removeTimeSlot(slot);
	}
	
	public void createTemporaryClient(String clientName, int userID)
	{
		Client client = (Client) userFactory.makeUser("client", clientName, null, userID, 0);
		parser.saveTemporaryClient(client);	
	}
	
	public void addReservationSlot(LocalTime timeStart, LocalTime timeEnd, LocalDate date, Doctor doctor, Client client)
	{
		/*
		 * - Saves a new reservation slot entry into the database.
		 * - Creates a time slot object first.
		 * - Pass the object to the data parser.
		 * - Save the object in the database.
		 */
				
		ReservedSlot reservedSlot = (ReservedSlot) slotFactory.makeSlot("reservedslot", timeStart, timeEnd, date, doctor, client, false);
		this.parser.saveReservation(reservedSlot);
	}
	
	public boolean checkUpdates(Users user)
	{
		return checker.checkUpdateTag(user);
	}
	
	public void setUpdated(Users user)
	{
		checker.setUpdated(user);
	}
	
	public void updateAll()
	{
		checker.updateAll();
	}
}
