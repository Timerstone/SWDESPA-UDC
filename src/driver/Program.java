package driver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.Hub;

public class Program extends Application
{
	/**
	 * Starts the primary stage (Login Terminal).
	 * Intitiates the Hub (Observer).
	 * The rest are initiated inside the Hub.
	 */
	@Override
    public void start(Stage primaryStage) throws Exception 
	{
		Hub main = new Hub();
		
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/LoginTerminal.fxml"));
        Scene scene = new Scene(loader.load());
        
        
        main.setLoginScene(scene);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("SWDESPA UDC");
        
        main.setLogin(loader.getController());
        main.setPrimaryStage(primaryStage);
    }
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
