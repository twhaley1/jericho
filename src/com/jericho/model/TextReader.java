package com.jericho.model;

import java.io.File;
import java.nio.file.Files;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;

public class TextReader extends Task<StringBuilder> {

	private File file;
	private DoubleProperty progress;
	
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
		StringBuilder sb = new StringBuilder();
		
		byte[] contents = Files.readAllBytes(this.file.toPath());
		double numberOfBytes = contents.length;
		double accumulatedBytes = 0.0;
		
		for (byte currentByte : contents) {
			Character content = (char) currentByte;
			if (!content.toString().equals(System.lineSeparator())) {
				sb.append(content);	
			}
			accumulatedBytes++;
				
			this.progress.setValue(accumulatedBytes / numberOfBytes);
		}
		
		return sb;
	}
	
	public ReadOnlyDoubleProperty getLoadingProgress() {
		return this.progress;
	}
}
