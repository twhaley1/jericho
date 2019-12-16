package com.jericho.model;

import java.io.File;

public final class ReaderFactory {

	public static Reader createReader(File file) {
		if (file == null) {
			throw new IllegalArgumentException();
		}
		
		if (file.getName().endsWith(".txt")) {
			return new TextReader(file);
		} else if (file.getName().endsWith(".pdf")) {
			return new PdfReader(file);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
}
