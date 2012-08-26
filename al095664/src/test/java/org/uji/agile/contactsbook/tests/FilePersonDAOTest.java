package org.uji.agile.contactsbook.tests;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uji.agile.contactsbook.*;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;

public class FilePersonDAOTest {

	private FilePersonDAO filePersonDAO;

	@Before
	public void setUp() {
		filePersonDAO = new FilePersonDAO();
	}
	
	@After
	public void tearDown() {
		filePersonDAO.removeAll();
	}
	
	@Test
	public void saveStoresThePersonAsASerializedObject() {
		Person person = new Person();
		person.addPhone(Phone.create("676767676"));
		
		boolean result = filePersonDAO.save(person);
		
		assertTrue(result);
	}

	@Test
	public void storedPersonCanBeRecovered() {
		Person person = new Person("Manolo Domínguez");
		
		person.addPhone(Phone.create("600600600"));
		
		boolean result = filePersonDAO.save(person);
		
		assertTrue(result);
		
		Person recoveredPerson = null;
		try {
			recoveredPerson = filePersonDAO.read("Manolo Domínguez");
		} catch (NotFoundIdentifierException e) {
			Assert.fail();
		}
		List<Phone> fetchedPhones = recoveredPerson.getPhones();
		assertEquals(1, fetchedPhones.size());
		assertEquals(fetchedPhones.get(0), Phone.create("600600600"));
	}
	
	
	@Test
	public void existsShouldReturnFalseWhenThePersonHasntBeenStored() {
		assertFalse(filePersonDAO.exists("Aimar"));
	}
	
	@Test
	public void existsShouldReturnTrueWhenThePersonHasBeenSavedPreviously() {
		filePersonDAO.save(new Person("José María Ruíz"));
		assertTrue(filePersonDAO.exists("José María Ruíz"));
	}
	
	@Test
	public void removeAllShouldDeleteAnyData() {
		filePersonDAO.save(new Person("José María"));
		filePersonDAO.removeAll();
		assertFalse(filePersonDAO.exists("José María"));
	}
	
	@Test
	public void addressCanBeStoredWithHisRelatedPerson() {
		Person person = new Person("José María");
		person.addAddress(Address.create("C/ Los Angeles nº 23"));
		filePersonDAO.save(person);
	}
	
	@Test
	public void addressCanBeRecoveredFromHisRelatedPerson() throws NotFoundIdentifierException {
		Person person = new Person("José María");
		person.addAddress(Address.create("C/ Los Angeles nº 23"));
		filePersonDAO.save(person);
		Person retrievedPerson = filePersonDAO.read("José María");
		assertThat(retrievedPerson.getAddresses(), hasItem(Address.create("C/ Los Angeles nº 23")));
		assertEquals(1,retrievedPerson.getAddresses().size());
	}
	
	@Test
	public void matchReturnsNoPersonWhenThereIsNoPersonInTheSystem() {
		List<Person> matchedPeople = filePersonDAO.search("a");
		assertEquals(0, matchedPeople.size());
	}
	
	@Test
	public void matchReturnsPeopleWhoMatchWithTheString() {
		Person maria = new Person("Maria");
		filePersonDAO.save(maria);
		List<Person> matchedPeople = filePersonDAO.search("a");
		
		assertThat(matchedPeople, hasItem(maria));
		assertEquals(1, matchedPeople.size());
	}
	
	@Test
	public void matchReturnsOnlyThePeopleWhoMatchWithTheString() {
		Person alberto = new Person("Alberto"),
			   maria = new Person("Maria");
		
		filePersonDAO.save(alberto);
		filePersonDAO.save(maria);
		
		List<Person> matchedPeople = filePersonDAO.search("e");
		assertThat(matchedPeople, hasItem(alberto));
		assertEquals(1, matchedPeople.size());
	}
}

