package org.uji.agile.contactsbook;

public abstract class ContactsBookPackage {
	
	protected PersonStorage personStorage;
	
	public abstract ContactsBookPackage to(String personName);
	
	protected Person getPersonFromIdentifier(String personName) {
		Person person = null;
		if (personStorage.exists(personName)) {
			try {
				person = personStorage.read(personName);
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
