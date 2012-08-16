package org.uji.agile.contactsbook.tests.integration;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uji.agile.contactsbook.ContactsBook;
import org.uji.agile.contactsbook.Email;
import org.uji.agile.contactsbook.FileStorage;
import org.uji.agile.contactsbook.NotFoundIdentifierException;
import org.uji.agile.contactsbook.PersonNotExistsException;
import org.uji.agile.contactsbook.Phone;
import org.uji.agile.contactsbook.PhoneService;
import org.uji.agile.contactsbook.PhoneValidator;
import org.uji.agile.contactsbook.Storage;

import static org.junit.matchers.JUnitMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ContactsBookTest {

	private PhoneService mockPhoneService;
	private PhoneValidator mockPhoneValidator;
	private Storage fileStorage;
	@Before
	public void setUp() {
		mockPhoneService = mock(PhoneService.class);
		mockPhoneValidator = mock(PhoneValidator.class);
		when(mockPhoneValidator.validate(Phone.create("606912312"))).thenReturn(true);

		fileStorage = new FileStorage();
		
		ContactsBook.setPhoneService(mockPhoneService);
		ContactsBook.setPhoneValidator(mockPhoneValidator);
		ContactsBook.setStorage(fileStorage);
		
	}

	@After
	public void tearDown() {
		fileStorage.removeAll();
	}
	
	@Test
	public void callIsAbleToCallUsingAPhoneCallerService() {
		ContactsBook.call("606912312");
		verify(mockPhoneService).call(Phone.create("606912312"));
	}
	
	@Test
	public void callValidatesThePhoneNumber() {
		ContactsBook.call("606912312");
		verify(mockPhoneValidator).validate(Phone.create("606912312"));
	}
	
	@Test
	public void callDoesntExecuteTheCallMethodOnPhoneServiceWhenThePhoneNumberIsNotValid() {
		String phonenumber = "123412332"; 
		ContactsBook.call(phonenumber);
		verify(mockPhoneValidator).validate(Phone.create(phonenumber));
		verify(mockPhoneService, never()).call(Phone.create(phonenumber));
	}
	
	@Test
	public void addPhoneAllowsToAddPhonesToPerson() throws PersonNotExistsException  {
		ContactsBook.addPhone("606912312").to("Jaime");
		assertThat(ContactsBook.getPhonesFromPersonName("Jaime"), hasItem(Phone.create("606912312")));
	}
	
	@Test
	public void addPersonTakesReadyPersonInTheSystem() throws PersonNotExistsException  {
		ContactsBook.addPerson("Anyone");
		ContactsBook.getPhonesFromPersonName("Anyone");
	}
	
	@Test
	public void addPhoneAfterAddedPersonStoresHisPhonesToo() throws PersonNotExistsException {
		String personName = "Someone";
		List<String> phoneStrings = new ArrayList<String>();
		phoneStrings.add("656656656");
		phoneStrings.add("656656657");
		
		ContactsBook.addPerson(personName);
		
		for(String phoneString : phoneStrings) {
			when(mockPhoneValidator.validate(Phone.create(phoneString))).thenReturn(true);
			ContactsBook.addPhone(phoneString).to(personName);
		}
		
		List<Phone> retrievedPhones = ContactsBook.getPhonesFromPersonName(personName);
		
		for(String phoneString : phoneStrings ) {
			assertThat(retrievedPhones, hasItem(Phone.create(phoneString)));
		}
	}
	
	@Test
	public void getEmailsFromPersonNameReturnsTheEmailsItHadBeforeSaving() throws NotFoundIdentifierException {
		String personName = "Someone";
		String email = "email@domain.com";
		
		ContactsBook.addPerson(personName);
		ContactsBook.addEmail(email).to(personName);
		
		assertThat(ContactsBook.getEmailsFromPersonName(personName), 
				   hasItem(Email.create(email)));
	}
}
