package com.jericho.model.settings;

import java.io.Serializable;

import com.jericho.model.SpeedAdjuster;

import javafx.scene.paint.Color;

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
	private int fontSize;
	
	private double fontRed;
	private double fontGreen;
	private double fontBlue;
	
	private double backgroundRed;
	private double backgroundGreen;
	private double backgroundBlue;
	
	/**
	 * Creates a new settings object.
	 * 
	 * @param font the system font.
	 * @param speed the speed that text is displayed when pressing play.
	 */
	public Setting(String font, int speed, int fontSize, Color fontColor, Color backgroundColor) {
		if (font == null) {
			throw new IllegalArgumentException();
		}
		if (fontColor == null) {
			throw new IllegalArgumentException();
		}
		if (backgroundColor == null) {
			throw new IllegalArgumentException();
		}
		
		this.font = font;
		this.speed = speed;
		this.fontSize = fontSize;
		
		this.fontRed = fontColor.getRed();
		this.fontGreen = fontColor.getGreen();
		this.fontBlue = fontColor.getBlue();
		
		this.backgroundRed = backgroundColor.getRed();
		this.backgroundGreen = backgroundColor.getGreen();
		this.backgroundBlue = backgroundColor.getBlue();
	}
	
	/**
	 * Gets the setting's font color.
	 * 
	 * @return the font color.
	 */
	public Color getFontColor() {
		int normalizedRed = (int) (this.fontRed * 255);
		int normalizedGreen = (int) (this.fontGreen * 255);
		int normalizedBlue = (int) (this.fontBlue * 255);
		return Color.rgb(normalizedRed, normalizedGreen, normalizedBlue);
	}
	
	/**
	 * Gets the setting's background color.
	 * 
	 * @return the background color.
	 */
	public Color getBackgroundColor() {
		int normalizedRed = (int) (this.backgroundRed * 255);
		int normalizedGreen = (int) (this.backgroundGreen * 255);
		int normalizedBlue = (int) (this.backgroundBlue * 255);
		return Color.rgb(normalizedRed, normalizedGreen, normalizedBlue);
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
	 * Gets this setting's font size.
	 * 
	 * @return the font size.
	 */
	public int getFontSize() {
		return this.fontSize;
	}
	
	/**
	 * Gets this setting's speed.
	 * 
	 * @return the speed.
	 */
	public int getSpeed() {
		SpeedAdjuster adjuster = new SpeedAdjuster(1, 100);
		return adjuster.adjust(this.speed);
	}
}
