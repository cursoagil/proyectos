package org.uji.agile.contactsbook.tests;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uji.agile.contactsbook.*;

import static org.junit.Assert.*;

public class FileStorageTest {

	private FileStorage storage;

	@Before
	public void setUp() {
		storage = new FileStorage();
	}
	
	@Test
	public void saveStoresThePersonAsASerializedObject() {
		Person person = new Person();
		person.addPhone(Phone.create("676767676"));
		
		boolean result = storage.save(person);
		
		assertTrue(result);
	}

	@Test
	public void storedPersonCanBeRecovered() {
		Person person = new Person("Manolo Domínguez");
		
		person.addPhone(Phone.create("600600600"));
		
		boolean result = storage.save(person);
		
		assertTrue(result);
		
		Person recoveredPerson = null;
		try {
			recoveredPerson = (Person)storage.read("Manolo Domínguez");
		} catch (NotFoundIdentifierException e) {
			Assert.fail();
		}
		List<Phone> fetchedPhones = recoveredPerson.getPhones();
		assertEquals(1, fetchedPhones.size());
		assertEquals(fetchedPhones.get(0), Phone.create("600600600"));
	}
	
	
	@Test
	public void existsShouldReturnFalseWhenThePersonHasntBeenStored() {
		assertFalse(storage.exists("Aimar"));
	}
	
	@Test
	public void existsShouldReturnTrueWhenThePersonHasBeenSavedPreviously() {
		storage.save(new Person("José María Ruíz"));
		assertTrue(storage.exists("José María Ruíz"));
	}
}
