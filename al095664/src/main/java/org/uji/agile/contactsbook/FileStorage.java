package org.uji.agile.contactsbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileStorage implements Storage {

	private static final String STORAGE_DIR = "/tmp";
	
	@Override
	public boolean save(StorageSerializable storageable) {
		String identifier = storageable.getIdentifier();
		ObjectOutputStream persistenceStream = null ;
		try {
			persistenceStream = new ObjectOutputStream(
									new FileOutputStream(buildSerializedFileRoute(identifier))
								);
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		
		try {
			persistenceStream.writeObject(storageable);
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}

	private String buildSerializedFileRoute(String identifier) {
		return STORAGE_DIR + "/" + identifier + ".ser";
	}

	@Override
	public StorageSerializable read(String identifier) throws NotFoundException {
		ObjectInputStream persistenceStream = null;
		
		try {
			persistenceStream = new ObjectInputStream(
									new FileInputStream(buildSerializedFileRoute(identifier))
								);
		} catch (FileNotFoundException e) {
			throw new NotFoundException();
		} catch (IOException e) {
			throw new NotFoundException();
		}
		
		Object rawObject = null;
		try {
			rawObject = persistenceStream.readObject();
		} catch (IOException e) {
			throw new NotFoundException();
		} catch (ClassNotFoundException e) {
			throw new NotFoundException();
		}
		
		return (StorageSerializable) rawObject;
	}	
}
