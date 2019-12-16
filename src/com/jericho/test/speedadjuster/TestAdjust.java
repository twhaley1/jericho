package com.jericho.test.speedadjuster;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.jericho.model.SpeedAdjuster;

public class TestAdjust {

	@Test
	public void testNotAllowGreaterSpeed() {
		SpeedAdjuster adjuster = new SpeedAdjuster(1, 100);
		assertThrows(IllegalArgumentException.class, () -> adjuster.adjust(101));
	}
	
	@Test
	public void testNotAllowSmallerSpeed() {
		SpeedAdjuster adjuster = new SpeedAdjuster(1, 100);
		assertThrows(IllegalArgumentException.class, () -> adjuster.adjust(0));
	}
	
	@Test
	public void testAtBoundaries() {
		SpeedAdjuster adjuster = new SpeedAdjuster(1, 100);
		SpeedAdjuster otherAdjuster = new SpeedAdjuster(26, 94);
		
		int bottomBoundary = adjuster.adjust(1);
		int topBoundary = adjuster.adjust(100);
		
		int otherBottomBoundary = otherAdjuster.adjust(26);
		int otherTopBoundary = otherAdjuster.adjust(94);
		
		assertAll(() -> assertEquals(100, bottomBoundary),
				() -> assertEquals(1, topBoundary),
				() -> assertEquals(94, otherBottomBoundary),
				() -> assertEquals(26, otherTopBoundary));
	}
	
	@Test
	public void testAtMidpoint() {
		SpeedAdjuster adjuster = new SpeedAdjuster(1, 100);
		
		int midpoint = adjuster.adjust(51);
		
		assertEquals(50, midpoint);
	}
	
	@Test
	public void testDifferentVariations() {
		SpeedAdjuster adjuster = new SpeedAdjuster(35, 40);
		
		int test0 = adjuster.adjust(35);
		int test1 = adjuster.adjust(36);
		int test2 = adjuster.adjust(37);
		int test3 = adjuster.adjust(38);
		int test4 = adjuster.adjust(39);
		int test5 = adjuster.adjust(40);
		
		assertAll(() -> assertEquals(40, test0),
				() -> assertEquals(39, test1),
				() -> assertEquals(38, test2),
				() -> assertEquals(37, test3),
				() -> assertEquals(36, test4),
				() -> assertEquals(35, test5));
	}

}
