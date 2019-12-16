package com.jericho.test.speedadjuster;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.jericho.model.SpeedAdjuster;

public class TestConstructor {

	@Test
	public void testNotAllowEqualValues() {
		assertThrows(IllegalArgumentException.class, () -> new SpeedAdjuster(50, 50));
	}

	@Test
	public void testSetsProperUpperLower() {
		SpeedAdjuster adjuster = new SpeedAdjuster(5, 1);
		
		assertAll(() -> assertEquals(1, adjuster.getLower()),
				() -> assertEquals(5, adjuster.getUpper()));
	}
}
