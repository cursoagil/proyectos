package org.uji.agile.contactsbook;

import java.util.ArrayList;
import java.util.List;

import org.uji.agile.contactsbook.internals.ObjectSerializer;

public class FilePersonDAO implements PersonDAO {
	
	private static final String STORAGE_DIR = "/tmp";
	
	@Override
	public boolean save(Person person) {
		String identifier = person.getIdentifier();
		return ObjectSerializer.writeObject(person, buildSerializedFileRoute(identifier));
	}

	@Override
	public Person read(String identifier) throws NotFoundIdentifierException {
		Person readPerson = ObjectSerializer.readObject(buildSerializedFileRoute(identifier));
		if (readPerson == null) {
			throw new NotFoundIdentifierException();
		}
		return readPerson;
	}

	@Override
	public boolean exists(String identifier) {
		return ObjectSerializer.exists(buildSerializedFileRoute(identifier));
	}

	@Override
	public void removeAll() {
		ObjectSerializer.removeAll(STORAGE_DIR);
	}	
	
	@Override
	public List<Person> search(String substring) {
		List<Person> result = new ArrayList<Person>();
		List<Person> everyone = ObjectSerializer.readAll(STORAGE_DIR);
		for(Person person : everyone) {
			if (person.getIdentifier().contains(substring)) {
				result.add(person);
			}
		}
		
		return result;
	}
	
	private static String buildSerializedFileRoute(String identifier) {
		return STORAGE_DIR + "/" + identifier + ObjectSerializer.SERIALIZED_FILE_EXTENSION;
	}
}
