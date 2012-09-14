package org.uji.agile.contactsbook;

public abstract class ContactsBookPackage {
	
	protected PersonDAO personDAO;
	
	public abstract ContactsBookPackage to(String personName);
	
	protected Person getPersonFromIdentifier(String personName) {
		Person person = null;
		try {
			person = personDAO.read(personName);
		} catch (NotFoundIdentifierException e) {
			person = new Person(personName);
		}	

		return person;
	}
	
	public ContactsBookPackage and(String personName) {
		return to(personName);
	}
}
