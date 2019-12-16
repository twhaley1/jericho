package com.jericho.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * A class that uses a StringBuilder to incrementally build up 
 * strings one character at a time.
 * 
 * @author thomaswhaley
 *
 */
public class StringExpander implements Commandable {

	private static final int LIMIT = 350;
	
	private String content;
	
	private StringProperty contentProperty;
	private BooleanProperty isCompleteProperty;
	
	private int currentIndex;
	private boolean isPaused;
	
	/**
	 * Creates a new StringExpander with the specified StringBuilder.
	 * 
	 * @precondition builder != null.
	 * @postcondition contentProperty().get().equals(null) && isCompleteProperty().get() == false
	 * 
	 * @param builder the StringBuilder to give to this expander.
	 */
	public StringExpander(String content) {
		if (content == null) {
			throw new IllegalArgumentException();
		}
		
		this.content = content;
		this.contentProperty = new SimpleStringProperty("");
		this.isCompleteProperty = new SimpleBooleanProperty();
		
		this.currentIndex = 0;
		this.unPause();
	}

	@Override
	public void execute() {
		this.incrementContent();
	}

	@Override
	public boolean canExecute() {
		return !this.isComplete();
	}

	@Override
	public void dispose() {
		this.isCompleteProperty.setValue(this.isComplete());
	}
	
	/**
	 * Gets the current content of this expander.
	 * 
	 * @return the current content.
	 */
	public ReadOnlyStringProperty contentProperty() {
		return this.contentProperty;
	}
	
	/**
	 * Gets whether or not this expander is finished expanding its content.
	 * 
	 * @return true if this expander is finished expanding; false otherwise.
	 */
	public ReadOnlyBooleanProperty isCompleteProperty() {
		return this.isCompleteProperty;
	}
	
	/**
	 * Pauses this expander. Its content will not increase further
	 * upon a call to this method.
	 */
	public void pause() {
		this.isPaused = true;
	}
	
	/**
	 * Unpauses this expander. This expander's content can be extended upon.
	 */
	public void unPause() {
		this.isPaused = false;
	}
	
	/**
	 * Resets this expander. Extensions upon its contents will begin
	 * with an empty string.
	 */
	public void reset() {
		this.currentIndex = 0;
		this.isCompleteProperty.setValue(this.isComplete());
	}
	
	private void incrementContent() {
		if (!this.isPaused) {
			this.currentIndex++;	
		}
		
		// TODO: Need to do some work here. Maybe try to format the text more once it is uploaded.
		// TODO: Split it up by newline characters, then can create a map with key = index indicating line number, 
		// TODO: and value = the length of the line. We could use this to evenly cut out a whole line at a time 
		// TODO: instead of one character at a time.
		int lowerBound = this.currentIndex > LIMIT ? this.currentIndex - LIMIT : 0;
		this.contentProperty.setValue(this.content.substring(lowerBound, this.currentIndex));
	}
	
	private boolean isComplete() {
		return this.content.length() == this.currentIndex;
	}
	
}
