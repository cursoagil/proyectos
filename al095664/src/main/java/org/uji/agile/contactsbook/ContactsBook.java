package org.uji.agile.contactsbook;

public class ContactsBook {

	private static PhoneService phoneService;
	private static PhoneValidator phoneValidator;
	
	private ContactsBook() {
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

}
