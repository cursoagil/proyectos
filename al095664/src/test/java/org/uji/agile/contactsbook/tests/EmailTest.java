package org.uji.agile.contactsbook.tests;

import org.junit.Test;
import org.uji.agile.contactsbook.Email;

import static org.junit.Assert.assertEquals;

public class EmailTest {

	@Test
	public void twoEmailsAreEqualWhenTheyHaveTheSameLiteral() {
		String email = "j@gmail.com";
		String copiedEmail = String.format("%s", email);
		
		assertEquals(new Email(email), new Email(copiedEmail));
	}
	
	@Test
	public void twoEmailsHaveTheSameHashCode() {
		String email = "j@gmail.com";
		String copiedEmail = String.format("%s", email);
		
		assertEquals(new Email(email).hashCode(), new Email(copiedEmail).hashCode());
	}
	
}
