package com.jericho.model;

import java.io.Serializable;

public class Setting implements Serializable {

	private static final long serialVersionUID = 55L;
	
	private String font;
	private int speed;
	
	public Setting(String font, int speed) {
		this.font = font;
		this.speed = speed;
	}
	
	public String getFont() {
		return this.font;
	}
	
	public int getSpeed() {
		return this.speed;
	}
}
