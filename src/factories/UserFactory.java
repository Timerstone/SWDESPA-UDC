package factories;

import models.Admin;
import models.Client;
import models.Doctor;
import models.Secretary;
import models.Users;

public class UserFactory 
{
	public Users makeUser(String userType, String name, String username, int userID, int typeID)
	{
		switch(userType.toUpperCase())
		{
			case "ADMIN": 
				Admin admin = new Admin(userType, username, userID);
				return admin;
			case "CLIENT": 
				if(username != null)
				{
					Client client = new Client(name, username, userID, typeID, false);
					return client;
				}
				else
				{
					Client client = new Client(name, username, userID, typeID, true);
					return client;
				}
			case "DOCTOR": 
				Doctor doctor = new Doctor(name, username, userID, typeID);
				return doctor;
			case "SECRETARY":
				Secretary secretary = new Secretary(name, username, userID, typeID);
				return secretary;
		}
		
		return null;
	}
}
