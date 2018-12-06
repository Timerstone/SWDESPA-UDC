package doctortools;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controllers.DoctorController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.Doctor;
import models.Models;
import models.Slots;
import models.TimeSlot;
import models.Users;

public class EditSlot implements Initializable
{
	/***********************************************
	 * Class Attributes:
	 */
	private ObservableList<String> time	= FXCollections.observableArrayList("0800", "0830", "0900", "0930", "1000", "1030", "1100", "1130", "1200",
			"1230", "1300", "1330", "1400", "1430", "1500", "1530", "1600", "1630", "1700", "1730", "1800", "1830", "1900", "1930", "2000");
	private ArrayList<Slots> list;
	private Slots selectedSlot;
	private DoctorController control;
	private Models model;
	private LocalDate date;
	private Users user;
	
	/***********************************************
	 * FXML Resources:
	 */
    @FXML private Button btnCancel;
    @FXML private ComboBox<String> cmbStart;
    @FXML private DatePicker dtpChoose;
    @FXML private Label lblErrorMsg;
    @FXML private ChoiceBox<String> cmbSlots;
    @FXML private DatePicker dtpChange;
    @FXML private ComboBox<String> cmbEnd;
    @FXML private Button btnConfirm;

    /***********************************************
	 * FXML Methods:
	 */
    @FXML
    public void cancel() 
    {
    	Stage stage = (Stage)btnCancel.getScene().getWindow();
    	stage.close();
    }
    
    @FXML
    private void confirmChange() 
    {
    	LocalTime timeStart;
    	LocalTime timeEnd;
    	LocalDate date;
    	int hour;
    	int min;
    	
    	hour = Integer.parseInt(cmbStart.getValue().toString()) / 100;
    	min = Integer.parseInt(cmbStart.getValue().toString()) - hour * 100;
    	timeStart = LocalTime.of(hour, min);
    	
		hour = Integer.parseInt(cmbEnd.getValue().toString()) / 100;
		min = Integer.parseInt(cmbEnd.getValue().toString()) - hour * 100;
    	timeEnd = LocalTime.of(hour, min);
    	date = dtpChange.getValue();
    	
    	control.updateTimeSlot((TimeSlot) selectedSlot, timeStart, timeEnd, date);
    }

    /***********************************************
	 * Initialization:
	 */
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
    	 assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'EditSlot.fxml'.";
         assert cmbStart != null : "fx:id=\"cmbStart\" was not injected: check your FXML file 'EditSlot.fxml'.";
         assert dtpChoose != null : "fx:id=\"dtpChoose\" was not injected: check your FXML file 'EditSlot.fxml'.";
         assert lblErrorMsg != null : "fx:id=\"lblErrorMsg\" was not injected: check your FXML file 'EditSlot.fxml'.";
         assert cmbSlots != null : "fx:id=\"cmbSlots\" was not injected: check your FXML file 'EditSlot.fxml'.";
         assert dtpChange != null : "fx:id=\"dtpChange\" was not injected: check your FXML file 'EditSlot.fxml'.";
         assert cmbEnd != null : "fx:id=\"cmbEnd\" was not injected: check your FXML file 'EditSlot.fxml'.";
         assert btnConfirm != null : "fx:id=\"btnConfirm\" was not injected: check your FXML file 'EditSlot.fxml'.";
         
        //StartUp:
         cmbStart.setItems(time);
         cmbStart.getSelectionModel().selectFirst();
         
         cmbEnd.setItems(time);
         cmbEnd.getSelectionModel().selectFirst();
         
         dtpChoose.setValue(LocalDate.now());
         
         date = LocalDate.now();
         dtpChange.setValue(date);
         
         lblErrorMsg.setText("");
         
         btnConfirm.setDisable(true);
         cmbStart.setDisable(true);
         cmbEnd.setDisable(true);
         dtpChange.setDisable(true);
    }
    
    /***********************************************
	 * Class Methods:
	 */
    public void setController(DoctorController control)
    {
    	this.control = control;
    	control.setEditSlot(this);
    }
    
    public void setModels(Models model)
    {
    	this.model = model;
    }
    
    public void setUser(Users user)
    {
    	this.user = user;
    }
    
    public void setDate(LocalDate date)
	{
		this.date = date;
		dtpChoose.setValue(this.date);
	}
    
    public void setErrorMsg(String text)
	{
		lblErrorMsg.setText(text);
	}
    
    public void initiateListeners()
    {
    	dtpChoose.valueProperty().addListener((ov, oldValue, newValue) -> {
            date = newValue;
            addChoices();
            disableWidgets();
        });
    	
    	cmbSlots.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
        	@Override
        	public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
        		cmbSlots.getSelectionModel().clearAndSelect((Integer) number2);
        		setUpEdit((Integer) number2);
        	}
        });
    	
    	addChoices();
    }
    
    public void addChoices()
    {
    	if(!cmbSlots.getItems().isEmpty())
    	{
    		cmbSlots.getItems().clear();
    		list.clear();
    	}
    	
    	list = model.getTimeSlots(date, (Doctor) user);
    	
    	for(int i = 0; i < list.size(); i++)
    	{
    		if(!((TimeSlot) list.get(i)).isReserved())
    			cmbSlots.getItems().add(list.get(i).getTimeStart() + " - " + list.get(i).getTimeEnd());
    		else
    		{
    			list.remove(i);
    			i--;
    		}
    	}
    }
    
    public void disableWidgets()
    {
    	lblErrorMsg.setText("");
        lblErrorMsg.setVisible(false);
    	btnConfirm.setDisable(true);
        cmbStart.setDisable(true);
        cmbEnd.setDisable(true);
        dtpChange.setDisable(true);
    }
    
    public void setUpEdit(int index)
    {
    	if(!list.isEmpty() && index >= 0)
    	{
	    	cmbStart.setDisable(false);
	    	cmbEnd.setDisable(false);
	    	btnConfirm.setDisable(false);
	    	dtpChange.setDisable(false);
	    	
	    	LocalTime timeStart;
	    	LocalTime timeEnd;
	    	selectedSlot = list.get(index);
	    	timeStart = list.get(index).getTimeStart();
	    	timeEnd = list.get(index).getTimeEnd();
	    	
	    	dtpChange.setValue(date);
	    	cmbStart.setItems(time);
	    	cmbEnd.setItems(time);
	    	cmbStart.getSelectionModel().select(timeStart.toString().replace(":", ""));
	    	cmbEnd.getSelectionModel().select(timeEnd.toString().replace(":", ""));
    	}
    }
}
