package com.jericho.model.settings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * A class that contains a static utility method that
 * allows the system to save a serialized Setting object to a file.
 * 
 * @author thomaswhaley
 *
 */
public final class SettingSaver {

	/**
	 * Saves the specified Setting object in the specified file. 
	 * 
	 * @param file the file to save the Setting object in.
	 * @param setting the Setting object to save in the file.
	 * @throws IOException if an error occurs during the file I/O.
	 */
	public static void save(File file, Setting setting) throws IOException {
		try (FileOutputStream fileOutput = new FileOutputStream(file)) {
			try (ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput)) {
				objectOutput.writeObject(setting);
			}
		}
	}
}
