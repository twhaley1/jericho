package com.jericho.model;

/**
 * An abstract extension upon a standard FrameActions object.
 * ControlledFrameActions take a speed factor into account that limits what
 * frames the consumers/predicates occur on.
 * 
 * @author thomaswhaley
 *
 * @param <T>
 */
public abstract class ControlledFrameActions<T> extends FrameActions<T> {

	private int frameCount;
	private int speed;
	
	protected ControlledFrameActions(int speed) {
		if (speed <= 0) {
			throw new IllegalArgumentException();
		}
		
		this.speed = speed;
		this.frameCount = 0;
	}

	@Override
	public void handle(long currentTime) {
		if (this.canExecute()) {
			super.handle(currentTime);	
		}
		
		this.frameCount++;
	}
	
	/**
	 * Sets the speed at which the actions/predicates occur.
	 * The smaller the value for speed, the faster the executions
	 * will occur. A speed of 1 behaves exactly the same as an
	 * ordinary FrameActions object.
	 * 
	 * @precondition speed > 0
	 * @postcondition getSpeed() == speed
	 * 
	 * @param speed the value to set to speed.
	 */
	public void setSpeed(int speed) {
		if (speed <= 0) {
			throw new IllegalArgumentException();
		}
		
		this.speed = speed;
	}
	
	/**
	 * Gets the current speed of this ControlledFrameActions object.
	 * 
	 * @return the current speed.
	 */
	public int getSpeed() {
		return this.speed;
	}
	
	private boolean canExecute() {
		return this.frameCount % this.speed == 0;
	}
}
