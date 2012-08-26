package org.uji.agile.contactsbook.tests;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uji.agile.contactsbook.*;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;

public class FilePersonStorageTest {

	private FilePersonStorage filePersonStorage;

	@Before
	public void setUp() {
		filePersonStorage = new FilePersonStorage();
	}
	
	@Test
	public void saveStoresThePersonAsASerializedObject() {
		Person person = new Person();
		person.addPhone(Phone.create("676767676"));
		
		boolean result = filePersonStorage.save(person);
		
		assertTrue(result);
	}

	@Test
	public void storedPersonCanBeRecovered() {
		Person person = new Person("Manolo Domínguez");
		
		person.addPhone(Phone.create("600600600"));
		
		boolean result = filePersonStorage.save(person);
		
		assertTrue(result);
		
		Person recoveredPerson = null;
		try {
			recoveredPerson = filePersonStorage.read("Manolo Domínguez");
		} catch (NotFoundIdentifierException e) {
			Assert.fail();
		}
		List<Phone> fetchedPhones = recoveredPerson.getPhones();
		assertEquals(1, fetchedPhones.size());
		assertEquals(fetchedPhones.get(0), Phone.create("600600600"));
	}
	
	
	@Test
	public void existsShouldReturnFalseWhenThePersonHasntBeenStored() {
		assertFalse(filePersonStorage.exists("Aimar"));
	}
	
	@Test
	public void existsShouldReturnTrueWhenThePersonHasBeenSavedPreviously() {
		filePersonStorage.save(new Person("José María Ruíz"));
		assertTrue(filePersonStorage.exists("José María Ruíz"));
	}
	
	@Test
	public void removeAllShouldDeleteAnyData() {
		filePersonStorage.save(new Person("José María"));
		filePersonStorage.removeAll();
		assertFalse(filePersonStorage.exists("José María"));
	}
	
	@Test
	public void addressCanBeStoredWithHisRelatedPerson() {
		Person person = new Person("José María");
		person.addAddress(Address.create("C/ Los Angeles nº 23"));
		filePersonStorage.save(person);
	}
	
	@Test
	public void addressCanBeRecoveredFromHisRelatedPerson() throws NotFoundIdentifierException {
		Person person = new Person("José María");
		person.addAddress(Address.create("C/ Los Angeles nº 23"));
		filePersonStorage.save(person);
		Person retrievedPerson = filePersonStorage.read("José María");
		assertThat(retrievedPerson.getAddresses(), hasItem(Address.create("C/ Los Angeles nº 23")));
		assertEquals(1,retrievedPerson.getAddresses().size());
	}
}
