package org.uji.agile.contactsbook.tests;

import org.junit.Before;
import org.junit.Test;
import org.uji.agile.contactsbook.ContactsBook;
import org.uji.agile.contactsbook.Phone;
import org.uji.agile.contactsbook.PhoneService;

import static org.mockito.Mockito.*;

public class ContactsBookTest {

	private PhoneService mockPhoneService;

	@Before
	public void setUp() {
		mockPhoneService = mock(PhoneService.class);
		ContactsBook.setPhoneService(mockPhoneService);
	}
	
	@Test
	public void contactsBookIsAbleToCallUsingAPhoneCallerService() {
		ContactsBook.call("606912312");
		verify(mockPhoneService).call(Phone.create("606912312"));
	}
	
}
