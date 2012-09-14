package org.uji.agile.contactsbook;

public class EmailContactsBookPackage extends ContactsBookPackage {

	private String emailStr;

	public EmailContactsBookPackage(String emailStr, PersonDAO personDAO) {
		this.emailStr = emailStr;
		this.personDAO = personDAO;
	}
	
	@Override
	public ContactsBookPackage to(String personName) {
		Person person = null;
		try {
			person = personDAO.read(personName);
		} catch (NotFoundIdentifierException e) {
			person = new Person(personName);
		}
		person.addEmail(Email.create(emailStr));
		personDAO.save(person);
		return this;
	}

}
