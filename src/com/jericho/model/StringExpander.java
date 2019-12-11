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

	private StringBuilder builder;
	
	private StringProperty contentProperty;
	private BooleanProperty isCompleteProperty;
	
	private int currentIndex;
	private boolean isPaused;
	
	/**
	 * Creates a new StringExpander with the specified StringBuilder.
	 * 
	 * @precondition builder != null.
	 * 
	 * @param builder the StringBuilder to give to this expander.
	 */
	public StringExpander(StringBuilder builder) {
		if (builder == null) {
			throw new IllegalArgumentException();
		}
		
		this.builder = builder;
		this.contentProperty = new SimpleStringProperty();
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
		this.isCompleteProperty.setValue(true);
		this.reset();
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
	}
	
	private void incrementContent() {
		if (!this.isPaused) {
			this.currentIndex++;	
		}
		
		this.contentProperty.setValue(this.builder.substring(0, this.currentIndex));
	}
	
	private boolean isComplete() {
		return this.builder.length() == this.currentIndex;
	}
	
}
