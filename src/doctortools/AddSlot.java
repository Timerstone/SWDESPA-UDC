package doctortools;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import controllers.DoctorController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AddSlot implements Initializable
{
	/***********************************************
	 * Class Attributes:
	 */
	private ObservableList<String> time	= FXCollections.observableArrayList("0800", "0830", "0900", "0930", "1000", "1030", "1100", "1130", "1200",
			"1230", "1300", "1330", "1400", "1430", "1500", "1530", "1600", "1630", "1700", "1730", "1800", "1830", "1900", "1930", "2000");
	private LocalDate date;
	private DoctorController control;
	
	/***********************************************
	 * FXML Resources:
	 */
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Button btnCancel;
    @FXML private CheckBox ckbRecurring;
    @FXML private ComboBox<String> cmbStart;
    @FXML private Label lblErrorMsg;
    @FXML private DatePicker datepicker;
    @FXML private ComboBox<String> cmbEnd;
    @FXML private Button btnConfirm;

    /***********************************************
	 * FXML Methods:
	 */
    @FXML
    private void confirmAdd() 
    {
    	LocalTime timeStart;
    	LocalTime timeEnd;
    	LocalDate date = datepicker.getValue();
    	int hour, min;
    	
    	hour = Integer.parseInt(cmbStart.getValue().toString()) / 100;
    	min = Integer.parseInt(cmbStart.getValue().toString()) - hour * 100;
    	timeStart = LocalTime.of(hour, min);
   
    	hour = Integer.parseInt(cmbEnd.getValue().toString()) / 100;
    	min = Integer.parseInt(cmbEnd.getValue().toString()) - hour * 100;
    	timeEnd = LocalTime.of(hour, min);
    	
    	control.addTimeSlot(timeStart, timeEnd, date);
    	if(ckbRecurring.isSelected())
    	{
    		for(int i = 0; i < 3; i++)
    		{
    			date = date.plusDays(7);
    			control.addTimeSlot(timeStart, timeEnd, date);
    		}
    		
    		
    	}
    	
    	cancel();
    }

    @FXML
    public void cancel() 
    {
    	Stage stage = (Stage)btnCancel.getScene().getWindow();
    	stage.close();
    }

    /***********************************************
	 * Initialization:
	 */
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'AddSlot.fxml'.";
        assert ckbRecurring != null : "fx:id=\"ckbRecurring\" was not injected: check your FXML file 'AddSlot.fxml'.";
        assert cmbStart != null : "fx:id=\"cmbStart\" was not injected: check your FXML file 'AddSlot.fxml'.";
        assert lblErrorMsg != null : "fx:id=\"lblErrorMsg\" was not injected: check your FXML file 'AddSlot.fxml'.";
        assert datepicker != null : "fx:id=\"datepicker\" was not injected: check your FXML file 'AddSlot.fxml'.";
        assert cmbEnd != null : "fx:id=\"cmbEnd\" was not injected: check your FXML file 'AddSlot.fxml'.";
        assert btnConfirm != null : "fx:id=\"btnConfirm\" was not injected: check your FXML file 'AddSlot.fxml'.";
        datepicker.setValue(LocalDate.now());
        cmbStart.setItems(time);
        cmbEnd.setItems(time);
        cmbStart.getSelectionModel().selectFirst();
        cmbEnd.getSelectionModel().selectFirst();
        ckbRecurring.setSelected(false);
        
        lblErrorMsg.setVisible(false);
        
    }
    
    /***********************************************
	 * Class Methods:
	 */
	public void setDate(LocalDate date)
	{
		this.date = date;
		datepicker.setValue(this.date);
	}
	
	public void setController(DoctorController control)
	{
		this.control = control;
		control.setAddSlot(this);
	}
	
	
	public void setErrorMsg(String text)
	{
		lblErrorMsg.setText(text);
        lblErrorMsg.setVisible(true);
	}
}
