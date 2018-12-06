package views;

import modules.Modules;
import javafx.fxml.FXMLLoader;

public class ViewsNavigator {
	public static final String DAILYAGENDA = "/viewpanels/DailyAgendaView.fxml";
	public static final String WEEKLYAGENDA = "/viewpanels/WeeklyAgendaView.fxml";
	public static final String DAILY = "/viewpanels/DailyView.fxml";
	public static final String WEEKLY = "/viewpanels/WeeklyView.fxml";
	public static final String FINISHED = "/viewpanels/FinishedView.fxml";
	public static final String UPCOMING = "/viewpanels/UpcomingView.fxml";
	
	public static void loadView(String fxml, Modules module)
	{
		try 
		{
			module.setView(FXMLLoader.load(ViewsNavigator.class.getResource(fxml)));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}