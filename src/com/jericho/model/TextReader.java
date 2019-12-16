package com.jericho.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * A class that reads text from a file on a different thread.
 * The loading progress is trackable.
 * 
 * @author thomaswhaley
 *
 */
public class TextReader extends Reader {
	
	/**
	 * Creates a new TextReader object.
	 * 
	 * @precondition file != null && file.exists()
	 * @postcondition getLoadingProgress().get() == 0.0
	 * 
	 * @param file the file to read the contents from.
	 */
	public TextReader(File file) {
		super(file);
	}
	
	@Override
	protected String getFileContents() throws IOException {
		StringBuilder sb = new StringBuilder();
		
		byte[] contents = Files.readAllBytes(this.getFile().toPath());
		double numberOfBytes = contents.length;
		double accumulatedBytes = 0.0;
		
		for (byte currentByte : contents) {
			Character content = (char) currentByte;
			sb.append(content);	
			
			accumulatedBytes++;
			this.getLoadingProgress().setValue(accumulatedBytes / numberOfBytes);
		}
		
		return sb.toString();
	}
}
