package viewpanels;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import models.Doctor;
import models.Models;
import models.ReservedSlot;
import models.Slots;
import models.TimeSlot;
import modules.Modules;

public class WeeklyView extends Panels implements Initializable
{
	/***********************************************
	 * Class Attributes:
	 */
	private DoubleProperty vPos = new SimpleDoubleProperty();
	private DoubleProperty hPos = new SimpleDoubleProperty();
	private ArrayList<Label> monList;
	private ArrayList<Label> tueList;
	private ArrayList<Label> wedList;
	private ArrayList<Label> thuList;
	private ArrayList<Label> friList;
	private ArrayList<Label> satList;
	private ArrayList<Label> sunList;
	private Models model;
	private Modules module;
	private LocalDate date;
	private LocalDate startOfWeek;
	private LocalDate endOfWeek;
	private int filter;
	private Doctor doctorfilter;
	
	/***********************************************
	 * FXML Resources:
	 */
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Label lblWed;
    @FXML private Label lblFri;
    @FXML private Button btnPrev;
    @FXML private Button btnNext;
    @FXML private Label lblThu;
    @FXML private Label lblTue;
    @FXML private GridPane gpnList;
    @FXML private ScrollPane spnDays;
    @FXML private Label lblSat;
    @FXML private Label lblMon;
    @FXML private Label lblSun;
    @FXML private ScrollPane spnBoard;
    @FXML private Label lblWeek;
    @FXML private ScrollPane spnTime;
    @FXML private GridPane gpnBoard;
    
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
    public void initialize(URL location, ResourceBundle resources) 
    {
        assert lblWed != null : "fx:id=\"lblWed\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert lblFri != null : "fx:id=\"lblFri\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert btnPrev != null : "fx:id=\"btnPrev\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert btnNext != null : "fx:id=\"btnNext\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert lblThu != null : "fx:id=\"lblThu\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert lblTue != null : "fx:id=\"lblTue\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert gpnList != null : "fx:id=\"gpnList\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert spnDays != null : "fx:id=\"spnDays\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert lblSat != null : "fx:id=\"lblSat\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert lblMon != null : "fx:id=\"lblMon\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert lblSun != null : "fx:id=\"lblSun\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert spnBoard != null : "fx:id=\"spnBoard\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert lblWeek != null : "fx:id=\"lblWeek\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert spnTime != null : "fx:id=\"spnTime\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert gpnBoard != null : "fx:id=\"gpnBoard\" was not injected: check your FXML file 'WeeklyView.fxml'.";

        spnTime.setVbarPolicy(ScrollBarPolicy.NEVER);
        spnDays.setHbarPolicy(ScrollBarPolicy.NEVER);
        
        vPos.bind(spnBoard.vvalueProperty());        
        vPos.addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {
                 spnTime.setVvalue((double) arg2);

            }
        });
        
        hPos.bind(spnBoard.hvalueProperty());
        hPos.addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {
                 spnDays.setHvalue((double) arg2);

            }
        });
        
        monList = new ArrayList<Label>();
        tueList = new ArrayList<Label>();
        wedList = new ArrayList<Label>();
        thuList = new ArrayList<Label>();
        friList = new ArrayList<Label>();
        satList = new ArrayList<Label>();
        sunList = new ArrayList<Label>();
        Node node;
    	for(int i = 0; i < 48; i++)
    	{
    		monList.add(new Label());
    		node = monList.get(i);
    		gpnBoard.add(node, 0, i);
    		monList.get(i).setPrefSize(299, 23);
    		monList.get(i).setAlignment(Pos.BOTTOM_CENTER);
    		monList.get(i).setText("");
    		monList.get(i).setStyle("-fx-background-color: #ffffff;");
    		monList.get(i).setTextAlignment(TextAlignment.CENTER);
    		
    		tueList.add(new Label());
    		node = tueList.get(i);
    		gpnBoard.add(node, 1, i);
    		tueList.get(i).setPrefSize(299, 23);
    		tueList.get(i).setAlignment(Pos.BOTTOM_CENTER);
    		tueList.get(i).setText("");
    		tueList.get(i).setStyle("-fx-background-color: #ffffff;");
    		tueList.get(i).setTextAlignment(TextAlignment.CENTER);
    		
    		wedList.add(new Label());
    		node = wedList.get(i);
    		gpnBoard.add(node, 2, i);
    		wedList.get(i).setPrefSize(299, 23);
    		wedList.get(i).setAlignment(Pos.BOTTOM_CENTER);
    		wedList.get(i).setText("");
    		wedList.get(i).setStyle("-fx-background-color: #ffffff;");
    		wedList.get(i).setTextAlignment(TextAlignment.CENTER);
    		
    		thuList.add(new Label());
    		node = thuList.get(i);
    		gpnBoard.add(node, 3, i);
    		thuList.get(i).setPrefSize(299, 23);
    		thuList.get(i).setAlignment(Pos.BOTTOM_CENTER);
    		thuList.get(i).setText("");
    		thuList.get(i).setStyle("-fx-background-color: #ffffff;");
    		thuList.get(i).setTextAlignment(TextAlignment.CENTER);
    		
    		friList.add(new Label());
    		node = friList.get(i);
    		gpnBoard.add(node, 4, i);
    		friList.get(i).setPrefSize(299, 23);
    		friList.get(i).setAlignment(Pos.BOTTOM_CENTER);
    		friList.get(i).setText("");
    		friList.get(i).setStyle("-fx-background-color: #ffffff;");
    		friList.get(i).setTextAlignment(TextAlignment.CENTER);
    		
    		satList.add(new Label());
    		node = satList.get(i);
    		gpnBoard.add(node, 5, i);
    		satList.get(i).setPrefSize(299, 23);
    		satList.get(i).setAlignment(Pos.BOTTOM_CENTER);
    		satList.get(i).setText("");
    		satList.get(i).setStyle("-fx-background-color: #ffffff;");
    		satList.get(i).setTextAlignment(TextAlignment.CENTER);
    		
    		sunList.add(new Label());
    		node = sunList.get(i);
    		gpnBoard.add(node, 6, i);
    		sunList.get(i).setPrefSize(299, 23);
    		sunList.get(i).setAlignment(Pos.BOTTOM_CENTER);
    		sunList.get(i).setText("");
    		sunList.get(i).setStyle("-fx-background-color: #ffffff;");
    		sunList.get(i).setTextAlignment(TextAlignment.CENTER);
    	}
    }
    
    /***********************************************
	 * Class Methods:
	 */
    public void refresh()
    {
    	lblMon.setText(startOfWeek.toString());
    	lblTue.setText(startOfWeek.plusDays(1).toString());
    	lblWed.setText(startOfWeek.plusDays(2).toString());
    	lblThu.setText(startOfWeek.plusDays(3).toString());
    	lblFri.setText(startOfWeek.plusDays(4).toString());
    	lblSat.setText(startOfWeek.plusDays(5).toString());
    	lblSun.setText(startOfWeek.plusDays(6).toString());
    	
    	ArrayList<Slots> temp = new ArrayList<Slots>();
    	ArrayList<Label> list = monList;
    	int hour;
    	int min;
    	int start = 0;
    	int end = 0;
    	LocalDate date;
    	boolean load;
    	
    	date = this.date;
    	
    	clearLabels();
    	
    	switch(module.getUser().getUserType().toUpperCase())
    	{
    		case "DOCTOR":
    			for(int i = 0; i < list.size(); i++)
    			{
    				switch(i)
    				{
    					case 0:
    						list = monList;
    						break;
    					case 1:
    						list = tueList;
    						break;
    					case 2:
    						list = wedList;
    						break;
    					case 3:
    						list = thuList;
    						break;
    					case 4:
    						list = friList;
    						break;
    					case 5:
    						list = satList;
    						break;
    					case 6:
    						list = sunList;
    						break;
    				}
    				
    				date = startOfWeek.plusDays(i);
    				temp = model.getTimeSlots(date, (Doctor) module.getUser());
    				
    				for(int j = 0; j < temp.size(); j++)
    				{
    					if(temp.get(j).getDate().equals(date))
    					{
    						TimeSlot slot = (TimeSlot) temp.get(j);
    						
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
    	        			
    	        			for(int k = start; k < end; k++)
    	        			{
    	        				if(k == start)
    	        					if(filter == 1 && slot.isReserved()) {
    	        						list.get(k).setText(slot.getTimeStart().toString() + " - " + slot.getTimeEnd().toString());
    	                        	}
    	                        	else if(filter == 2 && !slot.isReserved()) {
    	                        		list.get(k).setText(slot.getTimeStart().toString() + " - " + slot.getTimeEnd().toString());
    	                        	}
    	                            else if(filter == 0) {
    	                                list.get(k).setText(slot.getTimeStart().toString() + " - " + slot.getTimeEnd().toString());
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
    	        						list.get(k).setStyle("-fx-background-color: #00ffff;");
    	        					}
    	        					else
    	        					{
    	        						list.get(k).setStyle("-fx-background-color: #2ecc71;");
    	        					}
    	        				}
    	        			}
    					}
    				}
    			}
    			
    			break;
    		case "CLIENT":
    			if(doctorfilter != null)
    			{
	    			for(int i = 0; i < list.size(); i++)
	    			{
	    				switch(i)
	    				{
	    					case 0:
	    						list = monList;
	    						break;
	    					case 1:
	    						list = tueList;
	    						break;
	    					case 2:
	    						list = wedList;
	    						break;
	    					case 3:
	    						list = thuList;
	    						break;
	    					case 4:
	    						list = friList;
	    						break;
	    					case 5:
	    						list = satList;
	    						break;
	    					case 6:
	    						list = sunList;
	    						break;
	    				}
	    				
	    				date = startOfWeek.plusDays(i);
	    				temp = model.getTimeSlots(date, doctorfilter);
	    				
	    				for(int j = 0; j < temp.size(); j++)
	    				{
	    					if(temp.get(j).getDate().equals(date))
	    					{
	    						TimeSlot slot = (TimeSlot) temp.get(j);
	    						
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
	    	        			
	    	        			for(int k = start; k < end; k++)
	    	        			{
	    	        				if(k == start)
	    	        					if(filter == 1 && slot.isReserved()) {
	    	        						list.get(k).setText(slot.getTimeStart().toString() + " - " + slot.getTimeEnd().toString());
	    	                        	}
	    	                        	else if(filter == 2 && !slot.isReserved()) {
	    	                        		list.get(k).setText(slot.getTimeStart().toString() + " - " + slot.getTimeEnd().toString());
	    	                        	}
	    	                            else if(filter == 0) {
	    	                                list.get(k).setText(slot.getTimeStart().toString() + " - " + slot.getTimeEnd().toString());
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
	    	        						list.get(k).setStyle("-fx-background-color: #00ffff;");
	    	        					}
	    	        					else
	    	        					{
	    	        						list.get(k).setStyle("-fx-background-color: #2ecc71;");
	    	        					}
	    	        				}
	    	        			}
	    					}
	    				}
	    			}
	    			
	    			if(filter == 1 || filter == 0)
	    			{
	    				for(int i = 0; i < list.size(); i++)
	        			{
	        				switch(i)
	        				{
	        					case 0:
	        						list = monList;
	        						break;
	        					case 1:
	        						list = tueList;
	        						break;
	        					case 2:
	        						list = wedList;
	        						break;
	        					case 3:
	        						list = thuList;
	        						break;
	        					case 4:
	        						list = friList;
	        						break;
	        					case 5:
	        						list = satList;
	        						break;
	        					case 6:
	        						list = sunList;
	        						break;
	        				}
	        				
	        				temp = model.getReservedSlots(startOfWeek.plusDays(i), doctorfilter);
	    				
		    				for(int j = 0; j < temp.size(); j++)
		    				{
	    	    				ReservedSlot slot = (ReservedSlot) temp.get(j);
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
	    	        			
	    	        			for(int k = start; k < end; k++)
	    	        			{
	    	        				String label = "Doctor: " + slot.getDoctor().getName();
	    	        				if(k == start)
	    	        					list.get(k).setText(label);
	    	        				
	    	        				list.get(k).setStyle("-fx-background-color: #00ffff;"); //blue = res
	    	        			}
		    				}
		    			}
	    			}
    			}
    			
    		case "SECRETARY":
    			if(doctorfilter != null)
    			{
	    			for(int i = 0; i < list.size(); i++)
	    			{
	    				switch(i)
	    				{
	    					case 0:
	    						list = monList;
	    						break;
	    					case 1:
	    						list = tueList;
	    						break;
	    					case 2:
	    						list = wedList;
	    						break;
	    					case 3:
	    						list = thuList;
	    						break;
	    					case 4:
	    						list = friList;
	    						break;
	    					case 5:
	    						list = satList;
	    						break;
	    					case 6:
	    						list = sunList;
	    						break;
	    				}
	    				
	    				date = startOfWeek.plusDays(i);
	    				temp = model.getTimeSlots(date, doctorfilter);
	    				
	    				for(int j = 0; j < temp.size(); j++)
	    				{
	    					if(temp.get(j).getDate().equals(date))
	    					{
	    						TimeSlot slot = (TimeSlot) temp.get(j);
	    						
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
	    	        			
	    	        			for(int k = start; k < end; k++)
	    	        			{
	    	        				if(k == start)
	    	        					if(filter == 1 && slot.isReserved()) {
	    	        						list.get(k).setText(slot.getTimeStart().toString() + " - " + slot.getTimeEnd().toString());
	    	                        	}
	    	                        	else if(filter == 2 && !slot.isReserved()) {
	    	                        		list.get(k).setText(slot.getTimeStart().toString() + " - " + slot.getTimeEnd().toString());
	    	                        	}
	    	                            else if(filter == 0) {
	    	                                list.get(k).setText(slot.getTimeStart().toString() + " - " + slot.getTimeEnd().toString());
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
	    	        						list.get(k).setStyle("-fx-background-color: #00ffff;");
	    	        					}
	    	        					else
	    	        					{
	    	        						list.get(k).setStyle("-fx-background-color: #2ecc71;");
	    	        					}
	    	        				}
	    	        			}
	    					}
	    				}
	    			}
	    			
	    			if(filter == 1 || filter == 0)
	    			{
	    				for(int i = 0; i < list.size(); i++)
	        			{
	        				switch(i)
	        				{
	        					case 0:
	        						list = monList;
	        						break;
	        					case 1:
	        						list = tueList;
	        						break;
	        					case 2:
	        						list = wedList;
	        						break;
	        					case 3:
	        						list = thuList;
	        						break;
	        					case 4:
	        						list = friList;
	        						break;
	        					case 5:
	        						list = satList;
	        						break;
	        					case 6:
	        						list = sunList;
	        						break;
	        				}
	        				
	        				temp = model.getReservedSlots(startOfWeek.plusDays(i), doctorfilter);
	    				
		    				for(int j = 0; j < temp.size(); j++)
		    				{
	    	    				ReservedSlot slot = (ReservedSlot) temp.get(j);
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
	    	        			
	    	        			for(int k = start; k < end; k++)
	    	        			{
	    	        				String label = "Doctor: " + slot.getDoctor().getName();
	    	        				if(j == start)
	    	        					list.get(k).setText(label + "|| Reserved by: " + slot.getClient().getName());
	    	        				
	    	        				list.get(k).setStyle("-fx-background-color: #00ffff;"); //blue = res
	    	        			}
		    				}
		    			}
	    			}
    			}
    			
    			break;
    	}
    }
    
    public void setModels(Models model)
    {
    	this.model = model;
    }
    
    public void setModule(Modules module)
    {
    	this.module = module;
    }
    
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
    
    public void setFilter(int filter)
    {
    	this.filter = filter;
    	refresh();
    }
    
    public void clearLabels()
    {
    	for(int i = 0; i < monList.size(); i++)
    	{
    		monList.get(i).setText("");
    		monList.get(i).setStyle("-fx-background-color: #ffffff;");
    		
    		tueList.get(i).setText("");
    		tueList.get(i).setStyle("-fx-background-color: #ffffff;");
    		
    		wedList.get(i).setText("");
    		wedList.get(i).setStyle("-fx-background-color: #ffffff;");
    		
    		thuList.get(i).setText("");
    		thuList.get(i).setStyle("-fx-background-color: #ffffff;");
    		
    		friList.get(i).setText("");
    		friList.get(i).setStyle("-fx-background-color: #ffffff;");
    		
    		satList.get(i).setText("");
    		satList.get(i).setStyle("-fx-background-color: #ffffff;");
    		
    		sunList.get(i).setText("");
    		sunList.get(i).setStyle("-fx-background-color: #ffffff;");
    	}
    }

	@Override
	public void setDoctorFilter(Doctor filter) 
	{
		this.doctorfilter = filter;
		refresh();
	}

}
