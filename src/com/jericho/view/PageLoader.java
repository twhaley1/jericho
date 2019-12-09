package com.jericho.view;

import java.io.IOException;
import java.net.URL;

import com.jericho.model.MVVM;
import com.jericho.viewmodel.ViewModel;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * A class that can transition the application to different pages.
 * 
 * @author thomaswhaley
 *
 */
public final class PageLoader {

	private static final String LOADING_PAGE = "LoadingPage.fxml";
	private static final String MAIN_PAGE = "MainPage.fxml";
	
	private static final String CURRENT_PANE_NULL = "currentPane can not be null.";
	
	private URL loadingPageUrl;
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
		this.loadingPageUrl = this.getClass().getResource(LOADING_PAGE);
		this.mainPageUrl = this.getClass().getResource(MAIN_PAGE);
	}
	
	/**
	 * Transitions the application to the loading page.
	 */
	public void changeToLoadingPage() {
		try {
			SceneChanger.changeScene(this.loadingPageUrl, this.currentPane, this.viewModel);	
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Transitions the application to the main page.
	 */
	public void changeToMainPage() {
		try {
			SceneChanger.changeScene(this.mainPageUrl, this.currentPane, this.viewModel);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
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
		public static void changeScene(URL fxml, Pane currentPane, ViewModel viewModel) throws IOException {
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(fxml);
	    	Pane loadingPane = loader.load();
	    	MVVM controller = (MVVM) loader.getController();
	    	controller.setViewModel(viewModel);
	    	
	   		Stage currentStage = (Stage) currentPane.getScene().getWindow();
	   		currentStage.getScene().setRoot(loadingPane);
		}
	}
}