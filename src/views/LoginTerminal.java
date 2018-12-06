package views;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginTerminal implements Initializable{
	/***********************************************
	 * Class Attributes:
	 */
	private Hub main;
	
	/***********************************************
	 * FXML Resources:
	 */
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private TextField txfName;
    @FXML private Label lblErrorMsg;
    @FXML private PasswordField psfPassword;
    @FXML private Button btnConfirm;
    @FXML private Button btnExit;
    @FXML private Button btnRegister;

    /***********************************************
	 * FXML Methods:
	 */
    @FXML
    void pressEnter(ActionEvent event) 
    {
    	confirmLogIn();
    }
    
    @FXML
    private void confirmLogIn() 
    {
    	String username;
    	String password;
    	
    	username = txfName.getText();
    	password = psfPassword.getText();
    	
    	if (username.equalsIgnoreCase("") || password.equalsIgnoreCase("")) 
    	{
    		lblErrorMsg.setText("Error: Empty text field(s).");
		}
    	else
    	{
    		main.openNewModule(username, password);
    	}
    }

    @FXML
    private void exit() 
    {
    	System.exit(0); 
    }
    
    /***********************************************
	 * Initialization:
	 */
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        assert txfName != null : "fx:id=\"txfName\" was not injected: check your FXML file 'LoginTerminal.fxml'.";
        assert lblErrorMsg != null : "fx:id=\"lblErrorMsg\" was not injected: check your FXML file 'LoginTerminal.fxml'.";
        assert psfPassword != null : "fx:id=\"psfPassword\" was not injected: check your FXML file 'LoginTerminal.fxml'.";
        assert btnConfirm != null : "fx:id=\"btnConfirm\" was not injected: check your FXML file 'LoginTerminal.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'LoginTerminal.fxml'.";
        assert btnRegister != null : "fx:id=\"btnRegister\" was not injected: check your FXML file 'LoginTerminal.fxml'.";
        
        lblErrorMsg.setText("");
    }
    
    /***********************************************
	 * Class Methods:
	 */    
    public void setHub(Hub main)
    {
    	this.main = main;
    }
    
    public void setErrorMsg(String text)
    {
    	lblErrorMsg.setText(text);
    }
}
