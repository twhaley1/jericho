package com.jericho.model;

/**
 * A class that uses a StringBuilder to incrementally build up 
 * strings one character at a time.
 * 
 * @author thomaswhaley
 *
 */
public class StringExpander {

	private StringBuilder builder;
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
		this.currentIndex = 0;
		this.unPause();
	}
	
	/**
	 * Gets the next string from the string builder that has one additional 
	 * character than the last string returned from getNextString(). This 
	 * method can throw a StringIndexOutOfBoundsException if the number of 
	 * calls to getNextString() exceeds the length of this StringExpander's 
	 * StringBuilder. 
	 * 
	 * @return the next string.
	 */
	public String getNextString() {
		if (!this.isPaused) {
			this.currentIndex++;	
		}
		
		return this.builder.substring(0, this.currentIndex);
	}
	
	/**
	 * Determines if this string expander has reached the end of its 
	 * derived string builder. If this method returns true, then
	 * any further calls to getNextString() will result in a 
	 * StringIndexOutOfBoundsException. 
	 * 
	 * @return true if this expander is complete; false otherwise.
	 */
	public boolean isComplete() {
		return this.builder.length() == this.currentIndex;
	}
	
	/**
	 * Resets this string expander. As long as this string expander's 
	 * derived string builder has a length greater than or equal to 1, 
	 * then this method ensures that the next call to getNextString() will
	 * not throw an exception.
	 */
	public void reset() {
		this.currentIndex = 0;
	}
	
	/**
	 * Toggles the pausing effect on this expander. If the current
	 * state is paused, then this method will unpause the expander. If the
	 * current state is unpaused, then this method will pause the expander.
	 */
	public void toggle() {
		this.isPaused = !this.isPaused;
	}
	
	/**
	 * unpauses this expander. A call to this method will ensure that successive
	 * calls to getNextString() will result in a string with length greater than
	 * the string previously returned by getNextString(). This assumes that the 
	 * getNextString() method does not throw a StringIndexOutOfBoundsException.
	 */
	public void unPause() {
		this.isPaused = false;
	}
}
