package com.jericho.model;

import java.io.File;
import java.io.IOException;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;

public abstract class Reader extends Task<String> {

	private File file;
	private DoubleProperty progress;
	
	protected Reader(File file) {
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
	protected String call() throws Exception {
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
	protected abstract String getFileContents() throws IOException;
	
	/**
	 * Gets the loading progress of this reader.
	 * 
	 * @return the loading progress.
	 */
	public DoubleProperty getLoadingProgress() {
		return this.progress;
	}
	
	/**
	 * Gets the file this reader is working with.
	 * 
	 * @return this reader's file.
	 */
	public File getFile() {
		return this.file;
	}
}
