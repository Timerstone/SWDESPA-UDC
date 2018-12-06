package clienttools;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import controllers.ClientController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.Doctor;
import models.Models;
import models.Slots;
import models.Users;
import modules.Modules;

public class TakeSlot 
{
	/***********************************************
	 * Class Attributes:
	 */
	private ObservableList<String> time	= FXCollections.observableArrayList("0800", "0830", "0900", "0930", "1000", "1030", "1100", "1130", "1200",
			"1230", "1300", "1330", "1400", "1430", "1500", "1530", "1600", "1630", "1700", "1730", "1800", "1830", "1900", "1930", "2000");
	private LocalDate date;
	private ArrayList<Doctor> doctors = new ArrayList<Doctor>(); 
	private ClientController control;
	private Models model;
	private Modules module;
	private Users user;
	private ArrayList<Slots> list;
	
	/***********************************************
	 * FXML Resources:
	 */
	@FXML private DatePicker datepicker;
    @FXML private Label lblErrorMsg;
    @FXML private CheckBox ckbRecurring;
    @FXML private Button btnCancel;
    @FXML private Button btnConfirm;
    @FXML private ChoiceBox<String> chbDoctor;
    @FXML private ChoiceBox<String> chbSlots;
    @FXML private ChoiceBox<String> chbReserve;
    @FXML private ChoiceBox<String> chbTimeEnd;
    @FXML private ChoiceBox<String> chbTimeStart;
    
    @FXML
    void initialize() {
        assert datepicker != null : "fx:id=\"datepicker\" was not injected: check your FXML file 'TakeSlot.fxml'.";
        assert lblErrorMsg != null : "fx:id=\"lblErrorMsg\" was not injected: check your FXML file 'TakeSlot.fxml'.";
        assert ckbRecurring != null : "fx:id=\"ckbRecurring\" was not injected: check your FXML file 'TakeSlot.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'TakeSlot.fxml'.";
        assert btnConfirm != null : "fx:id=\"btnConfirm\" was not injected: check your FXML file 'TakeSlot.fxml'.";
        assert chbDoctor != null : "fx:id=\"chbDoctor\" was not injected: check your FXML file 'TakeSlot.fxml'.";
        assert chbSlots != null : "fx:id=\"chbSlots\" was not injected: check your FXML file 'TakeSlot.fxml'.";
        assert chbReserve != null : "fx:id=\"chbReserve\" was not injected: check your FXML file 'TakeSlot.fxml'.";
        datepicker.setValue(LocalDate.now());
        chbTimeStart.setItems(time);
        chbTimeEnd.setItems(time);
        chbTimeStart.getSelectionModel().selectFirst();
        chbTimeEnd.getSelectionModel().selectFirst();
        
        lblErrorMsg.setVisible(false);
        
    }
    
	@FXML
	public void cancel() 
    {
    	Stage stage = (Stage)btnCancel.getScene().getWindow();
    	stage.close();
    }
		
	public void setDate(LocalDate date)
	{
		this.date = date;
		datepicker.setValue(this.date);
	}
	
	public void setController(ClientController control)
	{
		this.control = control;
		control.setTakeSlot(this);
	}
	
	
	public void setErrorMsg(String text)
	{
		lblErrorMsg.setText(text);
        lblErrorMsg.setVisible(true);
	}
	
	public void confirmNewReservation() {
		LocalTime timeStart;
    	LocalTime timeEnd;
    	Doctor doctor =  new Doctor("", "", 0, 0);
    	LocalDate date = datepicker.getValue();
    	int hour, min;
    	
    	for(int i=0; i<doctors.size(); i++) {
    		String name;
    		name = chbDoctor.getValue();
    		if (name.equalsIgnoreCase(doctors.get(i).getName())) {
    			doctor = doctors.get(i);
    			break;
    		}
    			
    	}
    	
    	hour = Integer.parseInt(chbTimeStart.getValue().toString()) / 100;
    	min = Integer.parseInt(chbTimeStart.getValue().toString()) - hour * 100;
    	timeStart = LocalTime.of(hour, min);

    	hour = Integer.parseInt(chbTimeEnd.getValue().toString()) / 100;
    	min = Integer.parseInt(chbTimeEnd.getValue().toString()) - hour * 100;
    	timeEnd = LocalTime.of(hour, min);
    	
    	LocalDate selectedDate = datepicker.getValue();
    	control.addReservation(timeStart, timeEnd, user, doctor, selectedDate);
	}

	public void initiateListeners()
    {
		doctors = model.getDoctors();
    	for(int i = 0; i < doctors.size(); i++)
    	{
    		chbDoctor.getItems().add(doctors.get(i).getName());
    	}
    	chbDoctor.getSelectionModel().selectFirst();
		
    }
	
	public void setModels(Models model)
    {
    	this.model = model;
    }
	
	public Users getUser() 
	{
		return user;
	}

	public void setUser(Users user) 
	{
		this.user = user;
	}
	
}
