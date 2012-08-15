package org.uji.agile.contactsbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileStorage implements Storage {

	private static final String SERIALIZED_FILE_EXTENSION = ".ser";
	private static final String STORAGE_DIR = "/tmp";
	
	@Override
	public boolean save(StorageSerializable storageable) {
		String identifier = storageable.getIdentifier();
		ObjectOutputStream persistenceStream = getOutputPersistenceStreamFromIdentifier(identifier);
		if (persistenceStream == null) return false;
		return writeObjectOnPersistenceStream(storageable, persistenceStream);
	}

	@Override
	public StorageSerializable read(String identifier) throws NotFoundIdentifierException {
		ObjectInputStream persistenceStream = getInputPersistenceStreamFromIdentifier(identifier);
		if (persistenceStream == null) {
			throw new NotFoundIdentifierException();
		}
		StorageSerializable readObject = readRawObjectFromPersistenceStream(persistenceStream);
		if (readObject == null) {
			throw new NotFoundIdentifierException();
		}
		return readObject;
	}

	@Override
	public boolean exists(String identifier) {
		File file = new File(buildSerializedFileRoute(identifier));
		return file.exists();	
	}

	@Override
	public void removeAll() {
		File directory = new File(STORAGE_DIR);
		if (directory.isDirectory()) {
			for(File file: directory.listFiles()) {
				if (file.getAbsolutePath().endsWith(SERIALIZED_FILE_EXTENSION)) {
					file.delete();	
				}
			}
		}
	}	
	
	private String buildSerializedFileRoute(String identifier) {
		return STORAGE_DIR + "/" + identifier + SERIALIZED_FILE_EXTENSION;
	}
	
	private ObjectInputStream getInputPersistenceStreamFromIdentifier(String identifier) {
		ObjectInputStream persistenceStream = null;
		
		try {
			persistenceStream = new ObjectInputStream(
									new FileInputStream(buildSerializedFileRoute(identifier))
								);
		} catch (Exception e) {
			persistenceStream = null;
		}
			
		return persistenceStream;
	}
	
	private ObjectOutputStream getOutputPersistenceStreamFromIdentifier(String identifier) {
		ObjectOutputStream persistenceStream = null ;
		try {
			persistenceStream = new ObjectOutputStream(
									new FileOutputStream(buildSerializedFileRoute(identifier))
								);
		} catch (FileNotFoundException e) {
			persistenceStream = null;
		} catch (IOException e) {
			persistenceStream = null;
		}
		return persistenceStream;
	}
	
	private StorageSerializable readRawObjectFromPersistenceStream(ObjectInputStream persistenceStream) {
		Object rawObject = null;
		try {
			rawObject = persistenceStream.readObject();
		} catch (IOException e) {
			rawObject = null;
		} catch (ClassNotFoundException e) {
			rawObject = null;
		}
		return (StorageSerializable) rawObject;
	}
	
	private boolean writeObjectOnPersistenceStream(StorageSerializable object, ObjectOutputStream persistenceStream) {
		boolean isOK = true;
		try {
			persistenceStream.writeObject(object);
		} catch (IOException e) {
			isOK = false;
		}
		return isOK;
	}
}
