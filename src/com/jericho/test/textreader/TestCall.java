package com.jericho.test.textreader;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.jericho.model.TextReader;

public class TestCall {

	private class TestReader extends TextReader {

		public TestReader(File file) {
			super(file);
		}
		
		public StringBuilder getContents() throws Exception {
			return super.call();
		}
	}
	
	@Test
	public void testValidRead() throws Exception {
		String path = this.getClass().getResource("text_test.txt").getPath();
		File testFile = new File(path);
		TestReader reader = new TestReader(testFile);
		
		String readContents = reader.getContents().toString();
		
		assertAll(() -> assertEquals("This is a text file.", readContents),
				() -> assertEquals(1.0, reader.getLoadingProgress().get(), 0.000001));
	}

}
