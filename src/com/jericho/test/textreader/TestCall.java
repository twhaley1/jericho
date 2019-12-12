package com.jericho.test.textreader;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.jericho.model.TextReader;

public class TestCall {

	@Test
	public void testValidRead() throws InterruptedException {
		String path = this.getClass().getResource("text_test.txt").getPath();
		File testFile = new File(path);
		TextReader reader = new TextReader(testFile);
		Thread readingThread = new Thread(reader);
		readingThread.start();
		
		
		assertAll(() -> assertEquals("This is a text file.", reader.get().toString()),
				() -> assertEquals(1.0, reader.getLoadingProgress().get(), 0.000001));
	}

}
