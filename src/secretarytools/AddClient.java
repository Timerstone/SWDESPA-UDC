package secretarytools;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controllers.SecretaryController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Doctor;
import models.Models;
import models.Slots;
import models.TimeSlot;

public class AddClient implements Initializable 
{
	/***********************************************
	 * Class Attributes:
	 */
	private ObservableList<String> time	= FXCollections.observableArrayList("0800", "0830", "0900", "0930", "1000", "1030", "1100", "1130", "1200",
			"1230", "1300", "1330", "1400", "1430", "1500", "1530", "1600", "1630", "1700", "1730", "1800", "1830", "1900", "1930", "2000");
	private ArrayList<Slots> list;
	private LocalDate date;
	private Models model;
	private SecretaryController control;
	private ArrayList<Doctor> doctors = new ArrayList<Doctor>();
	private Doctor doctor;
	
	/***********************************************
	 * FXML Resources:
	 */
	@FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private ComboBox<String> cmbStart;
    @FXML private ComboBox<String> cmbEnd;
    @FXML private DatePicker datepicker;
    @FXML private Button btnConfirm;
    @FXML private Button btnCancel;
    @FXML private Label lblErrorMsg;
    @FXML private CheckBox ckbRecurring;
    @FXML private TextField txfName;
    @FXML private ChoiceBox<String> chbDoctor;
    @FXML private ChoiceBox<String> chbTimeSlot;
	
	/***********************************************
	 * FXML Methods:
	 */
    
    @FXML
    void cancel() 
    {
    	Stage stage = (Stage)btnCancel.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void confirmAdd() 
    {
    	LocalTime timeStart;
    	LocalTime timeEnd;
    	int hour;
    	int min;
    	String client;
    	
    	hour = Integer.parseInt(cmbStart.getValue().toString()) / 100;
    	min = Integer.parseInt(cmbStart.getValue().toString()) - hour * 100;
    	timeStart = LocalTime.of(hour, min);
    	
		hour = Integer.parseInt(cmbEnd.getValue().toString()) / 100;
		min = Integer.parseInt(cmbEnd.getValue().toString()) - hour * 100;
    	timeEnd = LocalTime.of(hour, min);
    	
    	client = txfName.getText();
    	
    	control.takeAppointment(timeStart, timeEnd, date, doctor, client);
    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) 
    {
    	assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'AddClient.fxml'.";
        assert cmbStart != null : "fx:id=\"cmbStart\" was not injected: check your FXML file 'AddClient.fxml'.";
        assert chbDoctor != null : "fx:id=\"chbDoctor\" was not injected: check your FXML file 'AddClient.fxml'.";
        assert lblErrorMsg != null : "fx:id=\"lblErrorMsg\" was not injected: check your FXML file 'AddClient.fxml'.";
        assert txfName != null : "fx:id=\"txfName\" was not injected: check your FXML file 'AddClient.fxml'.";
        assert chbTimeSlot != null : "fx:id=\"chbTimeSlot\" was not injected: check your FXML file 'AddClient.fxml'.";
        assert datepicker != null : "fx:id=\"datepicker\" was not injected: check your FXML file 'AddClient.fxml'.";
        assert cmbEnd != null : "fx:id=\"cmbEnd\" was not injected: check your FXML file 'AddClient.fxml'.";
        assert btnConfirm != null : "fx:id=\"btnConfirm\" was not injected: check your FXML file 'AddClient.fxml'.";
        
        this.date = LocalDate.now();
        datepicker.setValue(LocalDate.now());
        cmbStart.setItems(time);
        cmbEnd.setItems(time);
        cmbStart.getSelectionModel().selectFirst();
        cmbEnd.getSelectionModel().selectFirst();
           
        
        lblErrorMsg.setVisible(false);
	}
    /***********************************************
	 * Class Methods:
	 */
    
    public void setModels(Models model)
    {
    	this.model = model;
    }
    
	public void setDate(LocalDate date)
	{
		this.date = date;
		datepicker.setValue(this.date);
	}
	
	public void setController(SecretaryController control)
	{
		this.control = control;
	}
	
	
	public void setErrorMsg(String text)
	{
		lblErrorMsg.setText(text);
        lblErrorMsg.setVisible(true);
	}
    
    public void initiateListeners()
    {
    	doctors = model.getDoctors();
    	
    	for(int i = 0; i < doctors.size(); i++)
    	{
    		chbDoctor.getItems().add(doctors.get(i).getName());
    	}
    	
    	chbDoctor.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
        	
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
				chbDoctor.setValue(chbDoctor.getItems().get((Integer) number2));
				doctor = doctors.get((Integer) number2);
				addChoices();
			}
        	
		});
    	
    	datepicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            date = newValue;
            addChoices();
        });
    	
    	chbDoctor.getSelectionModel().selectFirst();
    	addChoices();
    }
    
    public void addChoices()
    {
    	if(!chbTimeSlot.getItems().isEmpty())
    	{
    		chbTimeSlot.getItems().clear();
    		list.clear();
    	}
    	
    	list = model.getTimeSlots(date, doctor);
    	
    	for(int i = 0; i < list.size(); i++)
    	{
    		if(!((TimeSlot) list.get(i)).isReserved())
    			chbTimeSlot.getItems().add(list.get(i).getTimeStart() + " - " + list.get(i).getTimeEnd());
    		else
    		{
    			list.remove(i);
    			i--;
    		}
    	}
    }

}
