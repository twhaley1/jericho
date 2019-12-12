package com.jericho.test.frameaction;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jericho.model.Commandable;
import com.jericho.model.FrameAction;

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
	private FrameAction action;
	
	@BeforeEach
	public void setUp() {
		this.command = new TestCommand();
		this.action = new FrameAction(this.command);
	}
	
	@AfterEach
	public void tearDown() {
		this.command = null;
		this.action = null;
	}
	
	@Test
	public void testWhileCanIsTrue() {
		this.command.setCanExecute(true);
		this.action.handle(1);
		
		assertEquals(1, this.command.getSignature());
	}

	@Test
	public void testWhileCanIsFalse() {
		this.command.setCanExecute(false);
		this.action.handle(1);
		
		assertEquals(0, this.command.getSignature());
	}
}
