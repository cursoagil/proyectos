package org.uji.agile.contactsbook;

public class PhoneContactsBookPackage extends ContactsBookPackage {
	private String phoneNumber;
	
	public PhoneContactsBookPackage(String phoneStr, PersonDAO personDAO) {
		this.phoneNumber = phoneStr;
		this.personDAO = personDAO;
	}
	
	public ContactsBookPackage to(String personName) {
		Phone phone = getPhoneFromPendingData();
		Person person = getPersonFromIdentifier(personName);
		person.addPhone(phone);
		personDAO.save(person);
		return this;
	}

	private Phone getPhoneFromPendingData() {
		return Phone.create(phoneNumber);
	}

}