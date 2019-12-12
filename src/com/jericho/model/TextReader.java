package com.jericho.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;

/**
 * A class that reads text from a file on a different thread.
 * The loading progress is trackable.
 * 
 * @author thomaswhaley
 *
 */
public class TextReader extends Task<StringBuilder> {

	private File file;
	private DoubleProperty progress;
	
	/**
	 * Creates a new TextReader object.
	 * 
	 * @precondition file != null && file.exists()
	 * @postcondition getLoadingProgress().get() == 0.0
	 * 
	 * @param file the file to read the contents from.
	 */
	public TextReader(File file) {
		if (file == null) {
			throw new IllegalArgumentException();
		}
		if (!file.exists()) {
			throw new IllegalArgumentException();
		}
		
		this.file = file;
		this.progress = new SimpleDoubleProperty();
	}

	@Override
	protected StringBuilder call() throws Exception {
		return this.getFileContents();
	}
	
	/**
	 * Reads the file contents from this reader's file object. Also,
	 * updates a progress property that indicates the current progress
	 * of reading the file.
	 * 
	 * @postcondition getLoadingProgress() == 1.0
	 * 
	 * @return a StringBuilder that contains the contents of the file.
	 * @throws IOException if an error occurs loading the file.
	 */
	private StringBuilder getFileContents() throws IOException {
		StringBuilder sb = new StringBuilder();
		
		byte[] contents = Files.readAllBytes(this.file.toPath());
		double numberOfBytes = contents.length;
		double accumulatedBytes = 0.0;
		
		for (byte currentByte : contents) {
			Character content = (char) currentByte;
			sb.append(content);	
			
			accumulatedBytes++;
			this.progress.setValue(accumulatedBytes / numberOfBytes);
		}
		
		return sb;
	}
	
	/**
	 * Gets the loading progress of this reader.
	 * 
	 * @return the loading progress.
	 */
	public ReadOnlyDoubleProperty getLoadingProgress() {
		return this.progress;
	}
}
