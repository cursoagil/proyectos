package org.uji.agile.contactsbook.tests;

import org.junit.Before;
import org.junit.Test;
import org.uji.agile.contactsbook.Phone;
import org.uji.agile.contactsbook.SpanishPhoneValidator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SpanishPhoneValidatorTest {

	private SpanishPhoneValidator validator;
	private Phone phoneMock;

	@Before
	public void setUp() {
		validator = new SpanishPhoneValidator();
		phoneMock = mock(Phone.class);
	}
	
	@Test
	public void validateShouldCallGetPhoneToGetThePhoneValue() {
		when(phoneMock.getPhone()).thenReturn("");
		validator.validate(phoneMock);
		verify(phoneMock).getPhone();
	}
	
	@Test
	public void validateShouldReturnFalseWhenThePhoneHasLessThanNineCharacters() {
		when(phoneMock.getPhone()).thenReturn("60060066");
		assertFalse(validator.validate(phoneMock));
	}

	@Test
	public void validateShouldReturnFalseWhenThePhoneHasMoreThanNineChars() {
		when(phoneMock.getPhone()).thenReturn("12312341234132123");
		assertFalse(validator.validate(phoneMock));
	}
	
	@Test
	public void validateShouldReturnFalseWhenThePhoneHasNonNumberCharacters() {
		when(phoneMock.getPhone()).thenReturn("abcd98982");
		assertFalse(validator.validate(phoneMock));
	}
	
	@Test
	public void validateShouldReturnTrueWhenThePhoneIsANineCharsNumberStartingWith6() {
		when(phoneMock.getPhone()).thenReturn("612345678");
		assertTrue(validator.validate(phoneMock));
	}
	
	@Test
	public void validateShouldReturnTrueWhenThePhoneIsANineCharsNumberStartingWith7() {
		when(phoneMock.getPhone()).thenReturn("712345678");
		assertTrue(validator.validate(phoneMock));
	}
	
	@Test
	public void validateShouldReturnTrueWhenThePhoneIsANonMobilePhoneNumber() {
		when(phoneMock.getPhone()).thenReturn("964534047");
		assertTrue(validator.validate(phoneMock));
	}
	
}
