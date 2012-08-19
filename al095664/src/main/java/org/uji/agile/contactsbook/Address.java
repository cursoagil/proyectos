package org.uji.agile.contactsbook;

import java.io.Serializable;

public class Address implements Serializable{

	private static final long serialVersionUID = 9166848730632139751L;
	private String address;

	public Address(String address) {
		this.address = address;
	}

	public static Address create(String address) {
		return new Address(address);
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Address)) return false; 
		Address otherAddress = (Address)other;
		return address.equals(otherAddress.address);
	}

	@Override
	public int hashCode() {
		return address.hashCode();
	}
	
}
