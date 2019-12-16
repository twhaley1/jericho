package com.jericho.view;

import com.jericho.view.viewtransitions.PageLoader;
import com.jericho.viewmodel.AbstractViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class AboutPageCodeBehind extends AbstractViewController {

	@FXML
	private AnchorPane pane;
	
	@FXML
    private Label titleLabel;

    @FXML
    private Label contentLabel;
    
    @FXML
    private void initialize() {
    	this.contentLabel.maxWidthProperty().bind(this.pane.widthProperty());
    	
    	this.titleLabel.setText("Jericho, Enhance Your Reading Ability.");
    	
    	this.contentLabel.setText("Choose any text you like, and start training yourself to reach a better standard of reading comprehension." + System.lineSeparator() + System.lineSeparator()
    			+ "Even if your goal isn't to get faster at reading, reading with Jericho offers a new experience. No longer does a reader have to be threatened by the"
    			+ "amount of content there is left. Jericho allows the reader to continue at their own pace. Enjoy.");
    }
    
    @FXML
    private void onBackButtonAction(ActionEvent event) {
    	PageLoader loader = new PageLoader(this.pane, this.getViewModel());
    	loader.changeToMainPage();
    }
}
