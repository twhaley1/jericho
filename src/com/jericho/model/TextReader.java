package com.jericho.model;

import java.io.File;
import java.nio.file.Files;
import java.util.function.Consumer;

import javafx.concurrent.Task;

public class TextReader extends Task<StringBuilder> {

	private FrameHandler<Double> frameHandler;
	private File file;
	private double progress;
	
	public TextReader(File file, Consumer<Double> callback) {
		this.file = file;
		this.progress = 0.0;
		
		this.frameHandler = new FrameHandler<Double>();
		this.frameHandler.addAction(callback);
		this.frameHandler.setParameter(this.progress);
	}

	@Override
	protected StringBuilder call() throws Exception {
		StringBuilder sb = new StringBuilder();
		
		byte[] contents = Files.readAllBytes(this.file.toPath());
		double numberOfBytes = contents.length;
		double accumulatedBytes = 0.0;
		
		this.frameHandler.start();
		for (byte currentByte : contents) {
			Character content = (char) currentByte;
			if (!content.toString().equals(System.lineSeparator())) {
				sb.append(content);	
			}
			accumulatedBytes++;
				
			this.progress = accumulatedBytes / numberOfBytes;
			this.frameHandler.setParameter(this.progress);
		}
		this.frameHandler.stop();
		this.frameHandler.fireAllStandardActions();
		
		return sb;
	}
	
}
