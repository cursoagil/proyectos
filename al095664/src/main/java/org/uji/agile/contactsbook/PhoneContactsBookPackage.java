package org.uji.agile.contactsbook;

public class PhoneContactsBookPackage extends ContactsBookPackage {
	private String phoneNumber;
	
	public PhoneContactsBookPackage(String phoneStr, Storage storage) {
		this.phoneNumber = phoneStr;
		this.storage = storage;
	}
	
	public void to(String personName) {
		Phone phone = getPhoneFromPendingData();
		Person person = getPersonFromIdentifier(personName);
		person.addPhone(phone);
		storage.save(person);
	}

	private Phone getPhoneFromPendingData() {
		return Phone.create(phoneNumber);
	}

}