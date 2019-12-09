package com.jericho.model;

public class StringExpander {

	private StringBuilder sb;
	private int currentIndex;
	private boolean isPaused;
	
	public StringExpander(StringBuilder sb) {
		this.sb = sb;
		this.currentIndex = 0;
		this.isPaused = false;
	}
	
	public String getNextString() {
		if (!this.isPaused) {
			this.currentIndex++;	
		}
		return this.sb.substring(0, this.currentIndex);
	}
	
	public boolean isComplete() {
		return this.sb.length() == this.currentIndex;
	}
	
	public void reset() {
		this.currentIndex = 0;
	}
	
	public void toggle() {
		this.isPaused = !this.isPaused;
	}
}
