package com.jericho.test.stringexpander;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jericho.model.StringExpander;

public class TestReset {

	private StringExpander expander;
	
	@BeforeEach
	public void setUp() {
		this.expander = new StringExpander("hello");
	}
	
	@AfterEach
	public void tearDown() {
		this.expander = null;
	}
	
	@Test
	public void testResetsCorrectValues() {
		this.expander.reset();
		
		assertEquals(false, this.expander.isCompleteProperty().get());
	}

	@Test
	public void testResetsWithEmptyStringBuilder() {
		this.expander = new StringExpander("");
		this.expander.reset();
		
		assertEquals(true, this.expander.isCompleteProperty().get());
	}
	
	@Test
	public void testEffectsCanExecute() {
		this.expander.reset();
		
		assertEquals(true, this.expander.canExecute());
	}
}
