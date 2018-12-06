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

public class RemoveSlot implements Initializable
{
	/***********************************************
	 * Class Attributes:
	 */
	private ObservableList<String> time	= FXCollections.observableArrayList();
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
    @FXML private Label lblErrorMsg;
    @FXML private ChoiceBox<String> cmbSlots;
    @FXML private DatePicker datepicker;
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
    private void freeUpSlot() 
    {   	
    	control.removeTimeSlot((TimeSlot) selectedSlot); 
    }

    /***********************************************
	 * Initialization:
	 */
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'RemoveSlot.fxml'.";
        assert cmbStart != null : "fx:id=\"cmbStart\" was not injected: check your FXML file 'RemoveSlot.fxml'.";
        assert lblErrorMsg != null : "fx:id=\"lblErrorMsg\" was not injected: check your FXML file 'RemoveSlot.fxml'.";
        assert cmbSlots != null : "fx:id=\"cmbSlots\" was not injected: check your FXML file 'RemoveSlot.fxml'.";
        assert datepicker != null : "fx:id=\"datepicker\" was not injected: check your FXML file 'RemoveSlot.fxml'.";
        assert cmbEnd != null : "fx:id=\"cmbEnd\" was not injected: check your FXML file 'RemoveSlot.fxml'.";
        assert btnConfirm != null : "fx:id=\"btnConfirm\" was not injected: check your FXML file 'RemoveSlot.fxml'.";

        //Startup:
        cmbStart.setItems(time);
        cmbStart.getSelectionModel().selectFirst();
        
        cmbEnd.setItems(time);
        cmbEnd.getSelectionModel().selectFirst();
        
        datepicker.setValue(LocalDate.now());
        
        date = LocalDate.now();
        
        lblErrorMsg.setText("");
        
        btnConfirm.setDisable(true);
        cmbStart.setDisable(true);
        cmbEnd.setDisable(true);
    }
    
    /***********************************************
	 * Class Methods:
	 */
    public void setController(DoctorController control)
    {
    	this.control = control;
    	control.setRemoveSlot(this);
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
		datepicker.setValue(this.date);
	}
    
    public void setErrorMsg(String text)
	{
		lblErrorMsg.setText(text);
	}
    
    public void initiateListeners()
    {
    	datepicker.valueProperty().addListener((ov, oldValue, newValue) -> {
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
    }
    
    public void setUpEdit(int index)
    {
    	if(!list.isEmpty() && index >= 0)
    	{
//	    	cmbStart.setDisable(false);
//	    	cmbEnd.setDisable(false);
	    	btnConfirm.setDisable(false);
	    	
	    	LocalTime timeStart;
	    	LocalTime timeEnd;
	    	selectedSlot = list.get(index);
	    	timeStart = list.get(index).getTimeStart();
	    	timeEnd = list.get(index).getTimeEnd();
	    	
	    	while(timeStart.isBefore(timeEnd))
	    	{
	    		time.add(timeStart.toString().replace(":", ""));
	    		timeStart = timeStart.plusMinutes(30);
	    	}
	    	
	    	time.add(timeEnd.toString().replace(",", ""));
	    	timeStart = list.get(index).getTimeStart();
	    	
	    	cmbStart.setItems(time);
	    	cmbEnd.setItems(time);
	    	cmbStart.getSelectionModel().select(timeStart.toString().replace(":", ""));
	    	cmbEnd.getSelectionModel().select(timeEnd.toString().replace(":", ""));
    	}
    }
}
