package viewpanels;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import models.Doctor;
import models.Models;
import models.ReservedSlot;
import models.Slots;
import models.TimeSlot;
import modules.Modules;

public class WeeklyAgendaView extends Panels implements Initializable
{
	/***********************************************
	 * Class Attributes:
	 */
	private LocalDate date;
	private Models model;
	private Modules module;
	private int filter;
	private LocalDate startOfWeek;
	private LocalDate endOfWeek;
	private Doctor doctorfilter;
	
	/***********************************************
	 * FXML Resources:
	 */
	@FXML private Button btnPrev;
    @FXML private Button btnNext;
    @FXML private Label lblWeek;
    @FXML private ListView<Slots> list;
    
    /***********************************************
	 * FXML Methods:
	 */
    @FXML
    public void nextWeek() 
    {
    	setDate(date.plusDays(7));
    }
    
    @FXML
    public void prevWeek() 
    {
    	setDate(date.minusDays(7));
    }
    
    /***********************************************
	 * Initialization:
	 */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert btnPrev != null : "fx:id=\"btnPrev\" was not injected: check your FXML file 'AgendaView.fxml'.";
        assert btnNext != null : "fx:id=\"btnNext\" was not injected: check your FXML file 'AgendaView.fxml'.";
        assert lblWeek != null : "fx:id=\"lblDate\" was not injected: check your FXML file 'AgendaView.fxml'.";
        assert list != null : "fx:id=\"list\" was not injected: check your FXML file 'AgendaView.fxml'.";
        
        list.setCellFactory(lv -> new ListCell<Slots>() {
            @Override
            protected void updateItem(Slots item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setTextFill(null);
                }
                else {
                	switch(module.getUser().getUserType().toUpperCase())
                	{
                		
                		case "DOCTOR":
                			TimeSlot slot = (TimeSlot) item;
                			if(filter == 1 && slot.isReserved()) 
                			{
                				setText("[" + item.getDate().getDayOfWeek() + "] [" + item.getDate() + "] [" + item.getTimeStart() + "-" + item.getTimeEnd() + "]");
                				setTextFill(Color.BLUE);
                        	}
                        	else if(filter == 2 && !slot.isReserved()) 
                        	{
                        		setText("[" + item.getDate().getDayOfWeek() + "] [" + item.getDate() + "] [" + item.getTimeStart() + "-" + item.getTimeEnd() + "]");
                				setTextFill(Color.GREEN);
                        	}
                            else if(filter == 0 || filter == 3) 
                            {
                            	if(slot.isReserved())
                            	{
                            		setText("[" + item.getDate().getDayOfWeek() + "] [" + item.getDate() + "] [" + item.getTimeStart() + "-" + item.getTimeEnd() + "]");
                    				setTextFill(Color.BLUE);
                            	}
                            	else
                            	{
                            		setText("[" + item.getDate().getDayOfWeek() + "] [" + item.getDate() + "] [" + item.getTimeStart() + "-" + item.getTimeEnd() + "]");
                    				setTextFill(Color.GREEN);
                            	}
                        	}
                			break;
                		case "CLIENT":
                			if(filter == 1 || filter == 0 || filter == 3)
                			{
                				System.out.println(item.getClass().toString().split("\\.")[1]);
                				if(item.getClass().toString().split("\\.")[1].toUpperCase().equals("TIMESLOT"))
                				{
                					setText("[" + item.getDate().getDayOfWeek() + "] [" + item.getDate() + "] [" + item.getTimeStart() + "-" + item.getTimeEnd() + "]");
                    				setTextFill(Color.GREEN);
                				}
                			}
                			
                			if(filter == 2 || filter == 0)
                			{
                				if(item.getClass().toString().split("\\.")[1].toUpperCase().equals("RESERVEDSLOT"))
                				{
                					setText("[" + item.getDate().getDayOfWeek() + "] [" + item.getDate() + "] [" + item.getTimeStart() + "-" + item.getTimeEnd() + "]"
                								+ " Doctor: " + ((ReservedSlot) item).getDoctor().getName());
                    				setTextFill(Color.BLUE);
                				}
                			}
                			break;
                		case "SECRETARY":
                			if(filter == 1 || filter == 0 || filter == 3)
                			{
                				System.out.println(item.getClass().toString().split("\\.")[1]);
                				if(item.getClass().toString().split("\\.")[1].toUpperCase().equals("TIMESLOT"))
                				{
                					setText("[" + item.getDate().getDayOfWeek() + "] [" + item.getDate() + "] [" + item.getTimeStart() + "-" + item.getTimeEnd() + "]");
                    				setTextFill(Color.GREEN);
                				}
                			}
                			
                			if(filter == 2 || filter == 0)
                			{
                				if(item.getClass().toString().split("\\.")[1].toUpperCase().equals("RESERVEDSLOT"))
                				{
                					setText("[" + item.getDate().getDayOfWeek() + "] [" + item.getDate() + "] [" + item.getTimeStart() + "-" + item.getTimeEnd() + "]"
                								+ " Doctor: " + ((ReservedSlot) item).getDoctor().getName() + " || Reserved by: " + ((ReservedSlot) item).getClient().getName());
                    				setTextFill(Color.BLUE);
                				}
                			}
                			break;
                	}
                }
            }
        });
    }
    
    /***********************************************
	 * Class Methods:
	 */
    public void setDate(LocalDate date)
    {
    	this.date = date;
    	module.setDate(date);
    	
    	switch(date.getDayOfWeek().getValue())
    	{
    		case 1:
    			startOfWeek = date;
    			endOfWeek = date.plusDays(6);
    			break;
    		case 2:
    			startOfWeek = date.minusDays(1);
    			endOfWeek = date.plusDays(5);
    			break;
    		case 3:
    			startOfWeek = date.minusDays(2);
    			endOfWeek = date.plusDays(4);
    			break;
    		case 4:
    			startOfWeek = date.minusDays(3);
    			endOfWeek = date.plusDays(3);
    			break;
    		case 5:
    			startOfWeek = date.minusDays(4);
    			endOfWeek = date.plusDays(2);
    			break;
    		case 6:
    			startOfWeek = date.minusDays(5);
    			endOfWeek = date.plusDays(1);
    			break;
    		case 7:
    			startOfWeek = date.minusDays(6);
    			endOfWeek = date;
    			break;
    	}
    	
    	lblWeek.setText(startOfWeek.toString() + " <=> " + endOfWeek.toString());
    	refresh();
    }
    
    public void setModels(Models model)
    {
    	this.model = model;
    }
    
    public void setModule(Modules module)
    {
    	this.module = module;
    }
    
    public void setFilter(int filter)
    {
    	this.filter = filter;
    	refresh();
    }
    
    public void refresh()
    {
    	list.getItems().clear();
    	
    	ArrayList<Slots> temp;
	    LocalDate start;
	    
    	switch(module.getUser().getUserType().toUpperCase())
    	{
	    	
    		case "DOCTOR":
    			start = startOfWeek;
    			while(start.isBefore(endOfWeek.plusDays(1)))
    			{
    				temp = model.getTimeSlots(start, (Doctor) module.getUser());
    	    	
    				list.getItems().addAll(temp);
    				
    				start = start.plusDays(1);
    			}
    	    	break;
    		case "CLIENT":
    			if(doctorfilter != null)
    			{
	    			start = startOfWeek;
	    			while(start.isBefore(endOfWeek.plusDays(1)))
	    			{
	    				temp = model.getTimeSlots(start, doctorfilter);
	    	    	
	    				for(int i = 0; i < temp.size(); i++)
	    				{
	    					if(!((TimeSlot) temp.get(i)).isReserved())
	    						list.getItems().add(temp.get(i));
	    				}
	    				
	    				start = start.plusDays(1);
	    			}
	    			start = startOfWeek;
	    			while(start.isBefore(endOfWeek.plusDays(1)))
	    			{
	    				temp = model.getReservedSlots(start, doctorfilter);
	    	    	
	    				for(int i = 0; i < temp.size(); i++)
	    				{
	    					list.getItems().add(temp.get(i));
	    				}
	    				
	    				start = start.plusDays(1);
	    			}
    			}
    			break;
    		case "SECRETARY":
    			if(doctorfilter != null)
    			{
	    			start = startOfWeek;
	    			while(start.isBefore(endOfWeek.plusDays(1)))
	    			{
	    				temp = model.getTimeSlots(start, doctorfilter);
	    	    	
	    				for(int i = 0; i < temp.size(); i++)
	    				{
	    					if(!((TimeSlot) temp.get(i)).isReserved())
	    						list.getItems().add(temp.get(i));
	    				}
	    				
	    				start = start.plusDays(1);
	    			}
	    			start = startOfWeek;
	    			while(start.isBefore(endOfWeek.plusDays(1)))
	    			{
	    				temp = model.getReservedSlots(start, doctorfilter);
	    	    	
	    				for(int i = 0; i < temp.size(); i++)
	    				{
	    					list.getItems().add(temp.get(i));
	    				}
	    				
	    				start = start.plusDays(1);
	    			}
    			}
    			break;
    	}
    }

	@Override
	public void setDoctorFilter(Doctor filter) 
	{
		this.doctorfilter = filter;
		refresh();
	}
}
