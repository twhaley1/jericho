package com.jericho.view;

import com.jericho.model.SpeedAdjuster;
import com.jericho.model.settings.Setting;
import com.jericho.view.viewtransitions.PageLoader;
import com.jericho.viewmodel.AbstractViewController;
import com.jericho.viewmodel.ViewModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Save page for SavePage.fxml
 * 
 * @author thomaswhaley
 *
 */
public class SavePageCodeBehind extends AbstractViewController {

	private static final int MIN_FONT_SIZE = 8;
	private static final int MAX_FONT_SIZE = 48;
	
	@FXML
	private AnchorPane pane;
	
	@FXML
    private Slider textSpeedSlider;

    @FXML
    private ComboBox<String> fontComboBox;
    
    @FXML
    private Spinner<Integer> fontSizeSpinner;
    
    @FXML
    private ColorPicker fontColorPicker;
    
    @FXML
    private ColorPicker backgroundColorPicker;

    @FXML
    private Button saveButton;

    @FXML
    private void initialize() {
    	this.setUpFontComboBox();
    	this.fontSizeSpinner.setValueFactory(new BoundIntegerValueFactory(MIN_FONT_SIZE, MAX_FONT_SIZE));
    }
    
    @Override
    public void setViewModel(ViewModel viewModel) {
    	super.setViewModel(viewModel);
    	
    	SpeedAdjuster adjuster = new SpeedAdjuster(1, 100);
    	this.textSpeedSlider.setValue(adjuster.adjust(this.getViewModel().speedProperty().get()));
    	this.fontComboBox.getSelectionModel().select(this.getViewModel().fontProperty().get().getFamily());
    	this.fontSizeSpinner.getValueFactory().setValue((int) this.getViewModel().fontProperty().get().getSize());
    	this.fontColorPicker.setValue(this.getViewModel().fontColorProperty().get());
    	
    	Color currentBackgroundColor = (Color) this.getViewModel().backgroundProperty().get().getFills().get(0).getFill();
    	this.backgroundColorPicker.setValue(currentBackgroundColor);
    }
    
    @FXML
    private void onSaveButtonAction(ActionEvent event) {
    	String selectedFont = this.fontComboBox.getSelectionModel().getSelectedItem();
    	int sliderSpeed = (int) this.textSpeedSlider.getValue();
    	int fontSize = this.fontSizeSpinner.getValue();
    	Color fontColor = this.fontColorPicker.getValue();
    	Color backgroundColor = this.backgroundColorPicker.getValue();
    	Setting setting = new Setting(selectedFont, sliderSpeed, fontSize, fontColor, backgroundColor);
    	
    	this.getViewModel().settingsProperty().setValue(setting);
    	
    	PageLoader loader = new PageLoader(this.pane, this.getViewModel());
    	loader.changeToMainPage();
    }
    
    private void setUpFontComboBox() {
    	this.fontComboBox.setValue(Font.getDefault().getFamily());
    	this.fontComboBox.getItems().setAll(Font.getFamilies());
    }
    
    private class BoundIntegerValueFactory extends SpinnerValueFactory<Integer> {

    	private int lower;
    	private int upper;
    	
    	public BoundIntegerValueFactory(int arg0, int arg1) {
    		if (arg0 == arg1) {
    			throw new IllegalArgumentException();
    		}
    		
    		this.lower = arg0 > arg1 ? arg1 : arg0;
    		this.upper = arg0 > arg1 ? arg0 : arg1;
    		this.setValue(this.lower);
    	}
    	
		@Override
		public void decrement(int numberOfDecrements) {
			for (int i = 0; i < numberOfDecrements; i++) {
				if (this.getValue() > this.lower) {
					this.setValue(this.getValue() - 1);
				}
			}
		}

		@Override
		public void increment(int numberOfIncrements) {
			for (int i = 0; i < numberOfIncrements; i++) {
				if (this.getValue() < this.upper) {
					this.setValue(this.getValue() + 1);
				}
			}
		}
    	
    }
}
