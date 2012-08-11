package org.uji.agile.contactsbook;

public class SpanishPhoneValidator implements PhoneValidator {

	@Override
	public boolean validate(Phone phone) {
		String phonenumber = phone.getPhone();
		return phonenumber.matches("^([6-7]|9)[0-9]{8}$"); 
	}

}
