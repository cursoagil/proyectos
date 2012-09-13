package org.uji.agile.contactsbook;

public class NullContactsBookPackage extends ContactsBookPackage {

	@Override
	public ContactsBookPackage to(String personName) {
		return this;
	}

}
