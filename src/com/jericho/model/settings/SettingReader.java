package com.jericho.model.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * This class contains a static utility method that 
 * allows the system to read in a serialized Setting object.
 * 
 * @author thomaswhaley
 *
 */
public final class SettingReader {
	
	/**
	 * Reads in a Setting object from the specified file.
	 * 
	 * @param file the file to read in the Setting object from.
	 * 
	 * @return the read Setting object.
	 * 
	 * @throws IOException if an error occurs reading the file.
	 * @throws ClassNotFoundException if an error occurs during the serialization process.
	 */
	public static Setting readSetting(File file) throws IOException, ClassNotFoundException {
		Setting setting = null;
		
		try (FileInputStream fileInput = new FileInputStream(file)) {
			try (ObjectInputStream objectInput = new ObjectInputStream(fileInput)) {
				setting = (Setting) objectInput.readObject();
			}
		}
		
		return setting;
	}
	
}
