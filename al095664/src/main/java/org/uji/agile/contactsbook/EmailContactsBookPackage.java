package org.uji.agile.contactsbook;

public class EmailContactsBookPackage implements ContactsBookPackage {

	private String emailStr;
	private Storage storage;

	public EmailContactsBookPackage(String emailStr, Storage storage) {
		this.emailStr = emailStr;
		this.storage = storage;
	}
	
	@Override
	public void to(String personName) {
		Person person = null;
		try {
			person = (Person)storage.read(personName);
		} catch (NotFoundIdentifierException e) {
			person = new Person(personName);
		}
		person.addEmail(Email.create(emailStr));
		storage.save(person);
	}

}
