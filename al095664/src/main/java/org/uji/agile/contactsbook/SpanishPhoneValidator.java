package org.uji.agile.contactsbook;

public class SpanishPhoneValidator implements PhoneValidator {

	private boolean isMobilePhone(String phonenumber) {
		return phonenumber.matches("^[6-7][0-9]{8}$");
	}
	
	private boolean isLandLinePhone(String phonenumber) {
		return phonenumber.matches("^9[0-9]{8}$");
	}
	
	@Override
	public boolean validate(Phone phone) {
		String phonenumber = phone.getPhone();
		return isMobilePhone(phonenumber) || isLandLinePhone(phonenumber);
	}

}
