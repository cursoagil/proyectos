package org.uji.agile.contactsbook.internals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public final class ObjectSerializer {

	public static final String SERIALIZED_FILE_EXTENSION = ".ser";
	
	private ObjectSerializer() {}
	
	private static List<File> listSerializationFiles(String directoryPath) {
		List<File> personFiles = new ArrayList<File>();
		File directory = new File(directoryPath);
		if (directory.isDirectory()) {
			for(File file: directory.listFiles()) {
				if (file.getAbsolutePath().endsWith(SERIALIZED_FILE_EXTENSION)) {
					personFiles.add(file);
				}
			}
		}
		return personFiles;
	}
	
	private static <E> E readFromPersistenceStream(ObjectInputStream persistenceStream) {
		Object rawObject = null;
		try {
			rawObject = persistenceStream.readObject();
		} catch (IOException e) {
			rawObject = null;
		} catch (ClassNotFoundException e) {
			rawObject = null;
		}
		return (E) rawObject;
	}

	private static ObjectInputStream createInputPersistenceStream(String route) {
		ObjectInputStream persistenceStream = null;
		
		try {
			persistenceStream = new ObjectInputStream(
									new FileInputStream(route)
								);
		} catch (Exception e) {
			persistenceStream = null;
		}
			
		return persistenceStream;
	}
	
	
	private static ObjectOutputStream createOutputPersistenceStream(String route) {
		ObjectOutputStream persistenceStream = null ;
		try {
			persistenceStream = new ObjectOutputStream(
									new FileOutputStream(route)
								);
		} catch (FileNotFoundException e) {
			persistenceStream = null;
		} catch (IOException e) {
			persistenceStream = null;
		}
		return persistenceStream;
	}
	
	private static <E> boolean writeObjectOnPersistenceStream(E object, ObjectOutputStream persistenceStream) {
		boolean noExceptionThrown = true;
		try {
			persistenceStream.writeObject(object);
		} catch (IOException e) {
			noExceptionThrown = false;
		}
		return noExceptionThrown;
	}

	public static void removeAll(String directoryPath) {
		for(File serializationFile: listSerializationFiles(directoryPath)) {
			serializationFile.delete();	
		}
	}

	public static boolean exists(String absolutePath) {
		return new File(absolutePath).exists();
	}

	public static <E> E readObject(String route) {
		ObjectInputStream persistenceStream = createInputPersistenceStream(route);
		if (persistenceStream == null) return null;
		return readFromPersistenceStream(persistenceStream);
	}

	public static <E> boolean writeObject(E object, String filename) {
		ObjectOutputStream persistenceStream = createOutputPersistenceStream(filename);
		if (persistenceStream == null) return false;
		return writeObjectOnPersistenceStream(object, persistenceStream);
	}

	public static <E> List<E> readAll(String directoryPath) {
		List<E> result =  new ArrayList<E>();
		for(File file : listSerializationFiles(directoryPath)) {
			E readObject = readObject(file.getAbsolutePath());
			result.add(readObject);
		}
		return result;
	}

	
}
