package com.jericho.view.fileselection;

import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

/**
 * Allows the user to select a text file from their system.
 * 
 * @author thomaswhaley
 *
 */
public class UserTextFileSelection extends FileSelection {

	private static final String TEXT_FILE_TITLE = "Select Text File";
	private static final String TEXT_FILE_SUMMARY = "Text Files";
	private static final String TEXT_FILE_EXTENSIONS = "*.txt";
	
	/**
	 * Creates a new UserTextFileSelection.
	 * 
	 * @precondition ownerWindow != null
	 * 
	 * @param ownerWindow the owner window of the file chooser.
	 */
	public UserTextFileSelection(Window ownerWindow) {
		super(ownerWindow, TEXT_FILE_TITLE, new ExtensionFilter(TEXT_FILE_SUMMARY, TEXT_FILE_EXTENSIONS));
	}

}
