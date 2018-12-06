package modules;

import java.time.LocalDate;

import javafx.scene.Node;
import models.Doctor;
import models.Models;
import models.Users;
import viewpanels.Panels;
import views.Hub;

public abstract class Modules 
{
	protected LocalDate date;
	protected Users user;
	protected Models model;
	protected Hub main;
	protected Panels panel;
	
	public void setModels(Models model)
    {
    	this.model = model;
    }
    
    public void setFilter(int filter)
    {
    	if(panel != null)
    	{
    		panel.setFilter(filter);
    	}
    }
    
    public void setDoctorFilter(Doctor doctorfilter)
    {
    	if(panel != null)
    	{
    		panel.setDoctorFilter(doctorfilter);
    	}
    }
    
    public void setUser(Users user)
    {
    	this.user = user;
    }
    
    public void setHub(Hub main)
    {
    	this.main = main;
    }
    
    public Users getUser() 
    {
    	return user;
    }
    
    public Object getPanelController(Node node) 
    {
        return node.getProperties().get("foo");
    }
    
    public abstract void setDate(LocalDate date);
    public abstract void setView(Node node);
    public abstract void update();
    public abstract void setUp();
}
