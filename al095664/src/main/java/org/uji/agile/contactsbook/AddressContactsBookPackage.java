package org.uji.agile.contactsbook;

public class AddressContactsBookPackage extends ContactsBookPackage {

	private Address pendingAddress;

	public AddressContactsBookPackage(String address, PersonDAO personDAO) {
		pendingAddress = Address.create(address);
		this.personDAO = personDAO;
	}
	
	@Override
	public AddressContactsBookPackage to(String personName) {
		Person person = getPersonFromIdentifier(personName);
		person.addAddress(pendingAddress);
		personDAO.save(person);
		return this;
	}
	
}
