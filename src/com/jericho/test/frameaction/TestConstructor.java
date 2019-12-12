package com.jericho.test.frameaction;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.jericho.model.FrameAction;

public class TestConstructor {

	@Test
	public void testNotAllowNullCommand() {
		assertThrows(IllegalArgumentException.class, () -> new FrameAction(null));
	}

}
