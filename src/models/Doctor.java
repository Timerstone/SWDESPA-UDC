package models;

public class Doctor extends Users
{
	private int doctorID;
	
	public Doctor(String name, String username, int userID, int doctorID)
	{
		this.userID = userID;
		this.doctorID = doctorID;
		this.name = name;
		this.username = username;
		this.userType = getClass().toString().split("\\.")[1];
	}
	
	public void setDoctorID(int doctorID)
	{
		this.doctorID = doctorID;
	}
	
	public int getDoctorID()
	{
		return doctorID;
	}
}
