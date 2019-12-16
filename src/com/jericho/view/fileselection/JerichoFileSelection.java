package com.jericho.view.fileselection;

import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

/**
 * Allows the user to select a text file from their system.
 * 
 * @author thomaswhaley
 *
 */
public class JerichoFileSelection extends FileSelection {

	private static final String TEXT_FILE_TITLE = "Select Reading File";
	private static final String TEXT_FILE_SUMMARY = "Text Files";
	private static final String TEXT_FILE_EXTENSIONS = "*.txt";
	private static final String PDF_FILE_SUMMARY = "PDF Files";
	private static final String PDF_FILE_EXTENSIONS = "*.pdf";
	
	/**
	 * Creates a new UserTextFileSelection.
	 * 
	 * @precondition ownerWindow != null
	 * 
	 * @param ownerWindow the owner window of the file chooser.
	 */
	public JerichoFileSelection(Window ownerWindow) {
		super(ownerWindow, TEXT_FILE_TITLE, 
				new ExtensionFilter(TEXT_FILE_SUMMARY, TEXT_FILE_EXTENSIONS),
				new ExtensionFilter(PDF_FILE_SUMMARY, PDF_FILE_EXTENSIONS));
	}

}
