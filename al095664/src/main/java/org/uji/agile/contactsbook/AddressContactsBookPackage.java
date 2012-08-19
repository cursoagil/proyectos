package org.uji.agile.contactsbook;

public class AddressContactsBookPackage extends ContactsBookPackage {

	private Address pendingAddress;

	public AddressContactsBookPackage(String address, Storage storage) {
		pendingAddress = Address.create(address);
		this.storage = storage;
	}
	
	@Override
	public AddressContactsBookPackage to(String personName) {
		Person person = getPersonFromIdentifier(personName);
		person.addAddress(pendingAddress);
		storage.save(person);
		return this;
	}
	
}
