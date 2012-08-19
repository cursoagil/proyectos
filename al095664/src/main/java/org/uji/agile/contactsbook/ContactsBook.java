package org.uji.agile.contactsbook;

import java.util.ArrayList;
import java.util.List;

import org.uji.agile.contactsbook.EmailValidator;

public final class ContactsBook {

	private static PhoneService phoneService;
	private static PhoneValidator phoneValidator;
	private static Storage storage;
	private static EmailValidator emailValidator;
	private static EmailService emailService;
	
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

	public static void setEmailValidator(EmailValidator iEmailValidator) {
		emailValidator = iEmailValidator;
	}

	public static void setEmailService(EmailService iEmailService) {
		emailService = iEmailService;
		
	}

	public static void call(String phone) {
		Phone phoneObject = Phone.create(phone);
		if (phoneValidator.validate(phoneObject)) {
			phoneService.call(Phone.create(phone));	
		}
	}

	public static boolean sendEmail(String emailStr, String topic, String body) {
		Email email = Email.create(emailStr);
		if (!emailValidator.validate(email)) {
			return false;
		}
		return emailService.send(email, topic, body);
	}
	
	public static void addPerson(String personIdentifier) {
		Person person = new Person(personIdentifier);
		storage.save(person);
	}
	
	public static ContactsBookPackage addPhone(String phonenumber) {
		if (phoneValidator.validate(Phone.create(phonenumber))) {
			return new PhoneContactsBookPackage(phonenumber, storage);	
		}
		return new NullContactsBookPackage();
	}

	public static ContactsBookPackage addAddress(String address) {
		return new AddressContactsBookPackage(address, storage);
	}

	public static ContactsBookPackage addEmail(String email) {
		if (emailValidator.validate(Email.create(email))) {
			return new EmailContactsBookPackage(email, storage);	
		}
		return new NullContactsBookPackage();
	}

	public static List<String> getPhonesFromPersonName(String personName) throws NotExistsPersonException {
		Person person = getPersonFromIdentifier(personName);
		
		List<Phone> phones = person.getPhones();
		List<String> result = new ArrayList<String>();
		
		for(Phone phone: phones) {
			result.add(phone.getPhone());
		}
		return result;
	}
	
	public static List<String> getEmailsFromPersonName(String personName) throws NotExistsPersonException {
		Person person = getPersonFromIdentifier(personName);
		List<Email> emails = person.getEmails();
		List<String> result = new ArrayList<String>();
		
		for(Email email: emails) {
			result.add(email.getEmail());
		}
		return result;
	}

	public static List<String> getAddressesFromPersonName(String personName) throws NotExistsPersonException {
		Person person = getPersonFromIdentifier(personName);
		List<Address> addresses = person.getAddresses();
		List<String> result = new ArrayList<String>();
		
		for(Address address: addresses) {
			result.add(address.getAddress());
		}
		return result;
	}

	private static Person getPersonFromIdentifier(String personName)
			throws NotExistsPersonException {
		Person person;
		try {
			person = (Person)storage.read(personName);
		} catch (NotFoundIdentifierException e) {
			throw new NotExistsPersonException();
		}
		return person;
	}
	
}
