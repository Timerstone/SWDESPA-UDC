package modules;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import clienttools.CancelReservation;
import clienttools.TakeSlot;
import controllers.ClientController;
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
import models.Doctor;
import models.Models;
import viewpanels.DailyAgendaView;
import viewpanels.DailyView;
import viewpanels.WeeklyAgendaView;
import viewpanels.WeeklyView;

public class ClientModule extends Modules implements Initializable
{
	/***********************************************
	 * Class Attributes:
	 */
	private ObservableList<String> vtype = FXCollections.observableArrayList("Weekly", "Daily", "Daily Agenda", "Weekly Agenda");
	private ObservableList<String> ftype = FXCollections.observableArrayList("Show All", "Time Slots", "Appointments");
	private ArrayList<Doctor> doctors = new ArrayList<Doctor>(); 
	private ClientController control;
	private TakeSlot takeSlot;
	private CancelReservation cancelRes;
	private Stage takeStage;
	private Stage cancelResStage;
	
	/***********************************************
	 * FXML Resources:
	 */
	@FXML private ResourceBundle resources;
    @FXML private URL location;
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
    @FXML private Button btnExit;
    
    /***********************************************
	 * FXML Methods:
	 */
    @FXML
    private void logout()
    {
    	main.logOut(user);
    }

    @FXML
    private void goToToday() 
    {
    	setDate(LocalDate.now());
    	panel.setDate(date);
    }

    @FXML
    private void bookAppointment() 
    {
    	try 
    	{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/clienttools/TakeSlot.fxml"));
			Scene scene;
			scene = new Scene(loader.load());
			
			takeSlot = loader.getController();
			takeSlot.setController(control);
			takeSlot.setModels(model);
			takeSlot.initiateListeners();
			takeSlot.setUser(user);
			
			takeStage = new Stage();
			takeStage.setScene(scene);
			takeStage.initStyle(StageStyle.UNDECORATED);
			takeStage.initModality(Modality.APPLICATION_MODAL);
			takeStage.show();
		} 
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
    }

    @FXML
    private void refresh() 
    {
    	update();
    }

    @FXML
    private void cancelAppointment() 
    {
    	try 
    	{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/clienttools/CancelReservation.fxml"));
			Scene scene;
			scene = new Scene(loader.load());
			
			cancelRes = loader.getController();
			cancelRes.setController(control);
			cancelRes.setModels(model);
//			cancelRes.initiateListeners();
			cancelRes.setUser(user);
			
			cancelResStage = new Stage();
			cancelResStage.setScene(scene);
			cancelResStage.initStyle(StageStyle.UNDECORATED);
			cancelResStage.initModality(Modality.APPLICATION_MODAL);
			cancelResStage.show();
		} 
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
    }
    
    @FXML
    private void editAppointment()
    {
    	
    }
    
    @FXML
    private void userDefault()
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
        assert btnLogout != null : "fx:id=\"btnLogout\" was not injected: check your FXML file 'ClientModule.fxml'.";
        assert btnTakeSlot != null : "fx:id=\"btnTakeSlot\" was not injected: check your FXML file 'ClientModule.fxml'.";
        assert btnDefault != null : "fx:id=\"btnDefault\" was not injected: check your FXML file 'ClientModule.fxml'.";
        assert txaNotif != null : "fx:id=\"txaNotif\" was not injected: check your FXML file 'ClientModule.fxml'.";
        assert btnToday != null : "fx:id=\"btnToday\" was not injected: check your FXML file 'ClientModule.fxml'.";
        assert lblToday != null : "fx:id=\"lblToday\" was not injected: check your FXML file 'ClientModule.fxml'.";
        assert chbView != null : "fx:id=\"chbView\" was not injected: check your FXML file 'ClientModule.fxml'.";
        assert datepicker != null : "fx:id=\"datepicker\" was not injected: check your FXML file 'ClientModule.fxml'.";
        assert apnBoard != null : "fx:id=\"apnBoard\" was not injected: check your FXML file 'ClientModule.fxml'.";
        assert btnMngApt != null : "fx:id=\"btnMngApt\" was not injected: check your FXML file 'ClientModule.fxml'.";
        assert btnRefresh != null : "fx:id=\"btnRefresh\" was not injected: check your FXML file 'ClientModule.fxml'.";
        assert chbDoctor != null : "fx:id=\"chbDoctor\" was not injected: check your FXML file 'ClientModule.fxml'.";
        assert chbFilter != null : "fx:id=\"chbFilter\" was not injected: check your FXML file 'ClientModule.fxml'.";
        assert lblUser != null : "fx:id=\"lblUser\" was not injected: check your FXML file 'ClientModule.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'ClientModule.fxml'.";

    }
    
    /***********************************************
   	 * Class Methods:
   	 */   
    public void setDate(LocalDate date)
    {
        this.date = date;
        lblToday.setText(date.toString());
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
		setDoctorFilter(doctors.get(chbDoctor.getSelectionModel().getSelectedIndex()));
    }
    
    public void update()
    {
    	if(panel != null)
    	{
	    	panel.setDate(date);
    	}
    	    	
    }
    
    public void setModels(Models model)
    {
    	this.model = model;
    }
    
    public void setUp()
    {
    	control = new ClientController();
        control.setModule(this);
        
        setDate(LocalDate.now());
        
    	lblUser.setText(user.getName());
    	control.setUser(user);
    	control.setModels(model);

    	doctors = model.getDoctors();
    	for(int i = 0; i < doctors.size(); i++)
    	{
    		chbDoctor.getItems().add(doctors.get(i).getName());
    	}
    	chbDoctor.getSelectionModel().selectFirst();
    	
    	chbDoctor.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				chbDoctor.setValue(chbDoctor.getItems().get((Integer) arg2));
				for(int i = 0; i < doctors.size(); i++)
				{
					if(chbDoctor.getValue().equalsIgnoreCase(doctors.get(i).getName()))
					{
						setDoctorFilter(doctors.get(i));
					}
				}
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
               
        control.changeView(chbView.getValue());
        setDoctorFilter(doctors.get(chbDoctor.getSelectionModel().getSelectedIndex()));
    }
}
