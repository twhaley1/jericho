package com.jericho.view.fileselection;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * An abstraction for prompting the user to select a file.
 * Subclasses of FileSelection may choose what they want the title
 * for the file chooser to be, as well as apply whatever extension filters they wish.
 * 
 * @author thomaswhaley
 *
 */
public abstract class FileSelection {

	private static final String OWNER_WINDOW_NULL = "ownerWindow cannot be null.";
	
	private FileChooser fileChooser;
	private Window ownerWindow;
	
	protected FileSelection(Window ownerWindow, String title, ExtensionFilter... extensionFilters) {
		if (ownerWindow == null) {
			throw new IllegalArgumentException(OWNER_WINDOW_NULL);
		}
		
		this.ownerWindow = ownerWindow;
		
		this.fileChooser = new FileChooser();
		this.fileChooser.setTitle(title);
		
		for (ExtensionFilter filter : extensionFilters) {
			this.fileChooser.getExtensionFilters().add(filter);
		}
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
