package models;

public class Admin extends Users
{
	public Admin(String userType, String username, int userID)
	{
		this.userType = userType;
		this.username = username;
		this.userID = userID;
		this.name = "Earon Tan";
	}
}
