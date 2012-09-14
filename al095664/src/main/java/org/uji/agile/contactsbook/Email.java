package org.uji.agile.contactsbook;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Email implements Serializable {

	private static final long serialVersionUID = -6067721308928442437L;
	private static Map<String, Email> emailInstances;

	static {
		emailInstances = new HashMap<String, Email>();
	}

	private String email;
	
	public Email(String email) {
		this.email = email;
	}
	
	public static Email create(String email) {
		if (!emailInstances.containsKey(email)) {
			emailInstances.put(email, new Email(email));	
		}
		return emailInstances.get(email);
	}
	
	@Override
	public int hashCode() {
		return email.hashCode();
	}

	@Override
	public boolean equals(Object otherEmail) {
		if (!(otherEmail instanceof Email)) {
			return false;
		}
		Email typedOtherEmail = (Email)otherEmail;
		return this.email.equals(typedOtherEmail.email);
	}

	@Override
	public String toString() {
		return email;
	}
	
	public String getEmail() {
		return email;
	}
}
