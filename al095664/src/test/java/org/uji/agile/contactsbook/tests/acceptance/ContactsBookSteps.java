package org.uji.agile.contactsbook.tests.acceptance;

import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.uji.agile.contactsbook.ContactsBook;
import org.uji.agile.contactsbook.FileStorage;
import org.uji.agile.contactsbook.PersonNotExistsException;
import org.uji.agile.contactsbook.Phone;
import org.uji.agile.contactsbook.PhoneService;
import org.uji.agile.contactsbook.PhoneValidator;
import org.uji.agile.contactsbook.SpanishPhoneValidator;
import org.uji.agile.contactsbook.Storage;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;
import static org.mockito.Mockito.*;

public class ContactsBookSteps {

	private static final String TEST_PHONE_NUMBER = "606012347";
	private static final String TEST_NONVALID_PHONE_NUMBER = "111111111";
	private PhoneValidator phoneValidatorMock;
	private PhoneService phoneServiceMock;
	private Storage storage;
	private List<String> phonesAsStrings;
	
	
	@BeforeScenario
	public void beforeScenario() {
		phoneValidatorMock = mock(SpanishPhoneValidator.class);
		phoneServiceMock = mock(PhoneService.class);
		storage = new FileStorage();
		when(phoneValidatorMock.validate(Phone.create(TEST_PHONE_NUMBER)))
		.thenReturn(true);
		
		phonesAsStrings = new ArrayList<String>();
		
		ContactsBook.setPhoneValidator(phoneValidatorMock);
		ContactsBook.setPhoneService(phoneServiceMock);
		ContactsBook.setStorage(storage); 
	}
	
	@AfterScenario
	public void afterScenario() {
		storage.removeAll();
	}
	
	@Given("a non-existing person identified by \"$personName\"")
	public void nonExistingPersonIdentifiedBy(String personName) {
		assertFalse(storage.exists(personName));
	}
	
	@Given("a person identified by \"$personName\" with phone numbers") 
	public void existingPersonIdentifiedBy(String personName) {
		phonesAsStrings.add("653423121");
		phonesAsStrings.add("912354231");
		
		ContactsBook.addPerson(personName);
		
		for(String phoneString : phonesAsStrings) {
			ContactsBook.addPhone(phoneString).to(personName);
		}
		
	}
	
	@When("a valid phone number is added to the person \"$personName\"") 
	public void phoneNumberAddedToPersonIdentifiedBy(String personName) {
		ContactsBook.addPhone(TEST_PHONE_NUMBER).to(personName);
	}
	
	@When("a non-valid phone number is added to the person \"$personName\"")
	public void nonValidPhoneNumberAddedToPersonIdentifiedBy(String personName) {
		ContactsBook.addPhone(TEST_NONVALID_PHONE_NUMBER).to(personName);
	}
	
	@When("the phone is valid")
	public void phoneIsValid() {
		PhoneValidator phoneValidator = new SpanishPhoneValidator();
		assertTrue(phoneValidator.validate(Phone.create(TEST_PHONE_NUMBER)));
	}
	
	@Then("the person called \"$personName\" is stored")
	public void personIsCreated(String personName) {
		try {
			ContactsBook.getPhonesFromPersonName(personName);
		}
		catch( PersonNotExistsException ex) {
			fail();
		}
	}

	@Then("the person \"$personName\" is not stored")
	public void personIsNotCreated(String personName) {
		boolean personNotExistsExceptionThrown = false;
		try {
			ContactsBook.getPhonesFromPersonName(personName);	
		}
		catch (PersonNotExistsException ex) {
			personNotExistsExceptionThrown = true;
		}
		finally {
			assertTrue(personNotExistsExceptionThrown);
		}
		
	}
	
	@Then("the valid phone is validated") 
	public void validPhoneIsValidated() {
		verify(phoneValidatorMock).validate(Phone.create(TEST_PHONE_NUMBER));
	}
	
	@Then("the non-valid phone is validated") 
	public void nonValidPhoneIsValidated() {
		verify(phoneValidatorMock).validate(Phone.create(TEST_NONVALID_PHONE_NUMBER));
	}
	
	@Then("the phone is added to the person \"$personName\"")
	public void phoneIsAddedToThePerson(String personName) throws PersonNotExistsException {
		List<Phone> phones = ContactsBook.getPhonesFromPersonName(personName);
		assertThat(phones, hasItem(Phone.create(TEST_PHONE_NUMBER)));
	}
	
	@Then("the phone is not added to the person \"$personName\"")
	public void phoneIsNotAddedToThePerson(String personName) throws PersonNotExistsException {
		List<Phone> phones = ContactsBook.getPhonesFromPersonName(personName);
		assertFalse(phones.contains(Phone.create(TEST_NONVALID_PHONE_NUMBER)));
	}
	
	
}

