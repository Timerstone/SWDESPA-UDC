package clienttools;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controllers.ClientController;
import controllers.DoctorController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import models.Models;
import models.Slots;
import models.Users;

public class CancelReservation implements Initializable
{
	/***********************************************
	 * Class Attributes:
	 */
	private ObservableList<String> time	= FXCollections.observableArrayList();
	private ArrayList<Slots> list;
	private Slots selectedSlot;
	private ClientController control;
	private Models model;
	private LocalDate date;
	private Users user;

	@Override
    public void initialize(URL location, ResourceBundle resources) 
    {
	 
    }
	
	public void setController(ClientController control)
	{
		this.control = control;
	}
	
	public void setModels(Models model)
    {
    	this.model = model;
    }
	
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	
	
}