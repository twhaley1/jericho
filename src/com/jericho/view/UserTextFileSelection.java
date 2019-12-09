package com.jericho.view;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

/**
 * Allows the user to select a variety of files.
 * 
 * @author thomaswhaley
 *
 */
public class UserTextFileSelection {

	private static final String TEXT_FILE_TITLE = "Select Text File";
	private static final String TEXT_FILE_SUMMARY = "Text Files";
	private static final String TEXT_FILE_EXTENSIONS = "*.txt";
	
	private static final String OWNER_WINDOW_NULL = "ownerWindow cannot be null.";
	
	private FileChooser fileChooser;
	private Window ownerWindow;
	
	/**
	 * Creates a new UserTextFileSelection.
	 * 
	 * @precondition ownerWindow != null
	 * 
	 * @param ownerWindow the owner window of the file chooser.
	 */
	public UserTextFileSelection(Window ownerWindow) {
		if (ownerWindow == null) {
			throw new IllegalArgumentException(OWNER_WINDOW_NULL);
		}
		
		this.ownerWindow = ownerWindow;
		this.fileChooser = new FileChooser();
		this.fileChooser.setTitle(TEXT_FILE_TITLE);
		this.fileChooser.getExtensionFilters().add(
				new ExtensionFilter(TEXT_FILE_SUMMARY, TEXT_FILE_EXTENSIONS));
	}
	
	/**
	 * Prompts a user to select a file and returns the selected
	 * file.
	 * 
	 * @return the selected file or null if no file was selected.
	 */
	public File selectFile() {
		return this.fileChooser.showOpenDialog(this.ownerWindow);
	}
}
