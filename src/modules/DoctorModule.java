package modules;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import controllers.DoctorController;
import doctortools.AddSlot;
import doctortools.EditSlot;
import doctortools.RemoveSlot;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import viewpanels.DailyAgendaView;
import viewpanels.DailyView;
import viewpanels.WeeklyAgendaView;
import viewpanels.WeeklyView;

public class DoctorModule extends Modules implements Initializable
{
	/***********************************************
	 * Class Attributes:
	 */
	private ObservableList<String> vtype = FXCollections.observableArrayList("Weekly", "Daily", "Daily Agenda", "Weekly Agenda");
	private ObservableList<String> ftype = FXCollections.observableArrayList("Show All", "Reservations", "Time Slots");
	private DoctorController control;
	private Stage editSlots;
	private Stage addSlots;
	private Stage removeSlots;
	private AddSlot add;
	private EditSlot edit;
	private RemoveSlot remove;
	
	/***********************************************
	 * FXML Resources:
	 */
    @FXML private AnchorPane apnBoard;
    @FXML private Button btnLogOut;
    @FXML private Button btnEdit;
    @FXML private Button btnDefault;
    @FXML private Button btnRemove;
    @FXML private Button btnExit;    
    @FXML private Button btnToday;
    @FXML private Button btnAdd;
    @FXML private ChoiceBox<String> chbView;
    @FXML private ChoiceBox<String> chbFilter;
    @FXML private DatePicker datepicker;
    @FXML private Label lblToday;
    @FXML private Label lblUser;
    @FXML private TextArea txaNotif;
    
    /***********************************************
	 * FXML Methods:
	 */    
    @FXML
    private void logOut()
    {
    	main.logOut(user);
    }
    
    @FXML
    private void addSlot()
    {
    	try 
    	{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/doctortools/AddSlot.fxml"));
			Scene scene;
			scene = new Scene(loader.load());
			
			add = loader.getController();
			add.setController(control);
			
			addSlots = new Stage();
			addSlots.setScene(scene);
			addSlots.initStyle(StageStyle.UNDECORATED);
			addSlots.initModality(Modality.APPLICATION_MODAL);
			addSlots.show();
		} 
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
    }
    
    @FXML
    private void editSlot()
    {
    	try
    	{
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/doctortools/EditSlot.fxml"));
			Scene scene;
			scene = new Scene(loader.load());
			
			edit = loader.getController();
			edit.setController(control);
			edit.setUser(user);
			edit.setModels(model);
			edit.initiateListeners();
			
			editSlots = new Stage();
			editSlots.setScene(scene);
			editSlots.initStyle(StageStyle.UNDECORATED);
			editSlots.initModality(Modality.APPLICATION_MODAL);
			editSlots.show();
	    } 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
    }
    
    @FXML
    private void removeSlot()
    {
    	try 
    	{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/doctortools/RemoveSlot.fxml"));
			Scene scene;
			scene = new Scene(loader.load());
			
			remove = loader.getController();
			remove.setController(control);
			remove.setUser(user);
			remove.setModels(model);
			remove.initiateListeners();
			
			removeSlots = new Stage();
			removeSlots.setScene(scene);
			removeSlots.initStyle(StageStyle.UNDECORATED);
			removeSlots.initModality(Modality.APPLICATION_MODAL);
			removeSlots.show();
		} 
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
    }
    
    @FXML
    private void goToToday()
    {
    	setDate(LocalDate.now());
    	panel.setDate(date);
    }
    
    @FXML
    private void useDefault()
    {
    	chbFilter.getSelectionModel().selectFirst();
		setFilter(chbFilter.getSelectionModel().getSelectedIndex());
		
		chbView.getSelectionModel().select(1);
        control.changeView(chbView.getValue());
        
        goToToday();
    }
    
    /***********************************************
	 * Initialization:
	 */
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        assert btnLogOut != null : "fx:id=\"btnLogOut\" was not injected: check your FXML file 'DoctorModule.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'DoctorModule.fxml'.";
        assert btnDefault != null : "fx:id=\"btnDefault\" was not injected: check your FXML file 'DoctorModule.fxml'.";
        assert txaNotif != null : "fx:id=\"txaNotif\" was not injected: check your FXML file 'DoctorModule.fxml'.";
        assert btnToday != null : "fx:id=\"btnToday\" was not injected: check your FXML file 'DoctorModule.fxml'.";
        assert lblToday != null : "fx:id=\"lblToday\" was not injected: check your FXML file 'DoctorModule.fxml'.";
        assert chbView != null : "fx:id=\"chbView\" was not injected: check your FXML file 'DoctorModule.fxml'.";
        assert datepicker != null : "fx:id=\"datepicker\" was not injected: check your FXML file 'DoctorModule.fxml'.";
        assert apnBoard != null : "fx:id=\"apnBoard\" was not injected: check your FXML file 'DoctorModule.fxml'.";
        assert chbFilter != null : "fx:id=\"chbFilter\" was not injected: check your FXML file 'DoctorModule.fxml'.";
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'DoctorModule.fxml'.";
        assert lblUser != null : "fx:id=\"lblUser\" was not injected: check your FXML file 'DoctorModule.fxml'.";
        assert btnRemove != null : "fx:id=\"btnRemove\" was not injected: check your FXML file 'DoctorModule.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'DoctorModule.fxml'.";

        //Startup:
        date = LocalDate.now();
        
        control = new DoctorController();
        
        lblToday.setText(date.toString());
        datepicker.setValue(date);
        txaNotif.setEditable(false);
        txaNotif.setText("");
    }
    
    /***********************************************
   	 * Class Methods:
   	 */ 
    public void setDate(LocalDate date)
    {
    	this.date = date;
    	datepicker.setValue(date);
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
    	control.setUser(user);
    	control.setModels(model);
    	control.setModule(this);
    	
    	//chbFilter ChangeListener:
        chbFilter.setItems(ftype);
        chbFilter.getSelectionModel().selectFirst();
        chbFilter.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
        	@Override
        	public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
        		chbFilter.setValue(chbFilter.getItems().get((Integer) number2));
        		setFilter((Integer) number2);
        	}
        });
        
        //chbView ChangeListener:
        chbView.setItems(vtype);
        chbView.getSelectionModel().select(1);
        chbView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
              chbView.setValue(chbView.getItems().get((Integer) number2));
              control.changeView(chbView.getValue());
            }
          });
        
        //datepicker ChangeListerner:
        datepicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            date = newValue;
            setDate(date);
            update();
        });
        
        control.changeView(chbView.getValue());
    }
    
    public void update()
    {
    	if(panel != null)
    	{
	    	panel.setDate(date);
    	}
    }
}
