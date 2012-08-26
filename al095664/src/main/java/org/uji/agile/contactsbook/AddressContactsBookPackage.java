package org.uji.agile.contactsbook;

public class AddressContactsBookPackage extends ContactsBookPackage {

	private Address pendingAddress;

	public AddressContactsBookPackage(String address, PersonStorage storage) {
		pendingAddress = Address.create(address);
		this.personStorage = storage;
	}
	
	@Override
	public AddressContactsBookPackage to(String personName) {
		Person person = getPersonFromIdentifier(personName);
		person.addAddress(pendingAddress);
		personStorage.save(person);
		return this;
	}
	
}
