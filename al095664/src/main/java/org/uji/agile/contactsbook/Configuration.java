package org.uji.agile.contactsbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

final class Configuration {
	private static final String STORAGE_DIR_PROPERTY = "storage_dir";
	private static final String PROPERTIES_FILE = "conf.properties";
	private static Properties properties;
	private static boolean failedInitialization = false;
	
	static {
		properties = new Properties();
		InputStream inputStream = null;
		inputStream = FilePersonDAO.class
				.getResourceAsStream(PROPERTIES_FILE);
		if (inputStream != null) {
			try {
				properties.load(inputStream);
			} catch (IOException e) {
				failedInitialization = true;
			}
			finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					failedInitialization = true;
				}	
			}
		}
	}
	
	private Configuration() {}
	
	public static String readStorageDirectory(String sDefault) {
		if (failedInitialization) return sDefault;
		return properties.getProperty(STORAGE_DIR_PROPERTY, sDefault);
	}

}
