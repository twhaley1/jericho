package com.jericho.view;

import com.jericho.model.SpeedAdjuster;
import com.jericho.model.settings.Setting;
import com.jericho.view.viewtransitions.PageLoader;
import com.jericho.viewmodel.AbstractViewController;
import com.jericho.viewmodel.ViewModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    
    @Override
    public void setViewModel(ViewModel viewModel) {
    	super.setViewModel(viewModel);
    	
    	SpeedAdjuster adjuster = new SpeedAdjuster(1, 100);
    	this.textSpeedSlider.setValue(adjuster.adjust(this.getViewModel().speedProperty().get()));
    	this.fontComboBox.getSelectionModel().select(this.getViewModel().fontProperty().get().getFamily());
    }
    
    @FXML
    private void onSaveButtonAction(ActionEvent event) {
    	String selectedFont = this.fontComboBox.getSelectionModel().getSelectedItem();
    	int sliderSpeed = (int) this.textSpeedSlider.getValue();
    	Setting setting = new Setting(selectedFont, sliderSpeed);
    	
    	this.getViewModel().settingsProperty().setValue(setting);
    	
    	PageLoader loader = new PageLoader(this.pane, this.getViewModel());
    	loader.changeToMainPage();
    }
    
    private void setUpFontComboBox() {
    	this.fontComboBox.setValue(Font.getDefault().getFamily());
    	this.fontComboBox.getItems().setAll(Font.getFamilies());
    }

}
