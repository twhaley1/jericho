package com.jericho.model;

public class ControlledFrameHandler<T> extends FrameHandler<T> {

	private int handleCount;
	private int speed;
	
	public ControlledFrameHandler(int speed) {
		this.speed = speed;
		this.handleCount = 0;
	}

	@Override
	public void handle(long arg0) {
		if (this.handleCount % this.speed == 0) {
			super.handle(arg0);	
		}
		
		this.handleCount++;
	}
	
}
