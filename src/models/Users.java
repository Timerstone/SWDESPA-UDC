package models;

public class Users 
{
	protected int userID;
	protected String name;
	protected String username;
	protected String userType;
	
	public void setUserID(int userID)
	{
		this.userID = userID;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setUserName(String username)
	{
		this.username = username;
	}
	
	public void setUserType(String userType)
	{
		this.userType = userType;
	}
	
	public int getUserID()
	{
		return userID;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getUserName()
	{
		return username;
	}
	
	public String getUserType()
	{
		return userType;
	}
	
	public boolean isEqual(Users user)
	{
		if(user.name.equals(this.name) && user.username.equals(this.username) && user.userID == this.userID)
			return true;
		
		return false;
	}
}
