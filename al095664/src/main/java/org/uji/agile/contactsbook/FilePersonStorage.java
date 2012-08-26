package org.uji.agile.contactsbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FilePersonStorage implements PersonStorage {

	private static final String SERIALIZED_FILE_EXTENSION = ".ser";
	private static final String STORAGE_DIR = "/tmp";
	
	@Override
	public boolean save(Person person) {
		String identifier = person.getIdentifier();
		ObjectOutputStream persistenceStream = getOutputPersistenceStreamFromIdentifier(identifier);
		if (persistenceStream == null) return false;
		return writeObjectOnPersistenceStream(person, persistenceStream);
	}

	@Override
	public Person read(String identifier) throws NotFoundIdentifierException {
		ObjectInputStream persistenceStream = getInputPersistenceStreamFromIdentifier(identifier);
		if (persistenceStream == null) {
			throw new NotFoundIdentifierException();
		}
		Person readPerson = readPersonFromPersistenceStream(persistenceStream);
		if (readPerson == null) {
			throw new NotFoundIdentifierException();
		}
		return readPerson;
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
	
	public List<Person> search(String substring) {
		List<Person> result = new ArrayList<Person>();
		
		for(File personFile : listPersonFiles()) {
			String fileName = removeExtension(personFile.getName());
			if (fileName.contains(substring)) {
				result.add(readPersonFromPersistenceStream(
					getInputPersistenceStreamFromRoute(personFile.getAbsolutePath())
				));
			}
		}
		
		return result;
	}
	
	private String buildSerializedFileRoute(String identifier) {
		return STORAGE_DIR + "/" + identifier + SERIALIZED_FILE_EXTENSION;
	}
	
	private ObjectInputStream getInputPersistenceStreamFromIdentifier(String identifier) {
		return getInputPersistenceStreamFromRoute(
				buildSerializedFileRoute(identifier)
		);
	}
	
	private ObjectInputStream getInputPersistenceStreamFromRoute(String route) {
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
	
	private Person readPersonFromPersistenceStream(ObjectInputStream persistenceStream) {
		Object rawObject = null;
		try {
			rawObject = persistenceStream.readObject();
		} catch (IOException e) {
			rawObject = null;
		} catch (ClassNotFoundException e) {
			rawObject = null;
		}
		return (Person) rawObject;
	}
	
	private boolean writeObjectOnPersistenceStream(Person object, ObjectOutputStream persistenceStream) {
		boolean isOK = true;
		try {
			persistenceStream.writeObject(object);
		} catch (IOException e) {
			isOK = false;
		}
		return isOK;
	}

	private List<File> listPersonFiles() {
		List<File> personFiles = new ArrayList<File>();
		File directory = new File(STORAGE_DIR);
		if (directory.isDirectory()) {
			for(File file: directory.listFiles()) {
				if (file.getAbsolutePath().endsWith(SERIALIZED_FILE_EXTENSION)) {
					personFiles.add(file);
				}
			}
		}
		return personFiles;
	}
	
	private static String removeExtension(String filename) {
		int index = filename.lastIndexOf(SERIALIZED_FILE_EXTENSION);
		if (index >= 0) {
			filename = filename.substring(0, index);
		}
		return filename;
	}
	
	
}
