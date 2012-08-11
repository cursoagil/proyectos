package org.uji.agile.contactsbook;

import java.io.File;
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
	public StorageSerializable read(String identifier) throws NotFoundIdentifierException {
		ObjectInputStream persistenceStream = null;
		
		boolean anyExceptionThrown = false;
		
		try {
			persistenceStream = new ObjectInputStream(
									new FileInputStream(buildSerializedFileRoute(identifier))
								);
		} catch (FileNotFoundException e) {
			anyExceptionThrown = true;
		} catch (IOException e) {
			anyExceptionThrown = true;
		} finally {
			if (anyExceptionThrown) throw new NotFoundIdentifierException();
		}
		
		Object rawObject = null;
		try {
			rawObject = persistenceStream.readObject();
		} catch (IOException e) {
			anyExceptionThrown = true;
		} catch (ClassNotFoundException e) {
			anyExceptionThrown = true;
		}
		finally {
			if (anyExceptionThrown) throw new NotFoundIdentifierException();
		}

		return (StorageSerializable) rawObject;
	}

	@Override
	public boolean exists(String identifier) {
		File file = new File(buildSerializedFileRoute(identifier));
		return file.exists();	
	}	
}
