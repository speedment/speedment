/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.internal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Singleton.
 * 
 * @author Emil Forslund
 */
public final class Settings {

	private final static File SETTINGS_FILE = new File("settings.properties");

	private final Properties props;
	
	private Settings() {
		props = new Properties();
		
		if (!SETTINGS_FILE.exists()) {
			try {
				SETTINGS_FILE.createNewFile();
			} catch (IOException ex) {
				throw new RuntimeException(
					"Could not create file '" + filename() + "'."
				);
			}
		}

		try (final InputStream is = new FileInputStream(SETTINGS_FILE)) {
			props.load(is);
		} catch (Exception e) {
			throw new RuntimeException(
				"Could not find file '" + filename() + "'."
			);
		}
	}
	
	public boolean has(String key) {
		return props.containsKey(key);
	}
	
	public <T> void set(String key, T value) {
		props.setProperty(key, value.toString());
		storeChanges();
	}
	
	public String get(String key) {
		return props.getProperty(key);
	}
	
	public String get(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}
	
	public Boolean get(String key, Boolean defaultValue) {
		return Boolean.parseBoolean(props.getProperty(key, Boolean.toString(defaultValue)));
	}
	
	public Integer get(String key, Integer defaultValue) {
		return Integer.parseInt(props.getProperty(key, Integer.toString(defaultValue)));
	}

	private void storeChanges() {
		try (final OutputStream out = new FileOutputStream(SETTINGS_FILE, false)) {
			props.store(out, "Speedment Settings");
		} catch (IOException ex) {
			throw new RuntimeException(
				"Could not save file '" + filename() + "'."
			);
		}
	}

	private static String filename() {
		return SETTINGS_FILE.getAbsolutePath();
	}
	
	private static class Holder {
		private final static Settings INSTANCE = new Settings();
	}
	
	public static Settings inst() {
		return Holder.INSTANCE;
	}
}