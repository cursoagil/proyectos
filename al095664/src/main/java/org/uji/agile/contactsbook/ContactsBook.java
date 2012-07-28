package org.uji.agile.contactsbook;

public class ContactsBook {

	public static PhoneService phoneService;
	
	public static void setPhoneService(PhoneService service) {
		phoneService = service;
	}

	public static void call(String phone) {
		phoneService.call(Phone.create(phone));
	}

}
