package org.uji.agile.contactsbook;

public interface Storage {

	Iterable<Phone> getPhonesFromPerson(Person person);
	
}
