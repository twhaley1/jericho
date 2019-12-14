package application;
	
import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.jericho.model.settings.Setting;
import com.jericho.model.settings.SettingReader;
import com.jericho.model.settings.SettingSaver;
import com.jericho.view.MainPageCodeBehind;
import com.jericho.viewmodel.ViewModel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

/**
 * Starts the application.
 * 
 * @author thomaswhaley
 *
 */
public class Main extends Application {
	
	public static final String APPLICATION_TITLE = "Jericho";
	public static final int DEFAULT_TEXT_SPEED = 75;
	public static final int DEFAULT_FONT_SIZE = 13;
	
	private ViewModel viewModel;
	
	public Main() {
		this.viewModel = new ViewModel();
		this.loadSettings();
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			URL mainPageFXML = this.getClass().getResource("../com/jericho/view/MainPage.fxml");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(mainPageFXML);
			Pane root = loader.load();
			
			MainPageCodeBehind controller = (MainPageCodeBehind) loader.getController();
			controller.setViewModel(this.viewModel);
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle(APPLICATION_TITLE);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() throws Exception {
		// TODO: Can add prompt that asks if the user wants to save any new changes to settings.

		this.saveSettings();
		super.stop();
	}
	
	private void loadSettings() {
		File saveDirectory = new File("usersettings");
    	if (!saveDirectory.exists()) {
    		saveDirectory.mkdir();
    	}
    	
    	File saveFile = new File(saveDirectory.getAbsolutePath() + File.separator + "jericho_save.ser");
    	try {
			this.viewModel.settingsProperty().setValue(SettingReader.readSetting(saveFile));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			this.viewModel.settingsProperty().setValue(new Setting(Font.getDefault().getFamily(), DEFAULT_TEXT_SPEED, DEFAULT_FONT_SIZE));
		}
	}
	
	private void saveSettings() {
		File saveFile = new File("usersettings" + File.separator + "jericho_save.ser");
    	
    	try {
    		saveFile.createNewFile();
			SettingSaver.save(saveFile, this.viewModel.settingsProperty().get());
		} catch (IOException e) {
			System.err.println("Could not save settings.");
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("An error occurred.");
			alert.setHeaderText("Settings could not be saved.");
			alert.setContentText("Try running the application as administrator and trying again.");
			alert.showAndWait();
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
