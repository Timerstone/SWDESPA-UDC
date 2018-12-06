package parsers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import models.Client;
import models.Doctor;
import models.ReservedSlot;
import models.TimeSlot;
import models.Users;

public class DatabaseParser extends DataParsers 
{
	private Connection connection;
	private PreparedStatement statement;
	
	/***********************************
	 * XXX: Database connection:
	 */
	private static Connection getConnection() throws Exception 
	{
		try{
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://127.0.0.2:3306/final?autoReconnect=true&useSSL=false";
			String username = "root";
			String password = "4654";
			Class.forName(driver);
				
			Connection connection = DriverManager.getConnection(url,username,password);
			return connection;
		} 
		catch(Exception e){
			e.printStackTrace();
		}	
		return null;
	}
	
	/***********************************
	 * XXX: Status Checkers
	 */
	public boolean checkLogin(int userID) 
	{
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT * FROM instances"
						+ " WHERE userID = ?");
			statement.setInt(1, userID);
			ResultSet result = statement.executeQuery();
			
			if(result.next())
			{
				connection.close();
				return true;
			}
			
			connection.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}

	public boolean checkUpdateTag(Users user) 
	{
		boolean status = false;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT updated FROM instances" 
						+ " WHERE userID = ?");
			statement.setInt(1, user.getUserID());
			ResultSet result = statement.executeQuery();
			
			if(result.next())
			{
				status = result.getBoolean("updated");
			}			
			
			connection.close();
			return status;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void setUpdated(Users user)
	{
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE instances SET updated = 1 WHERE userID = ?");
			statement.setInt(1, user.getUserID());
			statement.execute();
			
			connection.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void updateAll()
	{
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE instances SET updated = 1");
			statement.execute();
			connection.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/***********************************
	 * XXX: LogIn-LogOut
	 */
	public void LogIn(Users user)
	{
		int instanceID = 0;
		
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT instanceID FROM instances");
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				instanceID++;
			}
			
			instanceID++;
			
			statement = connection.prepareStatement("INSERT INTO instances"
						+ " (instanceID,userID,updated) VALUES (?,?,0);");
			statement.setInt(1, instanceID);
			statement.setInt(2, user.getUserID());
			statement.execute();
			
			connection.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void LogOut(Users user) 
	{
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM instances WHERE userID = ?");
			statement.setInt(1, user.getUserID());
			statement.execute();
			
			connection.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/***********************************
	 * XXX: User Getters
	 * @return string = "usertype,name,username,userid,(type)id"
	 */
	public String getUser(String username, String password) 
	{
		try
		{
			String type;
			connection = getConnection();
			statement = connection.prepareStatement("SELECT * FROM users" 
					 	+ " WHERE username = ? AND password = ?");
			statement.setString(1, username);
			statement.setString(2, password);
			ResultSet result = statement.executeQuery();
			
			if(result.next())
			{
				int userID = result.getInt("userID");
				String userType = result.getString("userType");
				
				if(userID == 0)
				{
					connection.close();
					return "admin,null,admin,0,0";
				}
				else
				{
					if(userType.equals("secretary"))
						type = "secretaries";
					else
						type = userType + "s";
					
					statement = connection.prepareStatement("SELECT * FROM " + type + " WHERE userID = ?");
					statement.setInt(1, userID);
					
					result = statement.executeQuery();
					if(result.next())
					{
						String Name = result.getString("name");
						int id = result.getInt("typeID");
						connection.close();
						return userType + "," + Name + "," + username +"," + userID + "," + id;
					}
				}
			}
			
			connection.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getUser(int userID)
	{
		String userType = null;
		String username = null;
		try
		{
			String type;
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT * FROM users WHERE userID = ?");
			statement.setInt(1, userID);
			
			ResultSet result = statement.executeQuery();
			if(result.next())
			{
				userType = result.getString("userType");
				username = result.getString("username");
				
				if(userType.equals("secretary"))
					type = "secretaries";
				else
					type = userType + "s";
				
				statement = connection.prepareStatement("SELECT * FROM " + type + " WHERE userID = ?");
				
				statement.setInt(1, userID);
				
				result = statement.executeQuery();
				if(result.next())
				{
					String Name = result.getString("name");
					int id = result.getInt("typeID");
					connection.close();
					return userType + "," + Name + "," + username +"," + userID + "," + id;
				}
			}
			
			connection.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getDoctor(int doctorID)
	{
		String Name = null;
		int userID = 0;
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT * FROM doctors WHERE typeID = ?");
			statement.setInt(1, doctorID);
			
			ResultSet result = statement.executeQuery();
			if(result.next())
			{
				Name = result.getString("name");
				userID = result.getInt("userID");
				
				statement = connection.prepareStatement("SELECT * FROM users WHERE userID = ?");               
				statement.setInt(1, userID);
				
				result = statement.executeQuery();
				if(result.next())
				{
					String userType = result.getString("userType");
					String username = result.getString("username");
					connection.close();
					return userType + "," + Name + "," + username +"," + userID + "," + doctorID;
				}
			}
			
			connection.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public String getClient(int clientID)
	{
		String Name = null;
		int userID = 0;
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT * FROM clients "
													+ "WHERE typeID = ?");
			statement.setInt(1, clientID);
			
			ResultSet result = statement.executeQuery();
			if(result.next())
			{
				Name = result.getString("name");
				userID = result.getInt("userID");
				
				statement = connection.prepareStatement("SELECT * FROM users" 
									                     + " WHERE userID = ?");
				statement.setInt(1, userID);
				
				result = statement.executeQuery();
				if(result.next())
				{
					String userType = result.getString("userType");
					String username = result.getString("username");
					connection.close();
					return userType + "," + Name + "," + username +"," + userID + "," + clientID;
				}
			}
			
			connection.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getSecretary(int secretaryID)
	{
		String Name = null;
		String userType = null;
		String username = null;
		int userID = 0;
		
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT * FROM secretaries "
													+ "WHERE typeID = ?");
			statement.setInt(1, secretaryID);
			
			ResultSet result = statement.executeQuery();
			if(result.next())
			{
				Name = result.getString("name");
				userID = result.getInt("userID");
				
				statement = connection.prepareStatement("SELECT * FROM users WHERE userID = ?");		                   
				statement.setInt(1, userID);
				
				result = statement.executeQuery();
				if(result.next())
				{
					userType = result.getString("userType");
					username = result.getString("username");
					connection.close();
					return userType + "," + Name + "," + username +"," + userID + "," + secretaryID;
				}
			}
			
			connection.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<String> getDoctors() 
	{
		ArrayList<String> list = new ArrayList<String>();
		String name = null;
		int doctorID = 0;
		int userID = 0;
		String username = null;
		
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT * FROM doctors d INNER JOIN users u on d.userID = u.userID");
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{			
				doctorID = result.getInt("typeID");
				name = result.getString("name");
				userID = result.getInt("userID");
				username = result.getString("username");
				
				list.add("doctor," + name + "," + username + "," + userID + "," + doctorID);
			}
			connection.close();
			return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<String> getTemporaryClients() 
	{
		ArrayList<String> list = new ArrayList<String>();
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT * FROM client WHERE isTemp = 1");
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{	
				int clientID = result.getInt("clientID");
				String name = result.getString("name");
				int userID = result.getInt("userID");
				
				list.add("client," + name + ",null," + userID + "," + clientID);
			}
			connection.close();
			return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	/***********************************
	 * XXX: TimeSlot Readers:
	 */
	public ArrayList<String> getTimeSlots(Doctor doctor) 
	{
		ArrayList<String> list = new ArrayList<String>();
		int slotID = 0;
		String timeStart = null;
		String timeEnd = null;
		String date = null;
		
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			statement = connection.prepareStatement("SELECT DISTINCT groupID FROM timeslots WHERE doctorID = ?");
			statement.setInt(1, doctor.getDoctorID());
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				statement = connection.prepareStatement("select * from timeslots where groupID = ?");
				statement.setInt(1, result.getInt("groupID"));
				ResultSet result2 = statement.executeQuery();
				slotID = 0;
				while(result2.next())
				{
					if(slotID == 0)
					{
						slotID = result2.getInt("slotID");
						timeStart = result2.getTime("timeStart").toString();
						timeEnd = result2.getTime("timeEnd").toString();
						date = result2.getDate("date").toString();
					}
					else if(slotID < result2.getInt("slotID"))
					{
						slotID = result2.getInt("slotID");
						timeEnd = result2.getTime("timeEnd").toString();
					}
					else
					{
						timeStart = result2.getTime("timeStart").toString();
					}
				}
				
				list.add("timeslot," + timeStart + "," + timeEnd + "," + date + "," + doctor.getDoctorID() + ",null,false");
			}
			
			connection.close();
			return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<String> getTimeSlots(LocalDate date)
	{
		ArrayList<String> list = new ArrayList<String>();
		int slotID = 0;
		String timeStart = null;
		String timeEnd = null;
		int doctorID = 0;
		
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			statement = connection.prepareStatement("SELECT DISTINCT groupID FROM timeslots WHERE date = ?");
			statement.setDate(1, Date.valueOf(date));
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				statement = connection.prepareStatement("select * from timeslots where groupID = ?");
				statement.setInt(1, result.getInt("groupID"));
				ResultSet result2 = statement.executeQuery();
				slotID = 0;
				
				while(result2.next())
				{
					if(slotID == 0)
					{
						doctorID = result2.getInt("doctorID");
						slotID = result2.getInt("slotID");
						timeStart = result2.getTime("timeStart").toString();
						timeEnd = result2.getTime("timeEnd").toString();
					}
					else if(slotID < result2.getInt("slotID"))
					{
						slotID = result2.getInt("slotID");
						timeEnd = result2.getTime("timeEnd").toString();
					}
					else
					{
						timeStart = result2.getTime("timeStart").toString();
					}
				}
				
				list.add("timeslot," + timeStart + "," + timeEnd + "," + date.toString() + "," + doctorID + ",null,false");
			}
			
			connection.close();
			return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<String> getTimeSlots(Doctor doctor, LocalDate date) 
	{
		ArrayList<String> list = new ArrayList<String>();
		int slotID = 0;
		String timeStart = null;
		String timeEnd = null;
		
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			statement = connection.prepareStatement("SELECT DISTINCT groupID FROM timeslots WHERE doctorID = ? AND date = ?");
			statement.setInt(1, doctor.getDoctorID());
			statement.setDate(2, Date.valueOf(date));
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				statement = connection.prepareStatement("select * from timeslots where groupID = ?");
				statement.setInt(1, result.getInt("groupID"));
				ResultSet result2 = statement.executeQuery();
				slotID = 0;
				while(result2.next())
				{
					if(slotID == 0)
					{
						slotID = result2.getInt("slotID");
						timeStart = result2.getTime("timeStart").toString();
						timeEnd = result2.getTime("timeEnd").toString();
					}
					else if(LocalTime.parse(timeEnd).isBefore(LocalTime.parse(result2.getTime("timeEnd").toString())))
					{
						slotID = result2.getInt("slotID");
						timeEnd = result2.getTime("timeEnd").toString();
					}
					else if(LocalTime.parse(timeStart).isAfter(LocalTime.parse(result2.getTime("timeStart").toString())))
					{
						timeStart = result2.getTime("timeStart").toString();
					}
				}
				
				list.add("timeslot," + timeStart + "," + timeEnd + "," + date.toString() + "," + doctor.getDoctorID() + ",null,false");
			}
			
			connection.close();
			return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	/***********************************
	 * XXX: ReservedSlot Readers:
	 */
	public ArrayList<String> getReservations(Client client) 
	{
		ArrayList<String> list = new ArrayList<String>();
		int timeslotID = 0;
		int doctorID = 0;
		String timeStart = null;
		String timeEnd = null;
		String date = null;
		
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT DISTINCT groupID FROM appointments WHERE clientID = ?");
			statement.setInt(1, client.getClientID());
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				statement = connection.prepareStatement("SELECT * FROM appointments WHERE groupID = ?");
				statement.setInt(1, result.getInt("groupID"));
				ResultSet result2 = statement.executeQuery();
				
				timeslotID = 0;
				while(result2.next())
				{
					timeslotID = result2.getInt("timeslot");
					statement = connection.prepareStatement("SELECT * FROM timeslots WHERE slotID = ?");
					statement.setInt(1, timeslotID);
					
					if(timeStart == null)
					{
						timeStart = result2.getTime("timeStart").toString();
						timeEnd = result2.getTime("timeEnd").toString();
						date = result2.getDate("date").toString();
						doctorID = result2.getInt("doctorID");
					}
					else if(LocalTime.parse(timeEnd).isBefore(LocalTime.parse(result2.getTime("timeEnd").toString())))
					{
						timeEnd = result2.getTime("timeEnd").toString();
					}
					else if(LocalTime.parse(timeStart).isAfter(LocalTime.parse(result2.getTime("timeStart").toString())))
					{
						timeStart = result2.getTime("timeStart").toString();
					}
				}
				
				list.add("reservedslot," + timeStart + "," + timeEnd + "," + date + "," + doctorID + "," + client.getClientID() + "," + false);
			}
			
			connection.close();
			return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<String> getReservations(Client client, LocalDate date) 
	{
		ArrayList<String> list = new ArrayList<String>();
		int timeslotID = 0;
		int doctorID = 0;
		String timeStart = null;
		String timeEnd = null;
		
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT DISTINCT groupID FROM appointments WHERE clientID = ?");
			statement.setInt(1, client.getClientID());
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				statement = connection.prepareStatement("SELECT * FROM appointments WHERE groupID = ?");
				statement.setInt(1, result.getInt("groupID"));
				ResultSet result2 = statement.executeQuery();
				
				timeslotID = 0;
				while(result2.next())
				{
					timeslotID = result2.getInt("timeslot");
					statement = connection.prepareStatement("SELECT * FROM timeslots WHERE slotID = ? AND date = ?");
					statement.setInt(1, timeslotID);
					statement.setDate(2, Date.valueOf(date));
					
					if(timeStart == null)
					{
						timeStart = result2.getTime("timeStart").toString();
						timeEnd = result2.getTime("timeEnd").toString();
						doctorID = result2.getInt("doctorID");
					}
					else if(LocalTime.parse(timeEnd).isBefore(LocalTime.parse(result2.getTime("timeEnd").toString())))
					{
						timeEnd = result2.getTime("timeEnd").toString();
					}
					else if(LocalTime.parse(timeStart).isAfter(LocalTime.parse(result2.getTime("timeStart").toString())))
					{
						timeStart = result2.getTime("timeStart").toString();
					}
				}
				
				list.add("reservedslot," + timeStart + "," + timeEnd + "," + date.toString() + "," + doctorID + "," + client.getClientID() + "," + false);
			}
			
			connection.close();
			return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<String> getReservations(Doctor doctor, LocalDate date)
	{
		ArrayList<String> list = new ArrayList<String>();
		int timeslotID = 0;
		int clientID = 0;
		String timeStart = null;
		String timeEnd = null;
		
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT DISTINCT a.groupID FROM appointments a INNER JOIN timeslots t ON a.timeslot = t.slotID AND t.doctorID = ? AND t.date = ?");
			statement.setInt(1, doctor.getDoctorID());
			statement.setDate(2, Date.valueOf(date));
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				statement = connection.prepareStatement("SELECT * FROM appointments WHERE groupID = ?");
				statement.setInt(1, result.getInt("groupID"));
				ResultSet result2 = statement.executeQuery();
				
				clientID = result2.getInt("clientID");
				
				timeslotID = 0;
				while(result2.next())
				{
					timeslotID = result2.getInt("timeslot");
					statement = connection.prepareStatement("SELECT * FROM timeslots WHERE slotID = ? AND date = ?");
					statement.setInt(1, timeslotID);
					statement.setDate(2, Date.valueOf(date));
					
					if(timeStart == null)
					{
						timeStart = result2.getTime("timeStart").toString();
						timeEnd = result2.getTime("timeEnd").toString();
					}
					else if(LocalTime.parse(timeEnd).isBefore(LocalTime.parse(result2.getTime("timeEnd").toString())))
					{
						timeEnd = result2.getTime("timeEnd").toString();
					}
					else if(LocalTime.parse(timeStart).isAfter(LocalTime.parse(result2.getTime("timeStart").toString())))
					{
						timeStart = result2.getTime("timeStart").toString();
					}
				}
				
				list.add("reservedslot," + timeStart + "," + timeEnd + "," + date.toString() + "," + doctor.getDoctorID() + "," + clientID + "," + false);
			}
			
			connection.close();
			return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return list;
	}
	
	/***********************************
	 * XXX: User Writer:
	 */
	public void saveTemporaryClient(Client client) 
	{
		try 
		{
			Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement("INSERT INTO client (typeID, name, isTemp, userID) " + "VALUES(?,?,?,?)");
			statement.setInt(1, client.getClientID());
			statement.setString(2, client.getName());
			statement.setBoolean(3, true);
			statement.setInt(4, client.getUserID());
			
			statement.executeUpdate();
			connection.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/***********************************
	 * XXX: TimeSlot Writer:
	 */
	public void saveTimeSlot(TimeSlot slot) 
	{
		/*
		 * - Receives a time slot object.
		 * - It is assumed that the time slot object has no conflicts.
		 * - Create a special ID for the time slot.
		 * - The time slot is divided into 30 minute interval entries.
		 * - Saves into the database.
		 */
		
		int appointment = 0;
		int ctr = 1;
		
		//TimeSlot ID creation
		try 
		{
			Connection connection = getConnection();
			PreparedStatement statement;
					
			statement = connection.prepareStatement("SELECT * FROM timeslots");
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				ctr++;
				
				if(result.getInt("groupID") != appointment)
				{
					 appointment = result.getInt("groupID");
				}
			}
			appointment++;
			
			String sql ="INSERT INTO timeslots(slotID, groupID, doctorID, date, timeStart, timeEnd)" + "VALUES(?,?,?,?,?,?)";
			
			String[] sTime = slot.getTimeStart().toString().split(":");
			String[] eTime = slot.getTimeEnd().toString().split(":");
			int SH = Integer.parseInt(sTime[0]);
			int SM = Integer.parseInt(sTime[1]);
			int EH = Integer.parseInt(eTime[0]);
			int EM = Integer.parseInt(eTime[1]);
			
			statement = connection.prepareStatement(sql);
			do
			{
				statement.setString(1, String.valueOf(ctr));
				statement.setString(2, String.valueOf(appointment));
				statement.setString(3, String.valueOf(slot.getDoctor().getDoctorID()));
				statement.setString(4, slot.getDate().toString());
				if(SM == 30)
				{
					statement.setString(5, SH + ":" + SM + ":00");
					//statement.addBatch();
					SH = SH + 1;
					SM = 00;
					statement.setString(6, SH + ":0" + SM + ":00");
				}
				else if(SM != 30)
				{
					statement.setString(5, SH + ":0" + SM + ":00");
					//statement.addBatch();
					SM = 30;
					statement.setString(6, SH + ":" + SM + ":00");
				}
				statement.addBatch();
				ctr++;
				
			}while(SH < EH || SM < EM);
			
			statement.executeBatch();	
			connection.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/***********************************
	 * XXX: ReservedSlot Writer:
	 */
	public void saveReservation(ReservedSlot slot) 
	{
		
		int appointment = -1;
		ArrayList<Integer> slots = new ArrayList<Integer>();
		int ctr=1;
		
		//ReservedSlotID creation
		try 
		{
			Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM appointments");
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				ctr = ctr+1;
			}
			appointment = appointment +1;
			
			statement = connection.prepareStatement("SELECT * FROM timeslots");
			ResultSet subResult = statement.executeQuery();
			boolean tof = false;
			while(subResult.next())
			{
				if(subResult.getString("timeEnd").equals(slot.getTimeEnd().toString() + ":00") && subResult.getString("timeStart").equals(slot.getTimeStart().toString() + ":00") && subResult.getInt("doctorID") == slot.getDoctor().getDoctorID() && subResult.getString("date").equals(slot.getDate().toString())){
					System.out.println("IN");
					slots.add(subResult.getInt("slotID"));
					break;
				}
				else if(subResult.getString("timeStart").equals(slot.getTimeStart().toString() + ":00") && subResult.getInt("doctorID") == slot.getDoctor().getDoctorID() && subResult.getString("date").equals(slot.getDate().toString()))
				{
					slots.add(subResult.getInt("slotID"));
					tof = true;
				}
				else if(!subResult.getString("timeEnd").equals(slot.getTimeEnd().toString() + ":00") && tof){
					slots.add(subResult.getInt("slotID"));
				}
				else if(subResult.getString("timeEnd").equals(slot.getTimeEnd().toString() + ":00") && tof){
					slots.add(subResult.getInt("slotID"));
					tof = false;
					break;
				}
				
			}
			
			String sql ="INSERT INTO appointments(slotID, doctorID, clientID, timeslot)" + "VALUES(?,?,?,?)";
			
			statement = connection.prepareStatement(sql);
			for(int i = 0; i<slots.size(); i++)
			{
				statement.setString(1, String.valueOf(ctr));
				statement.setString(2, String.valueOf(slot.getDoctor().getDoctorID()));
				statement.setInt(3, slot.getClient().getClientID());
				statement.setString(4, String.valueOf(slots.get(i)));
				statement.addBatch();
				ctr++;
			}
			statement.executeBatch();
			connection.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/***********************************
	 * XXX: Modify TimeSlot:
	 */
	public void modifyTimeSlot(TimeSlot slot, LocalTime newTimeStart, LocalTime newTimeEnd) 
	{
		/* 
		 * - Receives a time slot object with a new time start and a new time end.
		 * - It is assumed that the time slot object has no conflicts.
		 * - Searches the database of that specific time slot.
		 * - Modify the times.
		 * - The time slot is divided into 30 minute interval entries.
		 * - Saves into the database.
		 */
		
		int App_ID=-1;
		String Date = slot.getDate().toString();
		String OStime = slot.getTimeStart().toString() + ":00";
		String OEtime = slot.getTimeEnd().toString() + ":00";
		String NStime = newTimeStart.toString() + ":00";
		String NEtime = newTimeEnd.toString() + ":00";
//		String[] AOStime = OStime.split(":");
//		String[] AOEtime = OEtime.split(":");
//		String[] ANStime = NStime.split(":");
//		String[] ANEtime = NEtime.split(":");
		
		
		boolean changeStart = false;
		boolean changeEnd = false;
		boolean changeStartDel = false;
		boolean changeEndDel = false;
		
		
		try{
			Connection conn = getConnection();
			String sql = "SELECT * FROM timeslots";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			
			if(slot.getTimeStart().isAfter(newTimeStart)){
				changeStart = true;
				changeStartDel = false;
				System.out.println("Add S");
			}
			else if(slot.getTimeStart().isBefore(newTimeStart)){
				changeStart = false;
				changeStartDel = true;
				System.out.println("Del S");
			}
			else{
				changeStart = false;
				changeStartDel = false;
				
			}
			
			if(slot.getTimeEnd().isAfter(newTimeEnd)){
				changeEnd = false;
				changeEndDel = true;
				System.out.println("Del E");
			}
			else if(slot.getTimeEnd().isBefore(newTimeEnd)){
				changeEnd = true;
				changeEndDel = false;
				System.out.println("Add E");
			}
			else{
				changeEnd = false;
				changeEndDel = false;
				
			}
			
			while(result.next()){
				if(result.getString("timeStart").equals(OStime)){
					App_ID = result.getInt("groupID");
				}
			}
			System.out.println(App_ID);
			
			
			if(changeStart && changeEnd){
				//add Both Sides huhuhu
				System.out.println("Change Both");
				addModifiedSlots(NStime, OStime, App_ID, slot.getDoctor().getDoctorID(), Date);
				addModifiedSlots(OEtime, NEtime, App_ID, slot.getDoctor().getDoctorID(), Date);
			}
			else if(changeStart && !changeEnd){
				System.out.println("Extend Start");
				addModifiedSlots(NStime, OStime, App_ID, slot.getDoctor().getDoctorID(), Date);
			}
			else if(!changeStart && changeEnd){
				//Add at End
				System.out.println("Extend End");
				addModifiedSlots(OEtime, NEtime, App_ID, slot.getDoctor().getDoctorID(), Date);
				
			}
			
			if(changeStartDel && !changeEndDel){
				System.out.println("Shorten Start");
				System.out.println(OStime + " " + NStime);
				deleteModifiedSlots(OStime, NStime, App_ID, slot.getDoctor().getDoctorID(), Date);
			}
			else if(!changeStartDel && changeEndDel){
				System.out.println("Shorten End");
				deleteModifiedSlots(NEtime, OEtime, App_ID, slot.getDoctor().getDoctorID(), Date);
			}
			else if(changeStartDel && changeEndDel){
				System.out.println("Shorten Both");
				deleteModifiedSlots(OStime, NStime, App_ID, slot.getDoctor().getDoctorID(), Date);
				deleteModifiedSlots(NEtime, OEtime, App_ID, slot.getDoctor().getDoctorID(), Date);
			}
			
			if(!changeStart && !changeEnd && !changeStartDel && !changeEndDel){
				System.out.println("Change Nothing");
			}
			conn.close();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private void addModifiedSlots(String timeStart, String timeEnd, int groupID, int doctorID, String date){
		try {
			int ctr=1;
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT slotID FROM timeslots");
			ResultSet results = statement.executeQuery();
			while(results.next()){
				if(ctr < results.getInt("slotID"))
					ctr = results.getInt("slotID");
			}
			ctr++;
			
			String sql ="INSERT INTO timeslots(slotID, groupID, doctorID, date, timeStart, timeEnd)" + "VALUES(?,?,?,?,?,?)";
			
			String[] sTime = timeStart.toString().split(":");
			String[] eTime = timeEnd.toString().split(":");
			int SH = Integer.parseInt(sTime[0]);
			int SM = Integer.parseInt(sTime[1]);
			int EH = Integer.parseInt(eTime[0]);
			int EM = Integer.parseInt(eTime[1]);
			
			statement = conn.prepareStatement(sql);
			do
			{
				statement.setString(1, String.valueOf(ctr));
				statement.setString(2, String.valueOf(groupID));
				statement.setString(3, String.valueOf(doctorID));
				statement.setString(4, date);
				if(SM == 30)
				{
					statement.setString(5, SH + ":" + SM + ":00");
					//statement.addBatch();
					SH = SH + 1;
					SM = 00;
					statement.setString(6, SH + ":0" + SM + ":00");
				}
				else if(SM != 30)
				{
					statement.setString(5, SH + ":0" + SM + ":00");
					//statement.addBatch();
					SM = 30;
					statement.setString(6, SH + ":" + SM + ":00");
				}
				statement.addBatch();
				ctr++;
				
			}while(SH < EH || SM < EM);
			
			statement.executeBatch();	
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void deleteModifiedSlots(String timeStart, String timeEnd, int groupID, int doctorID, String date){
		
		System.out.println(timeStart + " " + timeEnd);
		ArrayList<Integer> delete = new ArrayList<Integer>();
		try {
			boolean trace = false;
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM timeslots");
			ResultSet results = statement.executeQuery();
			while(results.next()){
				if((results.getString("timeEnd").equals(timeEnd)) && (results.getString("timeStart").equals(timeStart)) && !trace){
					delete.add(results.getInt("slotID"));
					trace = false;
					break;
				}
				else if((results.getString("timeStart").equals(timeStart)) && !trace){
					trace = true;
					delete.add(results.getInt("slotID"));
				}
				else if(!results.getString("timeEnd").equals(timeEnd) && trace){
					delete.add(results.getInt("slotID"));
				}
				else if(results.getString("timeEnd").equals(timeEnd) && trace){
					delete.add(results.getInt("slotID"));
					trace = false;
					break;
				}	
			}
			
			String sql = "DELETE FROM timeslots WHERE slotID = ?";
			statement = conn.prepareStatement(sql);
			
			
			for(int i = 0; i<delete.size(); i++)
			{
				statement.setInt(1, delete.get(i));
				statement.addBatch();
			}
			System.out.println(delete);
			statement.executeBatch();	
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void modifyTimeSlot(TimeSlot slot, LocalDate newDate) 
	{
		/* 
		 * - Receives a time slot object with a new date.
		 * - It is assumed that the time slot object has no conflicts.
		 * - Searches the database of that specific time slot.
		 * - Simply change the date of the time slot entries.
		 */
		int appID = -1;
		
		try {
			Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT groupID FROM timeslots WHERE date = ? AND timeStart =?");
			statement.setString(1, slot.getDate().toString());
			statement.setString(2, slot.getTimeStart().toString());
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				appID = result.getInt("groupID");
			}
			
			statement = connection.prepareStatement("UPDATE timeslots SET date = ? WHERE groupID = ?");
			statement.setString(1, newDate.toString());
			statement.setInt(2, appID);
			statement.execute();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void modifyTimeSlot(TimeSlot slot, LocalDate newDate, LocalTime newTimeStart, LocalTime newTimeEnd) {
		/*
		 * - Receives a time slot object with a new time start, a new time end and a new date.
		 * - It is assumed that the time slot object has no conflicts.
		 * - Searches the database of that specific time slot.
		 * - Modify the times.
		 * - Change the date.
		 * - Split the times if necessary (create new ID and change the old IDs and and save new entries).
		 * - Unaffected entries will retain the old ID.
		 * - The time slot is divided into 30 minute interval entries.
		 * - Saves into the database.
		 */
		
		this.modifyTimeSlot(slot, newDate);
		slot.setDate(newDate);
		this.modifyTimeSlot(slot, newTimeStart, newTimeEnd);
	}

	/***********************************
	 * XXX: Remove TimeSlot:
	 */
	public void removeTimeSlot(TimeSlot slot) 
	{
		/* 
		 * - Receives a time slot object.
		 * - Searches the database of that specific time slot object.
		 * - Removes the entries related to that time slot object.
		 */
		
		ArrayList<Integer> slots = new ArrayList<Integer>();
		ArrayList<Integer> change = new ArrayList<Integer>();
		int LappID = -1;
		int appslot = 0;
		
		try {
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM timeslots");
			ResultSet result = statement.executeQuery();
			
			
			boolean tof = false;
			
			while(result.next())
			{
				if(!tof && result.getString("timeStart").equals(slot.getTimeStart().toString() + ":00") 
						&& result.getInt("doctorID") == slot.getDoctor().getDoctorID() 
						&& result.getString("date").equals(slot.getDate().toString()))
				{
					appslot = result.getInt("groupID");
					slots.add(result.getInt("slotID"));
					tof = true;
				}
				else if(tof && !result.getString("timeEnd").equals(slot.getTimeEnd().toString() + ":00") 
						&& result.getInt("groupID") == appslot)
				{
					slots.add(result.getInt("slotID"));
				}
				else if(tof && result.getString("timeEnd").equals(slot.getTimeEnd().toString() + ":00") 
						&& result.getInt("groupID") == appslot)
				{
					slots.add(result.getInt("slotID"));
					tof = false;
				}
				else if(result.getInt("groupID") == appslot){
					change.add(result.getInt("slotID"));
				}
			}
			conn.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		try
		{
			Connection conn = getConnection();
			String sql = "DELETE FROM timeslots WHERE slotID = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			
			
			for(int i = 0; i<slots.size(); i++)
			{
				statement.setInt(1, slots.get(i));
				statement.addBatch();
			}
			
			statement.executeBatch();
			conn.close();
		}  
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		//Finds last appID for segmented timeslots
		try {
			Connection conn = getConnection();
			String sql = "SELECT * FROM timeslots";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet results = statement.executeQuery();
			
			while(results.next()){
				if(LappID == -1){
					LappID = results.getInt("groupID");
				}
				else if(LappID != results.getInt("groupID")){
					LappID = results.getInt("groupID");
				}
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}


		try {
			Connection conn = getConnection();
			String sql = "UPDATE timeslots SET groupID = ? WHERE slotID = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			LappID = LappID + 1;
			
			for(int i =0; i<change.size(); i++){
				statement.setInt(1, LappID);
				statement.setInt(2, change.get(i));
				statement.addBatch();
			}
			statement.executeBatch();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/***********************************
	 * XXX: Remove ReservedSlot:
	 */
	public void removeReservedSlot(ReservedSlot slot) 
	{
		/* 
		 * - Receives a reserved slot object.
		 * - Searches the database of that specific reserved slot object.
		 * - Removes the entries related to that reserved slot object.
		 */
		
		ArrayList<Integer> slots = new ArrayList<Integer>();
		
		try 
		{
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM appointments a INNER JOIN timeslots t ON t.doctorID = a.doctorID AND a.timeslot = t.slotID");
			ResultSet result = statement.executeQuery();
			
			boolean tof = false;
			System.out.println(slot.getTimeStart().toString());
			while(result.next())
			{
				if(!tof && result.getString("timeStart").equals(slot.getTimeStart().toString() + ":00") 
						&& result.getString("date").equals(slot.getDate().toString()) && result.getString("timeEnd").equals(slot.getTimeEnd().toString() + ":00"))
				{
					slots.add(result.getInt("slotID"));
					break;
				}
				else if(!tof && result.getString("timeStart").equals(slot.getTimeStart().toString() + ":00") 
					&& result.getString("date").equals(slot.getDate().toString()))
				{
					System.out.println("IN");
					slots.add(result.getInt("slotID"));
					tof = true;
				}
				else if(tof && !result.getString("timeEnd").equals(slot.getTimeEnd().toString()))
				{
					slots.add(result.getInt("slotID"));
				}
				else if(tof && result.getString("timeEnd").equals(slot.getTimeEnd().toString()))
				{
					break;
				}
			}
			System.out.println(slots);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		//Deletes
		try
		{
			Connection conn = getConnection();
			String sql = "DELETE FROM appointments WHERE timeslot = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			
			for(int i = 0; i<slots.size(); i++)
			{
				statement.setInt(1, slots.get(i));
				statement.addBatch();
			}
			statement.executeBatch();
		}  
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
