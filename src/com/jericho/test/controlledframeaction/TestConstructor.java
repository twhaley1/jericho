package com.jericho.test.controlledframeaction;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jericho.model.Commandable;
import com.jericho.model.ControlledFrameAction;

public class TestConstructor {

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
	
	@BeforeEach
	public void setUp() {
		this.command = new TestCommand();
	}
	
	@AfterEach
	public void tearDown() {
		this.command = null;
	}
	
	@Test
	public void testNotAllowZeroSpeed() {
		assertThrows(IllegalArgumentException.class, () -> new ControlledFrameAction(this.command, 0));
	}

	@Test
	public void testNotAllowNegativeSpeed() {
		assertThrows(IllegalArgumentException.class, () -> new ControlledFrameAction(this.command, -4));
	}
	
	@Test
	public void testValidConstruction() {
		ControlledFrameAction action = new ControlledFrameAction(this.command, 5);
		
		assertEquals(5, action.getSpeed());
	}
}
