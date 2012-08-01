package org.uji.agile.contactsbook;

public class FileStorage implements Storage {

	@Override
	public Iterable<Phone> getPhonesFromPerson(Person person) {
		return person.getPhones();
	}

}
