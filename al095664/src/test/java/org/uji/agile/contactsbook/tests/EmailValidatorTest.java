package org.uji.agile.contactsbook.tests;

import org.junit.Before;
import org.junit.Test;
import org.uji.agile.contactsbook.Email;
import org.uji.agile.contactsbook.EmailValidator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
public class EmailValidatorTest {

	private EmailValidator emailValidator;
	private Email emailMock;

	@Before
	public void setUp() {
		emailValidator = new EmailValidator();
		emailMock = mock(Email.class);
		when(emailMock.getEmail()).thenReturn("");
	}
	
	@Test
	public void validateShouldCallGetEmailMethod() {
		emailValidator.validate(emailMock);
		verify(emailMock).getEmail();
	}
	
	@Test
	public void validateShouldReturnFalseWhenEmailHasntTheUsername() {
		when(emailMock.getEmail()).thenReturn("@gmail.com");
		assertFalse(emailValidator.validate(emailMock));
	}
	
	@Test
	public void validateShouldReturnFalseWhenDomainHasntDot() {
		when(emailMock.getEmail()).thenReturn("jorgonor88@gmail");
		assertFalse(emailValidator.validate(emailMock));
	}
	
	@Test
	public void validateShouldReturnFalseWhenHigherOrderDomainHasMoreThanThreeCharacters() {
		when(emailMock.getEmail()).thenReturn("jorgonor88@gmail.comcom");
		assertFalse(emailValidator.validate(emailMock));
	}
	
	@Test
	public void validateShouldReturnFalseWhenHigherOrderDomainHasLessThanTwoCharacters() {
		when(emailMock.getEmail()).thenReturn("jorgonor88@gmail.c");
		assertFalse(emailValidator.validate(emailMock));
	}
	
	@Test
	public void validateShouldReturnTrueOnWellFormedEmail() {
		when(emailMock.getEmail()).thenReturn("jorgonor88@gmail.com");
		assertTrue(emailValidator.validate(emailMock));
	}
	
	@Test
	public void validateShouldReturnTrueOnWellFormedEmailWithTwoCharactersAsHigherOrderDomain() {
		when(emailMock.getEmail()).thenReturn("jorgonor88@gmail.co");
		assertTrue(emailValidator.validate(emailMock));
	}
	
}
