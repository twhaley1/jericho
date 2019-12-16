package com.jericho.model;

public class Content {

	private String[] lines;
	
	public Content(String data) {
		if (data == null) {
			throw new IllegalArgumentException();
		}
		
		this.lines = data.split(System.lineSeparator());
	}

	public String getContentFrom(int lineNumber, int characterIndex, int cutoff) {
		int startingIndex = lineNumber > cutoff ? lineNumber - cutoff + 1 : 0;
		StringBuilder contentBuilder = new StringBuilder();
		for (int i = startingIndex; i < lineNumber; i++) {
			if (this.lines[i].length() != 0) {
				contentBuilder.append(this.lines[i] + System.lineSeparator());	
			}
		}
		contentBuilder.append(this.lines[lineNumber].substring(0, characterIndex));
		
		return contentBuilder.toString();
	}
	
	public int getNumberOfLines() {
		return this.lines.length;
	}

	public String getLineAt(int index) {
		return this.lines[index];
	}
}
