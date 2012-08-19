package org.uji.agile.contactsbook.tests;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uji.agile.contactsbook.*;

import static org.junit.Assert.*;

public class FileStorageTest {

	private FileStorage fileStorage;

	@Before
	public void setUp() {
		fileStorage = new FileStorage();
	}
	
	@Test
	public void saveStoresThePersonAsASerializedObject() {
		Person person = new Person();
		person.addPhone(Phone.create("676767676"));
		
		boolean result = fileStorage.save(person);
		
		assertTrue(result);
	}

	@Test
	public void storedPersonCanBeRecovered() {
		Person person = new Person("Manolo Domínguez");
		
		person.addPhone(Phone.create("600600600"));
		
		boolean result = fileStorage.save(person);
		
		assertTrue(result);
		
		Person recoveredPerson = null;
		try {
			recoveredPerson = (Person)fileStorage.read("Manolo Domínguez");
		} catch (NotFoundIdentifierException e) {
			Assert.fail();
		}
		List<Phone> fetchedPhones = recoveredPerson.getPhones();
		assertEquals(1, fetchedPhones.size());
		assertEquals(fetchedPhones.get(0), Phone.create("600600600"));
	}
	
	
	@Test
	public void existsShouldReturnFalseWhenThePersonHasntBeenStored() {
		assertFalse(fileStorage.exists("Aimar"));
	}
	
	@Test
	public void existsShouldReturnTrueWhenThePersonHasBeenSavedPreviously() {
		fileStorage.save(new Person("José María Ruíz"));
		assertTrue(fileStorage.exists("José María Ruíz"));
	}
	
	@Test
	public void removeAllShouldDeleteAnyData() {
		fileStorage.save(new Person("José María"));
		fileStorage.removeAll();
		assertFalse(fileStorage.exists("José María"));
	}
	
	@Test
	public void addressCanBeStoredWithHisRelatedPerson() {
		Person person = new Person("José María");
		person.addAddress(new Address("C/ Los Angeles nº 23"));
		fileStorage.save(person);
	}
	
}
