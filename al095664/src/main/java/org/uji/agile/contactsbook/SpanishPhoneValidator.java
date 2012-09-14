package org.uji.agile.contactsbook;

public class SpanishPhoneValidator implements PhoneValidator {

	private static final String REGEXP_LAND_LINE_PHONE = "^9[0-9]{8}$";
	private static final String REGEXP_MOBILE_PHONE = "^[6-7][0-9]{8}$";

	private boolean isMobilePhone(String phonenumber) {
		return phonenumber.matches(REGEXP_MOBILE_PHONE);
	}
	
	private boolean isLandLinePhone(String phonenumber) {
		return phonenumber.matches(REGEXP_LAND_LINE_PHONE);
	}
	
	@Override
	public boolean validate(Phone phone) {
		String phonenumber = phone.getPhone();
		return isMobilePhone(phonenumber) || isLandLinePhone(phonenumber);
	}

}
