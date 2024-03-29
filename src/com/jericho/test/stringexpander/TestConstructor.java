package com.jericho.test.stringexpander;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.jericho.model.StringExpander;

public class TestConstructor {

	@Test
	public void testNotAllowNullBuilder() {
		assertThrows(IllegalArgumentException.class, () -> new StringExpander(null));
	}

	@Test
	public void testValidConstruction() {
		StringExpander expander = new StringExpander("yes");
		
		assertAll(() -> assertEquals(false, expander.isCompleteProperty().get()),
				() -> assertEquals(null, expander.contentProperty().get()));
	}
}
