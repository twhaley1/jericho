package com.jericho.model;

import javafx.animation.AnimationTimer;

/**
 * An abstract class that is based on an animation timer. 
 * 
 * @author thomaswhaley
 *
 */
public class FrameAction extends AnimationTimer {

	private Commandable command;
	
	public FrameAction(Commandable command) {
		if (command == null) {
			throw new IllegalArgumentException();
		}
		
		this.command = command;
	}
	
	@Override
	public void handle(long currentTime) {
		if (this.command.canExecute()) {
			this.command.execute();
		} else {
			this.command.dispose();
			this.stop();
		}
	}
	
}
