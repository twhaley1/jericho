package com.jericho.test.stringexpander;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jericho.model.StringExpander;

public class TestExecute {

	private StringExpander expander;
	
	@BeforeEach
	public void setUp() {
		this.expander = new StringExpander("yes");
	}
	
	@AfterEach
	public void tearDown() {
		this.expander = null;
	}
	
	@Test
	public void testOneCall() {
		this.expander.execute();
		assertEquals("y", this.expander.contentProperty().get());
	}
	
	@Test
	public void testTwoCall() {
		this.expander.execute();
		this.expander.execute();
		assertEquals("ye", this.expander.contentProperty().get());
	}

	@Test
	public void testThreeCall() {
		this.expander.execute();
		this.expander.execute();
		this.expander.execute();
		assertEquals("yes", this.expander.contentProperty().get());
	}
	
	@Test
	public void testPauseDoesNotChangeContent() {
		this.expander.execute();
		this.expander.execute();
		this.expander.execute();
		
		this.expander.pause();
		
		this.expander.execute();
		this.expander.execute();
		this.expander.execute();
		
		assertEquals("yes", this.expander.contentProperty().get());
	}
	
	@Test
	public void testPauseAndUnPauseOnContent() {
		this.expander.execute();
		this.expander.execute();
		
		String beforePause = this.expander.contentProperty().get();
		this.expander.pause();
		
		this.expander.execute();
		this.expander.execute();
		
		this.expander.unPause();
		
		this.expander.execute();
		
		assertAll(() -> assertEquals("ye", beforePause),
				() -> assertEquals("yes", this.expander.contentProperty().get()));
	}
	
	@Test
	public void testResetOnContent() {
		this.expander.execute();
		this.expander.execute();
		this.expander.execute();
		String beforeReset = this.expander.contentProperty().get();
		
		this.expander.dispose();
		
		assertAll(() -> assertEquals("yes", beforeReset),
				() -> assertEquals(true, this.expander.isCompleteProperty().get()));
	}
	
	@Test
	public void testCompletionOnContent() {
		this.expander.execute();
		this.expander.execute();
		this.expander.execute();
		
		assertAll(() -> assertEquals("yes", this.expander.contentProperty().get()),
				() -> assertEquals(false, this.expander.canExecute()));
	}
}
