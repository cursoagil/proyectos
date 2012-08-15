package org.uji.agile.contactsbook;

public class ContactsBookData {
	private String pendingData;
	private Storage storage;
	
	public ContactsBookData(String pendingData, Storage storage) {
		this.pendingData = pendingData;
		this.storage = storage;
	}
	
	private boolean hasPendingData() {
		return !pendingData.equals("");
	}
	
	public void to(String personName) {
		if (!hasPendingData()) return;
		Phone phone = getPhoneFromPendingData(pendingData);
		Person person = getPersonFromIdentifier(personName);
		person.addPhone(phone);
		storage.save(person);
	}

	private Phone getPhoneFromPendingData(String pendingData) {
		String phoneNumber = pendingData;
		return Phone.create(phoneNumber);
	}

	private Person getPersonFromIdentifier(String personName) {
		Person person = null;
		if (storage.exists(personName)) {
			try {
				person = (Person)storage.read(personName);
			} catch (NotFoundIdentifierException e) {
				e.printStackTrace();
			}	
		}
		else {
			person = new Person(personName);
		}
		return person;
	}

}