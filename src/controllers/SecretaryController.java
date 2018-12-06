package controllers;

import java.time.LocalDate;
import java.time.LocalTime;

import models.Doctor;

public class SecretaryController extends Controllers
{
	public void takeAppointment(LocalTime timeStart, LocalTime timeEnd, LocalDate date, Doctor doctor, String client)
	{
		model.createTemporaryClient(client, user.getUserID());
		model.addSecretaryReservedSlot(timeStart, timeEnd, date, doctor, client);
		
	}
}
