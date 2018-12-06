package viewpanels;

import java.net.URL;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import models.Doctor;
import models.Models;
import models.ReservedSlot;
import models.Slots;
import models.TimeSlot;
import modules.Modules;

public class DailyView extends Panels implements Initializable{
	/***********************************************
	 * Class Attributes:
	 */
	private LocalDate date;
	private ArrayList<Label> labelList; 
	private Models model;
	private Modules module;
	private int filter;
	private Doctor doctorfilter;
	
	/***********************************************
	 * FXML Resources:
	 */
	@FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private GridPane gpnList;
    @FXML private Button btnPrev;
    @FXML private Button btnNext;
    @FXML private Label lblDate;
    @FXML private GridPane gpnBoard;
    
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
        assert gpnList != null : "fx:id=\"gpnList\" was not injected: check your FXML file 'DailyView.fxml'.";
        assert btnPrev != null : "fx:id=\"btnPrev\" was not injected: check your FXML file 'DailyView.fxml'.";
        assert btnNext != null : "fx:id=\"btnNext\" was not injected: check your FXML file 'DailyView.fxml'.";
        assert lblDate != null : "fx:id=\"lblDate\" was not injected: check your FXML file 'DailyView.fxml'.";
        
        lblDate.setText("");
       
        labelList = new ArrayList<Label>();
       
        for(int i = 0; i < 48; i++)
        {
        	labelList.add(new Label());
        }
        
        for(int i = 0; i < 48; i++) {
        	Node node = labelList.get(i);
        	gpnBoard.add(node, 0, i);
        	labelList.get(i).setPrefSize(550, 23);
        	labelList.get(i).setAlignment(Pos.BOTTOM_CENTER);
        	labelList.get(i).setText("");
        	labelList.get(i).setStyle("-fx-background-color: #ffffff;");
        	labelList.get(i).setTextAlignment(TextAlignment.CENTER);
        }
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
    
    public void setDoctorFilter(Doctor filter)
    {
    	this.doctorfilter = filter;
    	refresh();
    }
    
    public void refresh()
    {
    	ArrayList<Slots> temp = new ArrayList<Slots>();
    	int hour;
    	int min;
    	int start = 0;
    	int end = 0;
    	boolean load;
    	
    	clearLabels();
    	
    	switch(module.getUser().getUserType().toUpperCase())
    	{
    	case "DOCTOR":
			temp = model.getTimeSlots(date, (Doctor) module.getUser());
			
			for(int i = 0; i < temp.size(); i++)
			{
    			if(temp.get(i).getDate().equals(date))
    			{
    				TimeSlot slot = (TimeSlot) temp.get(i);
    				
    				min = slot.getTimeStart().getMinute();
    				hour = slot.getTimeStart().getHour();
    				
    				if(hour == 0 && min == 30)
        				start = hour + 1;
        			else if(hour == 0)
        				start = hour;
        			else if(min == 0)
        				start = hour * 2;
        			else if(min == 30)
        				start = hour * 2 + 1;
    				
    				
    				min = slot.getTimeEnd().getMinute();
        			hour = slot.getTimeEnd().getHour();
        			
        			if(hour == 0 && min == 30)
        				end = hour + 1;
        			else if(hour == 0)
        				end = hour;
        			else if(min == 0)
        				end = hour * 2;
        			else if(min == 30)
        				end = hour * 2 + 1;
        			
        			for(int j = start; j < end; j++)
        			{
        				if(j == start)
	    					if(filter == 1 && slot.isReserved()) {
	    						labelList.get(j).setText(slot.getTimeStart().toString() + " - " + slot.getTimeEnd().toString());
	                    	}
	                    	else if(filter == 2 && !slot.isReserved()) {
	                    		labelList.get(j).setText(slot.getTimeStart().toString() + " - " + slot.getTimeEnd().toString());
	                    	}
	                        else if(filter == 0) {
	                            labelList.get(j).setText(slot.getTimeStart().toString() + " - " + slot.getTimeEnd().toString());
	                    	}
        				
        				load = false;
        					
        				if(filter == 1 && slot.isReserved()) {
    						load = true;
                    	}
                    	else if(filter == 2 && !slot.isReserved()) {
                    		load = true;
                    	}
                        else if(filter == 0) {
                            load = true;
                    	}
        				
        				if(load)
        				{
        					if(slot.isReserved())
        					{
        						labelList.get(j).setStyle("-fx-background-color: #00ffff;"); //blue = res
        					}
        					else
        					{
        						labelList.get(j).setStyle("-fx-background-color: #2ecc71;"); //green = appt
        					}
        				}
        			}
    			}
			}
			break;
		case "CLIENT":
			if(doctorfilter != null)
			{
				temp = model.getTimeSlots(date, doctorfilter);
				
				for(int i = 0; i < temp.size(); i++)
				{
	    			if(temp.get(i).getDate().equals(date))
	    			{
	    				TimeSlot slot = (TimeSlot) temp.get(i);
	    				
	    				min = slot.getTimeStart().getMinute();
	    				hour = slot.getTimeStart().getHour();
	    				
	    				if(hour == 0 && min == 30)
	        				start = hour + 1;
	        			else if(hour == 0)
	        				start = hour;
	        			else if(min == 0)
	        				start = hour * 2;
	        			else if(min == 30)
	        				start = hour * 2 + 1;
	    				
	    				
	    				min = slot.getTimeEnd().getMinute();
	        			hour = slot.getTimeEnd().getHour();
	        			
	        			if(hour == 0 && min == 30)
	        				end = hour + 1;
	        			else if(hour == 0)
	        				end = hour;
	        			else if(min == 0)
	        				end = hour * 2;
	        			else if(min == 30)
	        				end = hour * 2 + 1;
	        			
	        			for(int j = start; j < end; j++)
	        			{
	        				if(j == start)
		    					if(filter == 1 && slot.isReserved()) {
		    						labelList.get(j).setText(slot.getTimeStart().toString() + " - " + slot.getTimeEnd().toString());
		                    	}
		                    	else if(filter == 2 && !slot.isReserved()) {
		                    		labelList.get(j).setText(slot.getTimeStart().toString() + " - " + slot.getTimeEnd().toString());
		                    	}
		                        else if(filter == 0) {
		                            labelList.get(j).setText(slot.getTimeStart().toString() + " - " + slot.getTimeEnd().toString());
		                    	}
	        				
	        				load = false;
	        					
	        				if(filter == 1 && slot.isReserved()) {
	    						load = true;
	                    	}
	                    	else if(filter == 2 && !slot.isReserved()) {
	                    		load = true;
	                    	}
	                        else if(filter == 0) {
	                            load = true;
	                    	}
	                        else if(filter == 3) {
	                        	load = false;
	                        }
	        				
	        				if(load)
	        				{
	        					if(slot.isReserved())
	        					{
	        						labelList.get(j).setStyle("-fx-background-color: #00ffff;"); //blue = res
	        					}
	        					else
	        					{
	        						labelList.get(j).setStyle("-fx-background-color: #2ecc71;"); //green = appt
	        					}
	        				}
	        			}
	    			}
				}
			
				if(filter == 1 || filter == 0)
				{
					temp = model.getReservedSlots(date, doctorfilter);
					
					for(int i = 0; i < temp.size(); i++)
					{
	    				ReservedSlot slot = (ReservedSlot) temp.get(i);
	    				System.out.println(slot.getClient().getName() + " " + slot.getTimeStart() + " - " + slot.getTimeEnd());
	    				
	    				min = slot.getTimeStart().getMinute();
	    				hour = slot.getTimeStart().getHour();
	    				
	    				if(hour == 0 && min == 30)
	        				start = hour + 1;
	        			else if(hour == 0)
	        				start = hour;
	        			else if(min == 0)
	        				start = hour * 2;
	        			else if(min == 30)
	        				start = hour * 2 + 1;
	    				
	    				
	    				min = slot.getTimeEnd().getMinute();
	        			hour = slot.getTimeEnd().getHour();
	        			
	        			if(hour == 0 && min == 30)
	        				end = hour + 1;
	        			else if(hour == 0)
	        				end = hour;
	        			else if(min == 0)
	        				end = hour * 2;
	        			else if(min == 30)
	        				end = hour * 2 + 1;
	        			
	        			for(int j = start; j < end; j++)
	        			{
	        				String label = "Doctor: " + slot.getDoctor().getName();
	        				if(j == start)
	        					labelList.get(j).setText(label);
	        				
	        				labelList.get(j).setStyle("-fx-background-color: #00ffff;"); //blue = res
	        			}
					}
				}
			}
			
			break;
		case "SECRETARY":
			if(doctorfilter != null)
			{
				temp = model.getTimeSlots(date, doctorfilter);
				
				for(int i = 0; i < temp.size(); i++)
				{
	    			if(temp.get(i).getDate().equals(date))
	    			{
	    				TimeSlot slot = (TimeSlot) temp.get(i);
	    				
	    				min = slot.getTimeStart().getMinute();
	    				hour = slot.getTimeStart().getHour();
	    				
	    				if(hour == 0 && min == 30)
	        				start = hour + 1;
	        			else if(hour == 0)
	        				start = hour;
	        			else if(min == 0)
	        				start = hour * 2;
	        			else if(min == 30)
	        				start = hour * 2 + 1;
	    				
	    				
	    				min = slot.getTimeEnd().getMinute();
	        			hour = slot.getTimeEnd().getHour();
	        			
	        			if(hour == 0 && min == 30)
	        				end = hour + 1;
	        			else if(hour == 0)
	        				end = hour;
	        			else if(min == 0)
	        				end = hour * 2;
	        			else if(min == 30)
	        				end = hour * 2 + 1;
	        			
	        			for(int j = start; j < end; j++)
	        			{
	        				if(j == start)
		    					if(filter == 1 && slot.isReserved()) {
		    						labelList.get(j).setText(slot.getTimeStart().toString() + " - " + slot.getTimeEnd().toString());
		                    	}
		                    	else if(filter == 2 && !slot.isReserved()) {
		                    		labelList.get(j).setText(slot.getTimeStart().toString() + " - " + slot.getTimeEnd().toString());
		                    	}
		                        else if(filter == 0) {
		                            labelList.get(j).setText(slot.getTimeStart().toString() + " - " + slot.getTimeEnd().toString());
		                    	}
	        				
	        				load = false;
	        					
	        				if(filter == 1 && slot.isReserved()) {
	    						load = true;
	                    	}
	                    	else if(filter == 2 && !slot.isReserved()) {
	                    		load = true;
	                    	}
	                        else if(filter == 0) {
	                            load = true;
	                    	}
	                        else if(filter == 3) {
	                        	load = false;
	                        }
	        				
	        				if(load)
	        				{
	        					if(slot.isReserved())
	        					{
	        						labelList.get(j).setStyle("-fx-background-color: #00ffff;"); //blue = res
	        					}
	        					else
	        					{
	        						labelList.get(j).setStyle("-fx-background-color: #2ecc71;"); //green = appt
	        					}
	        				}
	        			}
	    			}
				}
			
				if(filter == 1 || filter == 0)
				{
					temp = model.getReservedSlots(date, doctorfilter);
					
					for(int i = 0; i < temp.size(); i++)
					{
	    				ReservedSlot slot = (ReservedSlot) temp.get(i);
	    				System.out.println(slot.getClient().getName() + " " + slot.getTimeStart() + " - " + slot.getTimeEnd());
	    				
	    				min = slot.getTimeStart().getMinute();
	    				hour = slot.getTimeStart().getHour();
	    				
	    				if(hour == 0 && min == 30)
	        				start = hour + 1;
	        			else if(hour == 0)
	        				start = hour;
	        			else if(min == 0)
	        				start = hour * 2;
	        			else if(min == 30)
	        				start = hour * 2 + 1;
	    				
	    				
	    				min = slot.getTimeEnd().getMinute();
	        			hour = slot.getTimeEnd().getHour();
	        			
	        			if(hour == 0 && min == 30)
	        				end = hour + 1;
	        			else if(hour == 0)
	        				end = hour;
	        			else if(min == 0)
	        				end = hour * 2;
	        			else if(min == 30)
	        				end = hour * 2 + 1;
	        			
	        			for(int j = start; j < end; j++)
	        			{
	        				String label = "Doctor: " + slot.getDoctor().getName();
	        				if(j == start)
	        					labelList.get(j).setText(label + "|| Reserved by: " + slot.getClient().getName());
	        				
	        				labelList.get(j).setStyle("-fx-background-color: #00ffff;"); //blue = res
	        			}
					}
				}
			}
			
			break;
    	}
    }
    
    public void clearLabels()
    {
    	for(int i = 0; i < labelList.size(); i++)
    	{
    		Label temp = labelList.get(i);
    		
    		temp.setText("");
    		temp.setStyle("-fx-background-color: #ffffff;");
    	}
    }
    
}
