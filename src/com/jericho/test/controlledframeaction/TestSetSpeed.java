package com.jericho.test.controlledframeaction;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jericho.model.Commandable;
import com.jericho.model.ControlledFrameAction;

public class TestSetSpeed {

	private class TestCommand implements Commandable {
		
		@Override
		public void execute() {
		}
		
		@Override
		public boolean canExecute() {
			return false;
		}
		
		@Override
		public void dispose() {
		}
	}
	
	private TestCommand command;
	private ControlledFrameAction action;
	
	@BeforeEach
	public void setUp() {
		this.command = new TestCommand();
		this.action = new ControlledFrameAction(this.command, 1);
	}
	
	@AfterEach
	public void tearDown() {
		this.command = null;
		this.action = null;
	}
	
	@Test
	public void testNotAllowZeroSpeed() {
		assertThrows(IllegalArgumentException.class, () -> this.action.setSpeed(0));
	}

	@Test
	public void testNotAllowNegativeSpeed() {
		assertThrows(IllegalArgumentException.class, () -> this.action.setSpeed(-1));
	}
	
	@Test
	public void testValidSet() {
		this.action.setSpeed(10);
		
		assertEquals(10, this.action.getSpeed());
	}
}
