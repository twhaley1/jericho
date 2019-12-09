package application;
	
import java.net.URL;

import com.jericho.view.MainPageCodeBehind;
import com.jericho.viewmodel.ViewModel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Starts the application.
 * 
 * @author thomaswhaley
 *
 */
public class Main extends Application {
	
	public static final String APPLICATION_TITLE = "Jericho";
	
	@Override
	public void start(Stage primaryStage) {
		try {
			URL mainPageFXML = this.getClass().getResource("../com/jericho/view/MainPage.fxml");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(mainPageFXML);
			Pane root = loader.load();
			MainPageCodeBehind controller = (MainPageCodeBehind) loader.getController();
			controller.setViewModel(new ViewModel());
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle(APPLICATION_TITLE);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Launches the application.
	 * 
	 * @param args console arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
