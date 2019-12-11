package com.jericho.test.textreader;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import com.jericho.model.TextReader;

import org.junit.jupiter.api.Test;

public class TestConstructor {

	@Test
	public void testDoesNotAllowNullFile() {
		assertThrows(IllegalArgumentException.class, () -> new TextReader(null));
	}

	@Test
	public void testDoesNotAllowNonExistingFile() {
		File blankFile = new File("");
		assertThrows(IllegalArgumentException.class, () -> new TextReader(blankFile));
	}
	
	@Test
	public void testValidConstruction() throws IOException {
		TextReader reader = new TextReader(File.createTempFile("joe", null));
		
		assertEquals(0.0, reader.getLoadingProgress().get(), 0.0000001);
	}
}
