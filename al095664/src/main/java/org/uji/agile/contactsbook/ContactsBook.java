package org.uji.agile.contactsbook;

public class ContactsBook {

	private static PhoneService phoneService;

	private ContactsBook() {
	}
	
	public static void setPhoneService(PhoneService service) {
		phoneService = service;
	}

	public static void call(String phone) {
		phoneService.call(Phone.create(phone));
	}

}
