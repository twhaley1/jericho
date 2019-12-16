package com.jericho.view.viewtransitions;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.jericho.viewmodel.AbstractViewController;
import com.jericho.viewmodel.ViewModel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * A class that can transition the application to different pages/scenes.
 * 
 * @author thomaswhaley
 *
 */
public final class PageLoader {

	private static final String SETTINGS_PAGE = ".." + File.separator + "SettingsPage.fxml";
	private static final String MAIN_PAGE = ".." + File.separator + "MainPage.fxml";
	
	private static final String CURRENT_PANE_NULL = "currentPane can not be null.";
	
	private URL settingsPageUrl;
	private URL mainPageUrl;
	private Pane currentPane;
	private ViewModel viewModel;
	
	/**
	 * Creates a new PageLoader with the specified pane.
	 * 
	 * @precondition currentPane != null && viewModel != null
	 * 
	 * @param currentPane the pageloader's current pane.
	 * @param viewModel the view model to give to code behind controllers.
	 */
	public PageLoader(Pane currentPane, ViewModel viewModel) {
		if (currentPane == null) {
			throw new IllegalArgumentException(CURRENT_PANE_NULL);
		}
		if (viewModel == null) {
    		throw new IllegalArgumentException();
    	}
    	
    	this.viewModel = viewModel;
		this.currentPane = currentPane;
		this.settingsPageUrl = this.getClass().getResource(SETTINGS_PAGE);
		this.mainPageUrl = this.getClass().getResource(MAIN_PAGE);
	}
	
	/**
	 * Transitions the application to the loading page.
	 */
	public void changeToSettingsPage() {
		try {
	   		SceneChanger.changeScene(this.settingsPageUrl, this.currentPane, this.viewModel);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Transitions the application to the main page.
	 */
	public void changeToMainPage() {
		try {
			Scene scene = SceneChanger.changeScene(this.mainPageUrl, this.currentPane, this.viewModel);
			File cssFile = new File("src" + File.separator + "com" + File.separator + "jericho" + File.separator + "view" + File.separator + "MainPage.css");
			
			String stylesheet = cssFile.toURI().toURL().toString();
			scene.getStylesheets().add(stylesheet);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Closes the Stage that contains this current pane.
	 */
	public void close() {
		SceneChanger.exit(this.currentPane);
	}
	
	private static class SceneChanger {
		
		/**
		 * Changes the scene's root in the currentPane's Stage to the Pane
		 * loaded from the specified fxml.
		 * 
		 * @param fxml the fxml to derive the Pane from.
		 * @param currentPane the current pane.
		 * @throws IOException if an error occurs during loading.
		 */
		public static Scene changeScene(URL fxml, Pane currentPane, ViewModel viewModel) throws IOException {
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(fxml);
	    	Pane loadingPane = loader.load();
	    	AbstractViewController controller = (AbstractViewController) loader.getController();
	    	controller.setViewModel(viewModel);
	    	
	   		Stage currentStage = (Stage) currentPane.getScene().getWindow();
	   		currentStage.getScene().setRoot(loadingPane);
	   		
	   		return currentStage.getScene();
		}
		
		
		/**
		 * Closes the Stage that contains currentPane
		 * 
		 * @param currentPane the pane contained in the desired stage.
		 */
		public static void exit(Pane currentPane) {
			Stage stage = (Stage) currentPane.getScene().getWindow();
			stage.close();
		}
	}
}
