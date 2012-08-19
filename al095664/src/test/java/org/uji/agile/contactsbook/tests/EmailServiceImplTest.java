package org.uji.agile.contactsbook.tests;

import org.junit.Before;
import org.junit.Test;
import org.uji.agile.contactsbook.Email;
import org.uji.agile.contactsbook.EmailService;
import org.uji.agile.contactsbook.EmailServiceImpl;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class EmailServiceImplTest {

	EmailService emailService;
	
	@Before
	public void setUp() {
		emailService = new EmailServiceImpl();
	}
	
	@Test
	public void sendWithEmptyTopicReturnsFalse() {
		assertFalse(emailService.send(mock(Email.class), "", "body"));
	}

	@Test
	public void sendWithEmptyBodyReturnsFalse() {
		assertFalse(emailService.send(mock(Email.class), "topic", ""));
	}
	
}
