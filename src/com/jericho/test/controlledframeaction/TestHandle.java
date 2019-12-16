package com.jericho.test.controlledframeaction;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jericho.model.Commandable;
import com.jericho.model.ControlledFrameAction;

public class TestHandle {

	private class TestCommand implements Commandable {
		
		private boolean canExecute;
		
		private int methodSignature;
		
		public void setCanExecute(boolean canExecute) {
			this.canExecute = canExecute;
			this.methodSignature = -1;
		}
		
		@Override
		public void execute() {
			this.methodSignature = 1;
		}
		
		@Override
		public boolean canExecute() {
			return this.canExecute;
		}
		
		@Override
		public void dispose() {
			this.methodSignature = 0;
		}
		
		public int getSignature() {
			return this.methodSignature;
		}
	}
	
	private TestCommand command;
	private ControlledFrameAction action;
	
	@BeforeEach
	public void setUp() {
		this.command = new TestCommand();
		this.action = new ControlledFrameAction(this.command, 2);
		this.action.handle(1);
		this.action.handle(1);
	}
	
	@AfterEach
	public void tearDown() {
		this.command = null;
		this.action = null;
	}
	
	@Test
	public void testExecutesOnlyOnValidFrame() {
		this.command.setCanExecute(true);
		this.action.handle(1);
		int firstResult = this.command.getSignature();
		
		this.command.setCanExecute(true);
		this.action.handle(1);
		int secondResult = this.command.getSignature();
		
		assertAll(() -> assertEquals(1, firstResult),
				() -> assertEquals(-1, secondResult));
	}

}
