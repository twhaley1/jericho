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
		StringBuilder sb = new StringBuilder();
		sb.append("hello");
		this.expander = new StringExpander(sb);
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
		StringBuilder sb = new StringBuilder();
		sb.append("");
		this.expander = new StringExpander(sb);
		this.expander.reset();
		
		assertEquals(true, this.expander.isCompleteProperty().get());
	}
	
	@Test
	public void testEffectsCanExecute() {
		this.expander.reset();
		
		assertEquals(true, this.expander.canExecute());
	}
}
