package org.uji.agile.contactsbook;

import java.util.List;

public final class ContactsBook {

	private static PhoneService phoneService;
	private static PhoneValidator phoneValidator;
	private static Storage storage;
	
	private String pendingData;
	
	private ContactsBook(String data) {
		pendingData = data;
	}
	
	public static void setPhoneService(PhoneService service) {
		phoneService = service;
	}

	public static void call(String phone) {
		Phone phoneObject = Phone.create(phone);
		if (phoneValidator.validate(phoneObject)) {
			phoneService.call(Phone.create(phone));	
		}
	}

	public static void setPhoneValidator(PhoneValidator validator) {
		phoneValidator = validator;
	}

	public static ContactsBook addPhone(String phonenumber) {
		if (phoneValidator.validate(Phone.create(phonenumber))) {
			return new ContactsBook(phonenumber);	
		}
		return new ContactsBook("");
	}
	
	private boolean hasPendingData() {
		return !pendingData.equals("");
	}
	
	public void to(String personName) {
		if (!hasPendingData()) return;
		Phone phone = getPhoneFromPendingData(pendingData);
		Person person = getPersonFromIdentifier(personName);
		person.addPhone(phone);
		storage.save(person);
	}

	private static Phone getPhoneFromPendingData(String pendingData) {
		String phoneNumber = pendingData;
		return Phone.create(phoneNumber);
	}

	private static Person getPersonFromIdentifier(String personName) {
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

	public static void setStorage(Storage iStorage) {
		storage = iStorage;
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
	
}
