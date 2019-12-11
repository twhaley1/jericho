package com.jericho.model;

import javafx.animation.AnimationTimer;

/**
 * A class that uses executes commands based on an animation timer. 
 * 
 * @author thomaswhaley
 *
 */
public class FrameAction extends AnimationTimer {

	private Commandable command;
	
	/**
	 * Creates a new FrameAction object.
	 * 
	 * @precondition command != null
	 * 
	 * @param command the command to give to this FrameAction.
	 */
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
