package org.uji.agile.contactsbook.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.uji.agile.contactsbook.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertSame;

public class FileStorageTest {

	private FileStorage storage;

	@Before
	public void setUp() {
		storage = new FileStorage();
	}
	
	@Test
	public void storageRecoversPhonesFromPerson() {
		Person person = mock(Person.class);

		List<Phone> phones = new ArrayList<Phone>();
		phones.add(mock(Phone.class));
		
		when(person.getPhones()).thenReturn(phones);
		
		Iterable<Phone> recoveredPhones =
				storage.getPhonesFromPerson(person);

		verify(person).getPhones();

		assertSame(phones, recoveredPhones);
		
	}
	
}
