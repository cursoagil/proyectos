package org.uji.agile.contactsbook.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.uji.agile.contactsbook.Phone;

public class PhoneTest {

	@Test
	public void twoPhonesWithTheSamePhoneNumberButDifferentObjectsAreEqual() {
		assertEquals(new Phone("606606606"), new Phone("606606606"));
	}
	
	@Test
	public void twoPhonesWithDifferentPhoneNumbersAreNotEqual() {
		assertFalse(new Phone("706606606").equals( new Phone("606606606") ));
	}
	
	@Test
	public void equalsReturnsTwoForDifferentPhonesWithTheSamePhoneNumber() {
		Phone phone = new Phone("600343212");
		Phone equalPhone = new Phone("600343212");
		
		assertTrue(phone.equals(equalPhone));
		assertTrue(equalPhone.equals(phone));
	}
}
