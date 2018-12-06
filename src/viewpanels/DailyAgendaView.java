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

public class DailyAgendaView extends Panels implements Initializable
{
	/***********************************************
	 * Class Attributes:
	 */
	private LocalDate date;
	private Models model;
	private Modules module;
	private int filter;
	private Doctor doctorfilter;
	
	/***********************************************
	 * FXML Resources:
	 */
	@FXML private Button btnPrev;
    @FXML private Button btnNext;
    @FXML private Label lblDate;
    @FXML private ListView<Slots> list;
    
    /***********************************************
	 * FXML Methods:
	 */
    @FXML
    public void nextDay() 
    {
    	date = date.plusDays(1);
    	module.setDate(date);
    	lblDate.setText("" + date);
    	refresh();
    }
    
    @FXML
    public void prevDay() 
    {
    	date = date.minusDays(1); 
    	module.setDate(date);
    	lblDate.setText("" + date.toString());
    	refresh();
    }
    
    /***********************************************
	 * Initialization:
	 */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert btnPrev != null : "fx:id=\"btnPrev\" was not injected: check your FXML file 'AgendaView.fxml'.";
        assert btnNext != null : "fx:id=\"btnNext\" was not injected: check your FXML file 'AgendaView.fxml'.";
        assert lblDate != null : "fx:id=\"lblDate\" was not injected: check your FXML file 'AgendaView.fxml'.";
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
                				setText("[" + item.getTimeStart() + "-" + item.getTimeEnd() + "]");
                				setTextFill(Color.BLUE);
                        	}
                        	else if(filter == 2 && !slot.isReserved()) 
                        	{
                        		setText("[" + item.getTimeStart() + "-" + item.getTimeEnd() + "]");
                				setTextFill(Color.GREEN);
                        	}
                            else if(filter == 0) 
                            {
                            	if(slot.isReserved())
                            	{
                            		setText("[" + item.getTimeStart() + "-" + item.getTimeEnd() + "]");
                    				setTextFill(Color.BLUE);
                            	}
                            	else
                            	{
                            		setText("[" + item.getTimeStart() + "-" + item.getTimeEnd() + "]");
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
                					setText("[" + item.getDate() + "] [" + item.getTimeStart() + "-" + item.getTimeEnd() + "]");
                    				setTextFill(Color.GREEN);
                				}
                			}
                			
                			if(filter == 2 || filter == 0)
                			{
                				if(item.getClass().toString().split("\\.")[1].toUpperCase().equals("RESERVEDSLOT"))
                				{
                					setText("[" + item.getDate() + "] [" + item.getTimeStart() + "-" + item.getTimeEnd() + "]"
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
                					setText("[" + item.getDate() + "] [" + item.getTimeStart() + "-" + item.getTimeEnd() + "]");
                    				setTextFill(Color.GREEN);
                				}
                			}
                			
                			if(filter == 2 || filter == 0)
                			{
                				if(item.getClass().toString().split("\\.")[1].toUpperCase().equals("RESERVEDSLOT"))
                				{
                					setText("[" + item.getDate() + "] [" + item.getTimeStart() + "-" + item.getTimeEnd() + "]"
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
    	lblDate.setText(date.toString());
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
    	
    	switch(module.getUser().getUserType().toUpperCase())
    	{
    		case "DOCTOR":
    			
    	    	temp = model.getTimeSlots(date, (Doctor) module.getUser());
    	    	
    	    	list.getItems().addAll(temp);
    	    	break;
    		case "CLIENT":
    			if(doctorfilter != null)
    			{
	    			temp = model.getTimeSlots(date, doctorfilter);
	    	    	
    				for(int i = 0; i < temp.size(); i++)
    				{
    					if(!((TimeSlot) temp.get(i)).isReserved())
    						list.getItems().add(temp.get(i));
    				}
    				
    				temp = model.getReservedSlots(date, doctorfilter);
    	    	
    				for(int i = 0; i < temp.size(); i++)
    				{
    					list.getItems().add(temp.get(i));
    				}
    			}
    			break;
    		case "SECRETARY":
    			if(doctorfilter != null)
    			{
	    			temp = model.getTimeSlots(date, doctorfilter);
	    	    	
    				for(int i = 0; i < temp.size(); i++)
    				{
    					if(!((TimeSlot) temp.get(i)).isReserved())
    						list.getItems().add(temp.get(i));
    				}
    				
    				temp = model.getReservedSlots(date, doctorfilter);
    	    	
    				for(int i = 0; i < temp.size(); i++)
    				{
    					list.getItems().add(temp.get(i));
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
