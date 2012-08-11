package org.uji.agile.contactsbook;

import java.util.ArrayList;
import java.util.List;

public class Person implements StorageSerializable {

	private static final long serialVersionUID = 1352775845608894734L;
	
	private String name;
	
	private List<Phone> phones;
	
	public Person() {
		this.name = "";
		phones = new ArrayList<Phone>();
	}
	
	public Person(String name) {
		this.name = name;
		phones = new ArrayList<Phone>();
	}
	
	public void addPhone(Phone phone) {
		phones.add(phone);
	}

	public List<Phone> getPhones() {
		return phones;
	}

	@Override
	public String getIdentifier() {
		return name;
	}

}
