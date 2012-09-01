package org.uji.agile.contactsbook;

import org.uji.agile.contactsbook.Email;

public class EmailValidator {

	private static final String EMAIL_REGEXP = "^[a-zA-z][a-zA-z0-9]*@[a-zA-z][a-zA-z0-9]*\\.[a-z][a-z][a-z]?$";
	
	public boolean validate(Email email) {
		String emailStr = email.getEmail();
		return emailStr.matches(EMAIL_REGEXP);
	}

}
