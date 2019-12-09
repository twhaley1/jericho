package com.jericho.view;

import java.io.File;
import java.io.IOException;

import com.jericho.model.Setting;
import com.jericho.model.SettingSaver;
import com.jericho.view.viewtransitions.PageLoader;
import com.jericho.viewmodel.AbstractViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

/**
 * Save page for SavePage.fxml
 * 
 * @author thomaswhaley
 *
 */
public class SavePageCodeBehind extends AbstractViewController {

	@FXML
	private AnchorPane pane;
	
	@FXML
    private Slider textSpeedSlider;

    @FXML
    private ComboBox<String> fontComboBox;

    @FXML
    private Button saveButton;

    @FXML
    private void initialize() {
    	this.setUpFontComboBox();
    }
    
    @FXML
    private void onSaveButtonAction(ActionEvent event) {
    	String selectedFont = this.fontComboBox.getSelectionModel().getSelectedItem();
    	int sliderSpeed = (int) this.textSpeedSlider.getValue();
    	Setting setting = new Setting(selectedFont, sliderSpeed);
    	
    	String path = this.getClass().getResource("../settings").getPath();
    	File saveFile = new File(path + File.separator + "jericho_save.ser");
    	
    	SettingSaver saver = new SettingSaver(setting);
    	
    	try {
    		saveFile.createNewFile();
			saver.save(saveFile);
		} catch (IOException e) {
			System.err.println("Could not save settings.");
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("An error occurred.");
			alert.setHeaderText("Settings could not be saved.");
			alert.setContentText("Try running the application as administrator and trying again.");
			alert.showAndWait();
		}
    	
    	PageLoader loader = new PageLoader(this.pane, this.getViewModel());
    	loader.changeToMainPage();
    }
    
    private void setUpFontComboBox() {
    	this.fontComboBox.setValue(Font.getDefault().getFamily());
    	this.fontComboBox.getItems().setAll(Font.getFamilies());
    }

}