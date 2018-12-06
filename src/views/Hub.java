package views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import models.Models;
import models.Users;
import modules.Modules;

public class Hub 
{
	private LoginTerminal login;
	private Stage primaryStage;
	private Models model;
	private Modules module;
	private Scene loginScene;
	private Thread checker;
	private Checker stat;
	
	public Hub()
	{
		model = new Models();
	}
	
	public void setLogin(LoginTerminal login)
	{
		this.login = login;
		this.login.setHub(this);
	}
	
	public void setLoginScene(Scene scene)
	{
		this.loginScene = scene;
	}
	
	public void setPrimaryStage(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
		this.primaryStage.centerOnScreen();
	}
	
	public void openNewModule(String username, String password)
	{
		Users user;
		
		if(!model.checkLogin(username, password))
		{
			user = model.getUser(username, password);
			model.LogIn(user);
			
			switch(user.getUserType().toUpperCase())
			{
				case "ADMIN":
					try
					{
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/modules/AdminModule.fxml"));
				        Scene scene = new Scene(loader.load());
				        
				        module = loader.getController();
				        module.setUser(user);
				        module.setHub(this);
				        module.setModels(model);
				        module.setUp();
				        
				        primaryStage.hide();
				        primaryStage.setScene(scene);
				        primaryStage.setTitle("SWDESPA UDC: Admin Module");
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					break;
				case "DOCTOR":
					try
					{
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/modules/DoctorModule.fxml"));
				        Scene scene = new Scene(loader.load());
				        
				        module = loader.getController();
				        module.setUser(user);
				        module.setHub(this);
				        module.setModels(model);
				        module.setUp();
				        
				        primaryStage.hide();
				        primaryStage.setScene(scene);
				        primaryStage.setTitle("SWDESPA UDC: Doctor Module");
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					break;
				case "CLIENT":
					try
					{
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/modules/ClientModule.fxml"));
				        Scene scene = new Scene(loader.load());
				        
				        module = loader.getController();
				        module.setUser(user);
				        module.setHub(this);
				        module.setModels(model);
				        module.setUp();
				        
				        primaryStage.hide();
				        primaryStage.setScene(scene);
				        primaryStage.setTitle("SWDESPA UDC: Client Module");
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					break;
				case "SECRETARY":
					try
					{
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/modules/SecretaryModule.fxml"));
				        Scene scene = new Scene(loader.load());
				        
				        module = loader.getController();
				        module.setUser(user);
				        module.setHub(this);
				        module.setModels(model);
				        module.setUp();
				        
				        primaryStage.hide();
				        primaryStage.setScene(scene);
				        primaryStage.setTitle("SWDESPA UDC: Secretary Module");
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					break;
			}
			
			primaryStage.show();
	        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	        	@Override
	            public void handle(WindowEvent event) {
	                stop();
	            }
	        });
	        
	        checkUpdates();
		}
		else
		{
			login.setErrorMsg("Error: User is logged in.");
		}
	}
	
	public void logOut(Users user)
	{
		stopChecking();
		model.LogOut(user);
		primaryStage.hide();
		primaryStage.setScene(loginScene);
		primaryStage.setTitle("SWDESPA UDC");
		primaryStage.show();
	}
	
	public void stop()
	{
		if(module != null)
			model.LogOut(module.getUser());
	}
	
	public void checkUpdates()
	{
		stat = new Checker(this, model);
		checker = new Thread(stat);
		
		checker.start();
	}
	
	public void stopChecking()
	{
		stat.turnOff();
	}	
	
	public void startChecking()
	{
		stat.turnOn();
	}
	
	public void update()
	{
		module.update();
	}
	
	public Modules getModule()
	{
		return module;
	}
}
