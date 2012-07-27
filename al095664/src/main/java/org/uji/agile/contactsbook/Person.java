package org.uji.agile.contactsbook;

import java.util.ArrayList;
import java.util.List;

public class Person {

	List<Phone> phones;
	
	public Person() {
		phones = new ArrayList<Phone>();
	}
	
	public void addPhone(Phone phone) {
		phones.add(phone);
	}

	public Iterable<Phone> getPhones() {
		return phones;
	}

}
