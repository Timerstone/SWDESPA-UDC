package modules;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import viewpanels.DailyAgendaView;
import viewpanels.DailyView;
import viewpanels.WeeklyAgendaView;
import viewpanels.WeeklyView;

public class AdminModule extends Modules implements Initializable
{
	/***********************************************
	 * Class Attributes:
	 */
	
	/***********************************************
	 * FXML Resources:
	 */
	@FXML private AnchorPane apnBoard;
    @FXML private Button btnLogOut;
    @FXML private Button btnAddReserve;
    @FXML private Button btnRemoveReserve;
    @FXML private Button btnDefault;
    @FXML private Button btnTake;
    @FXML private Button btnToday;
    @FXML private Button btnAddTime;
    @FXML private Button btnCancel;
    @FXML private Button btnRemoveUser;
    @FXML private Button btnAddUser;
    @FXML private Button btnRemoveTime;
    @FXML private Button btnExit;
    @FXML private ChoiceBox<String> chbFilter;
    @FXML private ChoiceBox<String> chbView;
    @FXML private DatePicker datepicker;
    @FXML private Label lblUser;
    @FXML private Label lblToday;
    @FXML private TextArea txaNotif;
    
    /***********************************************
	 * FXML Methods:
	 */
    @FXML
    private void exit() 
    {

    }
    
    @FXML
    private void logOut()
    {
    	main.logOut(user);
    }

    /***********************************************
	 * Initialization:
	 */
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        assert btnLogOut != null : "fx:id=\"btnLogOut\" was not injected: check your FXML file 'AdminModule.fxml'.";
        assert btnAddReserve != null : "fx:id=\"btnAddReserve\" was not injected: check your FXML file 'AdminModule.fxml'.";
        assert btnRemoveReserve != null : "fx:id=\"btnRemoveReserve\" was not injected: check your FXML file 'AdminModule.fxml'.";
        assert btnDefault != null : "fx:id=\"btnDefault\" was not injected: check your FXML file 'AdminModule.fxml'.";
        assert btnTake != null : "fx:id=\"btnTake\" was not injected: check your FXML file 'AdminModule.fxml'.";
        assert txaNotif != null : "fx:id=\"txaNotif\" was not injected: check your FXML file 'AdminModule.fxml'.";
        assert btnToday != null : "fx:id=\"btnToday\" was not injected: check your FXML file 'AdminModule.fxml'.";
        assert lblToday != null : "fx:id=\"lblToday\" was not injected: check your FXML file 'AdminModule.fxml'.";
        assert chbView != null : "fx:id=\"chbView\" was not injected: check your FXML file 'AdminModule.fxml'.";
        assert datepicker != null : "fx:id=\"datepicker\" was not injected: check your FXML file 'AdminModule.fxml'.";
        assert apnBoard != null : "fx:id=\"apnBoard\" was not injected: check your FXML file 'AdminModule.fxml'.";
        assert btnAddTime != null : "fx:id=\"btnAddTime\" was not injected: check your FXML file 'AdminModule.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'AdminModule.fxml'.";
        assert btnRemoveUser != null : "fx:id=\"btnRemoveUser\" was not injected: check your FXML file 'AdminModule.fxml'.";
        assert btnAddUser != null : "fx:id=\"btnAddUser\" was not injected: check your FXML file 'AdminModule.fxml'.";
        assert chbFilter != null : "fx:id=\"chbFilter\" was not injected: check your FXML file 'AdminModule.fxml'.";
        assert btnRemoveTime != null : "fx:id=\"btnRemoveTime\" was not injected: check your FXML file 'AdminModule.fxml'.";
        assert lblUser != null : "fx:id=\"lblUser\" was not injected: check your FXML file 'AdminModule.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'AdminModule.fxml'.";

    }
    
    /***********************************************
   	 * Class Methods:
   	 */ 
    public void setDate(LocalDate date)
    {
    	this.date = date;
    }
    
    public void setView(Node node)
    {
    	apnBoard.getChildren().setAll(node);
    	
    	if(chbView.getValue().equals("Weekly"))
    	{
    		panel = (WeeklyView) getPanelController(node);
    	}
    	else if(chbView.getValue().equals("Daily"))
    	{
    		panel = (DailyView) getPanelController(node);
    	}
    	else if(chbView.getValue().equals("Daily Agenda"))
    	{
    		panel = (DailyAgendaView) getPanelController(node);
    	}
    	else if(chbView.getValue().equals("Weekly Agenda"))
    	{
    		panel = (WeeklyAgendaView) getPanelController(node);
    	}
    	
    	panel.setModels(model);
		panel.setModule(this);
		panel.setDate(date);
    }
    
    public void setUp()
    {
    	lblUser.setText(user.getName());
    	
    	date = LocalDate.now();
    	lblToday.setText(date.toString());
    	datepicker.setValue(date);
    }
    
   public void update()
   {
	   
   }
}
