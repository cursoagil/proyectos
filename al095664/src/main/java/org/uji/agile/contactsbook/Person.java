package org.uji.agile.contactsbook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Person implements Serializable {

	private static final long serialVersionUID = 1352775845608894734L;
	
	private String name;
	
	private List<Phone> phones;
	private List<Email> emails;
	private List<Address> addresses;
	 
	public Person() {
		this.name = "";
		initLists();
	}

	public Person(String name) {
		this.name = name;
		initLists();
	}
	
	private void initLists() {
		phones = new ArrayList<Phone>();
		emails = new ArrayList<Email>();
		addresses = new ArrayList<Address>();
	}

	public String getIdentifier() {
		return name;
	}

	
	public void addPhone(Phone phone) {
		phones.add(phone);
	}

	public void addEmail(Email email) {
		emails.add(email);
	}

	public void addAddress(Address address) {
		addresses.add(address);
	}
	
	public List<Phone> getPhones() {
		return phones;
	}
	
	public List<Email> getEmails() {
		return emails;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

}
