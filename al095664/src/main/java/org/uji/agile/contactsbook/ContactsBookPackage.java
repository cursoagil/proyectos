package org.uji.agile.contactsbook;

public abstract class ContactsBookPackage {
	
	protected Storage storage;
	
	public abstract ContactsBookPackage to(String personName);
	
	protected Person getPersonFromIdentifier(String personName) {
		Person person = null;
		if (storage.exists(personName)) {
			try {
				person = (Person)storage.read(personName);
			} catch (NotFoundIdentifierException e) {
				e.printStackTrace();
			}	
		}
		else {
			person = new Person(personName);
		}
		return person;
	}
	
	public ContactsBookPackage and(String personName) {
		return to(personName);
	}
}
