package org.uji.agile.contactsbook.tests.acceptance;

import java.util.List;

import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.uji.agile.contactsbook.ContactsBook;
import org.uji.agile.contactsbook.FileStorage;
import org.uji.agile.contactsbook.NotFoundException;
import org.uji.agile.contactsbook.Phone;
import org.uji.agile.contactsbook.PhoneService;
import org.uji.agile.contactsbook.PhoneValidator;
import org.uji.agile.contactsbook.SpanishPhoneValidator;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;
import static org.mockito.Mockito.*;

public class ContactsBookSteps {

	private static final String TEST_PHONE_NUMBER = "606012347";
	private PhoneValidator phoneValidatorMock;
	private PhoneService phoneServiceMock;
	
	@BeforeScenario
	public void beforeScenario() {
		phoneValidatorMock = mock(SpanishPhoneValidator.class);
		phoneServiceMock = mock(PhoneService.class);
		
		when(phoneValidatorMock.validate(Phone.create(TEST_PHONE_NUMBER)))
		.thenReturn(true);
		
		ContactsBook.setPhoneValidator(phoneValidatorMock);
		ContactsBook.setPhoneService(phoneServiceMock);
		ContactsBook.setStorage(new FileStorage()); 
	}
	
	@Given("a non-existing person identified by \"$personName\"")
	public void nonExistingPersonIdentifiedBy(String personName) {
		
	}
	
	@When("a phone number is added to the person \"$personName\"") 
	public void phoneNumberAddedToPersonIdentifiedBy(String personName) {
		ContactsBook.addPhone(TEST_PHONE_NUMBER).to(personName);
	}
	
	@When("the phone is valid")
	public void phoneIsValid() {
		assertTrue(phoneValidatorMock.validate(Phone.create(TEST_PHONE_NUMBER)));
	}
	
	@Then("the person called \"$personName\" is stored")
	public void personIsCreated(String personName) {
		try {
			ContactsBook.getPhonesFromPersonName(personName);
		}
		catch( NotFoundException ex) {
			fail();
		}
	}

	@Then("the phone is validated") 
	public void phoneIsValidated() {
		verify(phoneValidatorMock).validate(Phone.create(TEST_PHONE_NUMBER));
	}
	
	@Then("the phone is added to the person \"$personName\"")
	public void phoneIsAddedToThePerson(String personName) throws NotFoundException {
		List<Phone> phones = ContactsBook.getPhonesFromPersonName(personName);
		assertThat(phones, hasItem(Phone.create(TEST_PHONE_NUMBER)));
	}
	
}

