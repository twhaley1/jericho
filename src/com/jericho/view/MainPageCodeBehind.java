package com.jericho.view;

import java.io.File;

import com.jericho.model.MVVM;
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
public class MainPageCodeBehind implements MVVM {
	
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

    private ViewModel viewModel;
    
    @FXML
    private void initialize() {
    	this.setComponentBindings();
    }
    
    @Override
    public void setViewModel(ViewModel viewModel) {
    	if (viewModel == null) {
    		throw new IllegalArgumentException();
    	}
    	
    	this.viewModel = viewModel;
    	this.setViewModelBindings();
    }
    
    @FXML
    private void onClearMenuItemAction(ActionEvent event) {
    	this.viewModel.clearContents();
    }
    
    @FXML
    private void onCloseMenuItemAction(ActionEvent event) {
    	PageLoader uiLoader = new PageLoader(this.pane, this.viewModel);
    	uiLoader.close();
    }

    @FXML
    private void onOpenMenuItemAction(ActionEvent event) {
    	Window currentWindow = this.pane.getScene().getWindow();
    	UserTextFileSelection selector = new UserTextFileSelection(currentWindow);
    	File selectedFile = selector.selectFile();
    	
    	if (selectedFile != null) {	
    		this.viewModel.loadTextFromFile(selectedFile);
    	}	
    }

    @FXML
    private void onPauseButtonAction(ActionEvent event) {
    	this.viewModel.pauseIncreasingContents();
    }

    @FXML
    private void onPlayButtonAction(ActionEvent event) {
    	this.viewModel.iterativelyIncreaseContents();
    }
    
    private void setViewModelBindings() {
    	this.textLabel.textProperty().bind(this.viewModel.contentsProperty());
    	this.loadingProgressBar.progressProperty().bindBidirectional(this.viewModel.progressProperty());
    	this.playButton.disableProperty().bind(this.viewModel.isLoadingProperty().
    			or(this.viewModel.isPlayingProperty()).
    			or(this.viewModel.contentsProperty().isNull()));
    	this.loadingProgressBar.visibleProperty().bind(this.viewModel.isLoadingProperty());
    	this.pauseButton.disableProperty().bind(this.viewModel.isLoadingProperty().
    			or(this.viewModel.isPausedProperty()).
    			or(this.viewModel.isPlayingProperty().not()));
    	this.clearMenuItem.disableProperty().bind(this.viewModel.contentsProperty().isNull());
    }
    
    private void setComponentBindings() {
    	this.textScrollPane.vvalueProperty().bind(this.textLabel.heightProperty());
    }
    
}
