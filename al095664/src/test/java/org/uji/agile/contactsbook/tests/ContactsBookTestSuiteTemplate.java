package org.uji.agile.contactsbook.tests;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

import org.uji.agile.contactsbook.ContactsBook;
import org.uji.agile.contactsbook.EmailService;
import org.uji.agile.contactsbook.EmailServiceImpl;
import org.uji.agile.contactsbook.EmailValidator;
import org.uji.agile.contactsbook.FilePersonStorage;
import org.uji.agile.contactsbook.PhoneService;
import org.uji.agile.contactsbook.PhoneValidator;
import org.uji.agile.contactsbook.PersonStorage;

public abstract class ContactsBookTestSuiteTemplate {

	protected PhoneService mockPhoneService;
	protected PhoneValidator mockPhoneValidator;
	protected EmailService mockEmailService;
	protected PersonStorage mockFileStorage;
	protected EmailValidator mockEmailValidator;
	
	protected void setUpContactsBook() {
		initMocks();
		initMocksBehaviour();
		initContactsBook();
	}
	
	protected void initMocks() {
		mockPhoneService = mock(PhoneService.class);
		mockPhoneValidator = mock(PhoneValidator.class);
		mockEmailService = mock(EmailServiceImpl.class, CALLS_REAL_METHODS);
		mockEmailValidator = mock(EmailValidator.class, CALLS_REAL_METHODS);
		mockFileStorage = mock(FilePersonStorage.class, CALLS_REAL_METHODS);
	}

	protected void initContactsBook() {
		ContactsBook.setPhoneService(mockPhoneService);
		ContactsBook.setEmailService(mockEmailService);
		ContactsBook.setPhoneValidator(mockPhoneValidator);
		ContactsBook.setStorage(mockFileStorage);
		ContactsBook.setEmailValidator(mockEmailValidator);
	}
	
	protected abstract void initMocksBehaviour();
}
