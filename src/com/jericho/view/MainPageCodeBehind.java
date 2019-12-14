package com.jericho.view;

import java.io.File;

import com.jericho.view.fileselection.UserTextFileSelection;
import com.jericho.view.viewtransitions.PageLoader;
import com.jericho.viewmodel.AbstractViewController;
import com.jericho.viewmodel.ViewModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;

/**
 * Code Behind for MainPage.fxml
 * 
 * @author thomaswhaley
 *
 */
public class MainPageCodeBehind extends AbstractViewController {
	
	@FXML
    private BorderPane pane;
	
	@FXML
	private MenuItem clearMenuItem;
	
	@FXML
	private ScrollPane textScrollPane;

    @FXML
    private Button playButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Label textLabel;
    
    @FXML
    private ProgressBar loadingProgressBar;
    
    @FXML
    private void initialize() {
    	this.setComponentBindings();
    }
    
    @Override
    public void setViewModel(ViewModel viewModel) {
    	super.setViewModel(viewModel);
    	
    	this.setViewModelBindings();
    }
    
    @FXML
    private void onSettingsMenuItemAction(ActionEvent event) {
    	PageLoader loader = new PageLoader(this.pane, this.getViewModel());
    	loader.changeToSettingsPage();
    }
    
    @FXML
    private void onClearMenuItemAction(ActionEvent event) {
    	this.getViewModel().clearContents();
    }
    
    @FXML
    private void onCloseMenuItemAction(ActionEvent event) {
    	PageLoader loader = new PageLoader(this.pane, this.getViewModel());
    	loader.close();
    }

    @FXML
    private void onOpenMenuItemAction(ActionEvent event) {
    	Window currentWindow = this.pane.getScene().getWindow();
    	UserTextFileSelection selector = new UserTextFileSelection(currentWindow);
    	File selectedFile = selector.selectFile();
    	
    	if (selectedFile != null) {	
    		this.getViewModel().loadTextFromFile(selectedFile);
    	}	
    }

    @FXML
    private void onPauseButtonAction(ActionEvent event) {
    	this.getViewModel().pauseIncreasingContents();
    }

    @FXML
    private void onPlayButtonAction(ActionEvent event) {
    	this.getViewModel().startPlaying();
    }
    
    private void setViewModelBindings() {
    	this.textLabel.textProperty().bind(this.getViewModel().contentsProperty());
    	this.loadingProgressBar.progressProperty().bindBidirectional(this.getViewModel().progressProperty());
    	this.playButton.disableProperty().bind(this.getViewModel().isLoadingProperty().
    			or(this.getViewModel().isPlayingProperty()).
    			or(this.getViewModel().contentsProperty().isNull()));
    	this.loadingProgressBar.visibleProperty().bind(this.getViewModel().isLoadingProperty());
    	this.pauseButton.disableProperty().bind(this.getViewModel().isLoadingProperty().
    			or(this.getViewModel().isPausedProperty()).
    			or(this.getViewModel().isPlayingProperty().not()));
    	this.clearMenuItem.disableProperty().bind(this.getViewModel().contentsProperty().isNull());
    	this.textLabel.fontProperty().bind(this.getViewModel().fontProperty());
    	this.textLabel.textFillProperty().bind(this.getViewModel().fontColorProperty());
    }
    
    private void setComponentBindings() {
    	this.textScrollPane.vvalueProperty().bind(this.textLabel.heightProperty());
    }
    
}
