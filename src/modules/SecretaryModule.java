package modules;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import controllers.SecretaryController;
import viewpanels.DailyAgendaView;
import viewpanels.DailyView;
import viewpanels.WeeklyAgendaView;
import viewpanels.WeeklyView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Doctor;
import models.Models;
import secretarytools.AddClient;
import secretarytools.ManageClients;

public class SecretaryModule extends Modules implements Initializable
{
	/***********************************************
	 * Class Attributes:
	 */
	private ObservableList<String> vtype = FXCollections.observableArrayList("Weekly", "Daily", "Daily Agenda", "Weekly Agenda");
	private ObservableList<String> ftype = FXCollections.observableArrayList("Show All", "Reservations", "Time Slots", "None");
	private ArrayList<Doctor> doctors = new ArrayList<Doctor>();
	private SecretaryController control;
	private AddClient addClient;
	private Stage addClientStage;
	private ManageClients manageClients;
	private Stage manageClientStage;
	
	/***********************************************
	 * FXML Resources:
	 */
    @FXML private Button btnLogout;
    @FXML private Button btnTakeSlot;
    @FXML private Button btnDefault;
    @FXML private TextArea txaNotif;
    @FXML private Button btnToday;
    @FXML private Label lblToday;
    @FXML private ChoiceBox<String> chbView;
    @FXML private DatePicker datepicker;
    @FXML private AnchorPane apnBoard;
    @FXML private Button btnMngApt;
    @FXML private Button btnRefresh;
    @FXML private ChoiceBox<String> chbDoctor;
    @FXML private ChoiceBox<String> chbFilter;
    @FXML private Label lblUser;
    @FXML private Button btnNotifDoc;
    @FXML private Button btnVerifyApt;
    @FXML private Button btnExit;
    
	/***********************************************
	 * FXML Methods:
	 */
    @FXML
    private void addAppointment() 
    {
    	try
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/secretarytools/AddClient.fxml"));
    		Scene scene;
    		scene = new Scene(loader.load());
    	
    		addClient = loader.getController();
    		addClient.setController(control);
    		addClient.setModels(model);
    		addClient.initiateListeners();
    		addClient.addChoices();
    		
    		
    		addClientStage = new Stage();
    		addClientStage.setScene(scene);
    		addClientStage.initStyle(StageStyle.UNDECORATED);
    		addClientStage.initModality(Modality.APPLICATION_MODAL);
    		addClientStage.show();
    		
    	}
    	catch (IOException e)
    	{
    		e.printStackTrace();
    	}
    }
    
    @FXML
    private void exit() 
    {
    	logout();
    }

    @FXML
    private void goToToday() 
    {
       	setDate(LocalDate.now());
    	panel.setDate(date);
    }

    @FXML
    private void logout() 
    {
    	main.logOut(user);
    }

    @FXML
    private void manageClients() 
    {
    	try
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/secretarytools/ManageClients.fxml"));
    		Scene scene;
    		scene = new Scene(loader.load());
    		
    		manageClients = loader.getController();
    		manageClients.setController(control);
    		
    		manageClientStage = new Stage();
    		manageClientStage.setScene(scene);
    		manageClientStage.initStyle(StageStyle.UNDECORATED);
    		manageClientStage.initModality(Modality.APPLICATION_MODAL);
    		manageClientStage.show();
    		
    	}
    	catch (IOException e)
    	{
    		e.printStackTrace();
    	}
    }

    @FXML
    private void notifyDoctor() 
    {
    	
    }

    @FXML
    private void refresh() 
    {
    	update();
    }

    @FXML
    private void useDefault()
    {
    	chbFilter.getSelectionModel().selectFirst();
		setFilter(chbFilter.getSelectionModel().getSelectedIndex());
		
		chbView.getSelectionModel().select(1);
        control.changeView(chbView.getValue());
        
        chbDoctor.getSelectionModel().selectFirst();
        setDoctorFilter(doctors.get(chbFilter.getSelectionModel().getSelectedIndex()));
        goToToday();
    }
    
    /***********************************************
	 * Initialization:
	 */
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        assert btnLogout != null : "fx:id=\"btnLogout\" was not injected: check your FXML file 'SecretaryModule.fxml'.";
        assert btnTakeSlot != null : "fx:id=\"btnTakeSlot\" was not injected: check your FXML file 'SecretaryModule.fxml'.";
        assert btnDefault != null : "fx:id=\"btnDefault\" was not injected: check your FXML file 'SecretaryModule.fxml'.";
        assert txaNotif != null : "fx:id=\"txaNotif\" was not injected: check your FXML file 'SecretaryModule.fxml'.";
        assert btnToday != null : "fx:id=\"btnToday\" was not injected: check your FXML file 'SecretaryModule.fxml'.";
        assert lblToday != null : "fx:id=\"lblToday\" was not injected: check your FXML file 'SecretaryModule.fxml'.";
        assert chbView != null : "fx:id=\"chbView\" was not injected: check your FXML file 'SecretaryModule.fxml'.";
        assert datepicker != null : "fx:id=\"datepicker\" was not injected: check your FXML file 'SecretaryModule.fxml'.";
        assert apnBoard != null : "fx:id=\"apnBoard\" was not injected: check your FXML file 'SecretaryModule.fxml'.";
        assert btnMngApt != null : "fx:id=\"btnMngApt\" was not injected: check your FXML file 'SecretaryModule.fxml'.";
        assert btnRefresh != null : "fx:id=\"btnRefresh\" was not injected: check your FXML file 'SecretaryModule.fxml'.";
        assert chbDoctor != null : "fx:id=\"chbDoctor\" was not injected: check your FXML file 'SecretaryModule.fxml'.";
        assert chbFilter != null : "fx:id=\"chbFilter\" was not injected: check your FXML file 'SecretaryModule.fxml'.";
        assert lblUser != null : "fx:id=\"lblUser\" was not injected: check your FXML file 'SecretaryModule.fxml'.";
        assert btnNotifDoc != null : "fx:id=\"btnNotifDoc\" was not injected: check your FXML file 'SecretaryModule.fxml'.";
        assert btnVerifyApt != null : "fx:id=\"btnVerifyApt\" was not injected: check your FXML file 'SecretaryModule.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'SecretaryModule.fxml'.";

        //StartUp:
        date = LocalDate.now();
        
        control = new SecretaryController();
        control.setModule(this);
        
        lblToday.setText(date.toString());
        datepicker.setValue(date);
        txaNotif.setEditable(false);
        txaNotif.setText("");
        
        txaNotif.setEditable(false);
    }
    
    /***********************************************
	 * Class Methods:
	 */
    @Override
    public void setModels(Models model)
    {
    	this.model = model;
    	control.setModels(model);
    }
    
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
		if(doctors.size() > 0)
			panel.setDoctorFilter(doctors.get(chbDoctor.getSelectionModel().getSelectedIndex()));
    }
    
    public void update()
    {
    	if(panel != null)
    	{
	    	panel.setDate(date);
    	}
    }
    
    public void setUp()
    {
    	lblUser.setText(user.getName());
    	control.setUser(user);
    	control.setModels(model);
    	
    	doctors = model.getDoctors();
    	
    	for(int i = 0; i < doctors.size(); i++)
    	{
    		chbDoctor.getItems().add(doctors.get(i).getName());
    	}
    	
    	//chbDoctorFilter ChangeListener:
    	chbDoctor.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
        	
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
				chbDoctor.setValue(chbDoctor.getItems().get((Integer) number2));
				setDoctorFilter(doctors.get((Integer) number2));
			}
        	
		});

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
        //control.changeView(chbView.getValue());
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
            update();
        });
        
        chbDoctor.getSelectionModel().selectFirst();
        control.changeView(chbView.getValue());
        setDoctorFilter(doctors.get(chbDoctor.getSelectionModel().getSelectedIndex()));
    	update();
        
    }
    
    public void setDoctorFilter(Doctor filter)
    {
    	if(panel != null)
    	{
    		panel.setDoctorFilter(filter);
    	}
    }
}
