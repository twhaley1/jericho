package com.jericho.model;

import java.io.Serializable;

/**
 * A data class that maintains the current settings of the system.
 * 
 * @author thomaswhaley
 *
 */
public class Setting implements Serializable {

	private static final long serialVersionUID = 55L;
	
	private String font;
	private int speed;
	
	/**
	 * Creates a new settings object.
	 * 
	 * @param font the system font.
	 * @param speed the speed that text is displayed when pressing play.
	 */
	public Setting(String font, int speed) {
		this.font = font;
		this.speed = speed;
	}
	
	/**
	 * Gets this setting's font.
	 * 
	 * @return the font.
	 */
	public String getFont() {
		return this.font;
	}
	
	/**
	 * Gets this setting's speed.
	 * 
	 * @return the speed.
	 */
	public int getSpeed() {
		return this.speed;
	}
}
