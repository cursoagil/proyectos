package org.uji.agile.contactsbook.tests.integration;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uji.agile.contactsbook.ContactsBook;
import org.uji.agile.contactsbook.Email;
import org.uji.agile.contactsbook.NotExistsPersonException;
import org.uji.agile.contactsbook.Phone;
import org.uji.agile.contactsbook.tests.ContactsBookTestSuiteTemplate;

import static org.junit.matchers.JUnitMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ContactsBookTest extends ContactsBookTestSuiteTemplate {

	private static final String TEST_EMAIL_STR = "jorgonor88@gmail.com";
	
	@Before
	public void setUp() {
		setUpContactsBook();
	}

	protected void initMocksBehaviour() {
		when(mockPhoneValidator.validate(Phone.create("606912312"))).thenReturn(true);
		when(mockEmailService.send(Email.create(TEST_EMAIL_STR), "Hello", "Hello World!")).thenReturn(true);
	}
	
	@After
	public void tearDown() {
		mockFileStorage.removeAll();
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
	public void addPhoneAllowsToAddPhonesToPerson() throws NotExistsPersonException  {
		ContactsBook.addPhone("606912312").to("Jaime");
		assertThat(ContactsBook.getPhonesFromPersonName("Jaime"), hasItem("606912312"));
	}
	
	@Test
	public void addPersonTakesReadyPersonInTheSystem() throws NotExistsPersonException  {
		ContactsBook.addPerson("Anyone");
		ContactsBook.getPhonesFromPersonName("Anyone");
	}
	
	@Test
	public void addPhoneAfterAddedPersonStoresHisPhonesToo() throws NotExistsPersonException {
		String personName = "Someone";
		List<String> phoneStrings = new ArrayList<String>();
		phoneStrings.add("656656656");
		phoneStrings.add("656656657");
		
		ContactsBook.addPerson(personName);
		
		for(String phoneString : phoneStrings) {
			when(mockPhoneValidator.validate(Phone.create(phoneString))).thenReturn(true);
			ContactsBook.addPhone(phoneString).to(personName);
		}
		
		List<String> retrievedPhones = ContactsBook.getPhonesFromPersonName(personName);
		
		for(String phoneString : phoneStrings ) {
			assertThat(retrievedPhones, hasItem(phoneString));
		}
	}
	
	@Test
	public void getEmailsFromPersonNameReturnsTheEmailsItHadBeforeSaving() throws NotExistsPersonException {
		String personName = "Someone";
		String email = "email@domain.com";
		
		ContactsBook.addPerson(personName);
		ContactsBook.addEmail(email).to(personName);
		
		assertThat(ContactsBook.getEmailsFromPersonName(personName), 
				   hasItem(email));
	}
	
	@Test
	public void emailIsNotStoredIfItIsnotValid() throws NotExistsPersonException {
		String personName = "Jorge";
		String email = "jorgonor88@gmail";
		
		ContactsBook.addPerson(personName);
		ContactsBook.addEmail(email).to(personName);

		assertEquals(0, ContactsBook.getEmailsFromPersonName(personName).size());
		
	}
	
	@Test
	public void sendEmailCallsSendOverTheEmailService() {
		ContactsBook.sendEmail(TEST_EMAIL_STR, "Hello", "Hello World!");
		verify(mockEmailService).send(Email.create(TEST_EMAIL_STR), "Hello", "Hello World!");
		
	}
	
	@Test
	public void sendEmailValidatesEmail() {
		ContactsBook.sendEmail(TEST_EMAIL_STR, "Hello", "Hello World!");
		verify(mockEmailValidator).validate(Email.create(TEST_EMAIL_STR));
	}

	@Test
	public void sendEmailWhenEmailIsValidReturnsTrue() {
		assertTrue(ContactsBook.sendEmail(TEST_EMAIL_STR, "Hello", "Hello World!"));
	}
	
	@Test
	public void sendEmailWhenEmailIsNotValidReturnsFalse() {
		assertFalse(ContactsBook.sendEmail("masdmasdas", "Hello", "Hello World!"));
	}
	
	@Test
	public void addAddressShouldStoreAddress() throws NotExistsPersonException {
		String personName = "Jaime";
		String address = "C/ La Huerta nº 1";
		ContactsBook.addAddress(address).to(personName);
		
		assertThat(ContactsBook.getAddressesFromPersonName(personName), hasItem(address));
	}
	
}