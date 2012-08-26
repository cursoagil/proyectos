package org.uji.agile.contactsbook;

public class EmailContactsBookPackage extends ContactsBookPackage {

	private String emailStr;

	public EmailContactsBookPackage(String emailStr, PersonStorage storage) {
		this.emailStr = emailStr;
		this.personStorage = storage;
	}
	
	@Override
	public ContactsBookPackage to(String personName) {
		Person person = null;
		try {
			person = personStorage.read(personName);
		} catch (NotFoundIdentifierException e) {
			person = new Person(personName);
		}
		person.addEmail(Email.create(emailStr));
		personStorage.save(person);
		return this;
	}

}
