package com.jericho.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SettingReader {
	
	public Setting readSetting(File file) throws IOException, ClassNotFoundException {
		Setting setting = null;
		
		try (FileInputStream fileInput = new FileInputStream(file)) {
			try (ObjectInputStream objectInput = new ObjectInputStream(fileInput)) {
				setting = (Setting) objectInput.readObject();
			}
		}
		
		return setting;
	}
}
