package parsers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import models.Client;
import models.Doctor;
import models.ReservedSlot;
import models.TimeSlot;
import models.Users;

public abstract class DataParsers 
{
	public abstract boolean checkLogin(int userID);
	public abstract boolean checkUpdateTag(Users user);
	
	public abstract void LogIn(Users user);
	public abstract void LogOut(Users user);
	public abstract String getUser(String username, String password);
	public abstract String getUser(int userID);
	public abstract String getDoctor(int doctorID);
	public abstract String getClient(int clientID);
	public abstract String getSecretary(int secretaryID);
	public abstract ArrayList<String> getDoctors();
	public abstract ArrayList<String> getTemporaryClients();
	
	public abstract ArrayList<String> getTimeSlots(Doctor doctor);
	public abstract ArrayList<String> getTimeSlots(LocalDate date);
	public abstract ArrayList<String> getTimeSlots(Doctor doctor, LocalDate date);
	
	public abstract ArrayList<String> getReservations(Client client);
	public abstract ArrayList<String> getReservations(Client client, LocalDate date);
	public abstract ArrayList<String> getReservations(Doctor doctor, LocalDate date);
	
	public abstract void saveTemporaryClient(Client client);
	
	public abstract void saveTimeSlot(TimeSlot slot);
	public abstract void saveReservation(ReservedSlot slot);
	
	public abstract void modifyTimeSlot(TimeSlot slot, LocalTime newTimeStart, LocalTime newTimeEnd);
	public abstract void modifyTimeSlot(TimeSlot slot, LocalDate newDate);
	public abstract void modifyTimeSlot(TimeSlot slot, LocalDate newDate, LocalTime newTimeStart, LocalTime newTimeEnd);
	public abstract void removeTimeSlot(TimeSlot slot);
	public abstract void removeReservedSlot(ReservedSlot slot);
}
