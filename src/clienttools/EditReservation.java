package clienttools;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class EditReservation 
{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCancel;

    @FXML
    private Label lblErrorMsg;

    @FXML
    private ChoiceBox<?> chbTimeStart;

    @FXML
    private DatePicker datepicker;

    @FXML
    private Button btnConfirm;

    @FXML
    private ChoiceBox<?> chbReservations;

    @FXML
    private ChoiceBox<?> chbTimeEnd;
    
    @FXML
    void cancel() 
    {

    }

    @FXML
    void confirmNewReservation() 
    {

    }

    @FXML
    void initialize() 
    {
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'EditReservation.fxml'.";
        assert lblErrorMsg != null : "fx:id=\"lblErrorMsg\" was not injected: check your FXML file 'EditReservation.fxml'.";
        assert chbTimeStart != null : "fx:id=\"chbTimeStart\" was not injected: check your FXML file 'EditReservation.fxml'.";
        assert datepicker != null : "fx:id=\"datepicker\" was not injected: check your FXML file 'EditReservation.fxml'.";
        assert btnConfirm != null : "fx:id=\"btnConfirm\" was not injected: check your FXML file 'EditReservation.fxml'.";
        assert chbReservations != null : "fx:id=\"chbReservations\" was not injected: check your FXML file 'EditReservation.fxml'.";
        assert chbTimeEnd != null : "fx:id=\"chbTimeEnd\" was not injected: check your FXML file 'EditReservation.fxml'.";

    }
}
