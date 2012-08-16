package org.uji.agile.contactsbook;

import java.util.List;

public final class ContactsBook {

	private static PhoneService phoneService;
	private static PhoneValidator phoneValidator;
	private static Storage storage;
	
	private ContactsBook() {}
	
	public static void setPhoneService(PhoneService service) {
		phoneService = service;
	}

	public static void setPhoneValidator(PhoneValidator validator) {
		phoneValidator = validator;
	}

	public static void setStorage(Storage iStorage) {
		storage = iStorage;
	}

	public static void call(String phone) {
		Phone phoneObject = Phone.create(phone);
		if (phoneValidator.validate(phoneObject)) {
			phoneService.call(Phone.create(phone));	
		}
	}
	
	public static List<Phone> getPhonesFromPersonName(String personName) throws PersonNotExistsException {
		Person person = null;
		try {
			person = (Person)storage.read(personName);		
		}
		catch (NotFoundIdentifierException ex) {
			throw new PersonNotExistsException();
		}
		return person.getPhones();
	}

	public static void addPerson(String personIdentifier) {
		Person person = new Person(personIdentifier);
		storage.save(person);
	}
	
	public static ContactsBookPackage addPhone(String phonenumber) {
		if (phoneValidator.validate(Phone.create(phonenumber))) {
			return new PhoneContactsBookPackage(phonenumber, storage);	
		}
		return new PhoneContactsBookPackage("", storage);
	}

	public static ContactsBookPackage addEmail(String email) {
		return new EmailContactsBookPackage(email, storage);
	}

	public static List<Email> getEmailsFromPersonName(String personName) throws NotFoundIdentifierException {
		Person person = (Person)storage.read(personName);
		return person.getEmails();
	}

}
