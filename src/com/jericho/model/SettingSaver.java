package com.jericho.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SettingSaver {

	private Setting setting;
	
	public SettingSaver(Setting setting) {
		this.setting = setting;
	}
	
	public void save(File file) throws IOException {
		try (FileOutputStream fileOutput = new FileOutputStream(file)) {
			try (ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput)) {
				objectOutput.writeObject(this.setting);
			}
		}
	}
}
