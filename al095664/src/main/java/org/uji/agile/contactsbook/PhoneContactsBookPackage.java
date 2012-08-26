package org.uji.agile.contactsbook;

public class PhoneContactsBookPackage extends ContactsBookPackage {
	private String phoneNumber;
	
	public PhoneContactsBookPackage(String phoneStr, PersonStorage storage) {
		this.phoneNumber = phoneStr;
		this.personStorage = storage;
	}
	
	public ContactsBookPackage to(String personName) {
		Phone phone = getPhoneFromPendingData();
		Person person = getPersonFromIdentifier(personName);
		person.addPhone(phone);
		personStorage.save(person);
		return this;
	}

	private Phone getPhoneFromPendingData() {
		return Phone.create(phoneNumber);
	}

}