package org.uji.agile.contactsbook;

import java.util.List;

public class ContactsBook {

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
		return new ContactsBook(phonenumber);
	}
	
	public void to(String personName) {
		String phoneNumber = this.pendingData;
		Phone phone = Phone.create(phoneNumber);
		
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
		person.addPhone(phone);
		storage.save(person);
	}

	public static void setStorage(Storage iStorage) {
		storage = iStorage;
	}

	public static List<Phone> getPhonesFromPersonName(String personName) throws NotFoundIdentifierException {
		Person person = null;
		person = (Person)storage.read(personName);	
		return person.getPhones();
	}
	
}
