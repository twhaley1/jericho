package com.jericho.view;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class SaveDialog extends Dialog<Boolean> {

	public SaveDialog() {
		this.setTitle("Jericho");
		this.setHeaderText("New Settings Have Been Detected.");
		this.setContentText("Would you like to save these changes?");
		
		this.getDialogPane().getButtonTypes().add(ButtonType.YES);
		this.getDialogPane().getButtonTypes().add(ButtonType.NO);
		
		this.setResultConverter(button -> {
			if (button == ButtonType.NO) {
				return false;
			} else {
				return true;
			}
		});
	}
}
