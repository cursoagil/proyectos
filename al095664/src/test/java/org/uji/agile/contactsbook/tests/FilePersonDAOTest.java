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

	private FilePersonDAO personDAO;

	@Before
	public void setUp() {
		personDAO = new FilePersonDAO();
	}
	
	@After
	public void tearDown() {
		personDAO.removeAll();
	}
	
	@Test
	public void saveStoresThePersonAsASerializedObject() {
		Person person = new Person();
		person.addPhone(Phone.create("676767676"));
		
		boolean result = personDAO.save(person);
		
		assertTrue(result);
	}

	@Test
	public void storedPersonCanBeRecovered() {
		Person person = new Person("Manolo Domínguez");
		
		person.addPhone(Phone.create("600600600"));
		
		boolean result = personDAO.save(person);
		
		assertTrue(result);
		
		Person recoveredPerson = null;
		try {
			recoveredPerson = personDAO.read("Manolo Domínguez");
		} catch (NotFoundIdentifierException e) {
			Assert.fail();
		}
		List<Phone> fetchedPhones = recoveredPerson.getPhones();
		assertEquals(1, fetchedPhones.size());
		assertEquals(fetchedPhones.get(0), Phone.create("600600600"));
	}
	
	
	@Test
	public void existsShouldReturnFalseWhenThePersonHasntBeenStored() {
		assertFalse(personDAO.exists("Aimar"));
	}
	
	@Test
	public void existsShouldReturnTrueWhenThePersonHasBeenSavedPreviously() {
		personDAO.save(new Person("José María Ruíz"));
		assertTrue(personDAO.exists("José María Ruíz"));
	}
	
	@Test
	public void removeAllShouldDeleteAnyData() {
		personDAO.save(new Person("José María"));
		personDAO.removeAll();
		assertFalse(personDAO.exists("José María"));
	}
	
	@Test
	public void addressCanBeStoredWithHisRelatedPerson() {
		Person person = new Person("José María");
		person.addAddress(Address.create("C/ Los Angeles nº 23"));
		personDAO.save(person);
	}
	
	@Test
	public void addressCanBeRecoveredFromHisRelatedPerson() throws NotFoundIdentifierException {
		Person person = new Person("José María");
		person.addAddress(Address.create("C/ Los Angeles nº 23"));
		personDAO.save(person);
		Person retrievedPerson = personDAO.read("José María");
		assertThat(retrievedPerson.getAddresses(), hasItem(Address.create("C/ Los Angeles nº 23")));
		assertEquals(1,retrievedPerson.getAddresses().size());
	}
	
	@Test
	public void searchReturnsNoPersonWhenThereIsNoPersonInTheSystem() {
		List<Person> matchedPeople = personDAO.search("a");
		assertEquals(0, matchedPeople.size());
	}
	
	@Test
	public void searchReturnsPeopleWhoMatchWithTheString() {
		Person maria = new Person("Maria");
		personDAO.save(maria);
		List<Person> matchedPeople = personDAO.search("a");
		
		assertHasOnly(matchedPeople, maria);
	}
	
	@Test
	public void searchReturnsOnlyThePeopleWhoMatchWithTheString() {
		Person alberto = new Person("Alberto"),
			   maria = new Person("Maria");
		
		personDAO.save(alberto);
		personDAO.save(maria);
		
		List<Person> matchedPeople = personDAO.search("e");
		assertHasOnly(matchedPeople, alberto);
	}
	
	@Test
	public void searchIsCaseInsensitive() {
		Person alberto = new Person("Alberto");
		
		personDAO.save(alberto);
		assertHasOnly(personDAO.search("a"), alberto);
	}
	
	private static <E> void assertHasOnly(List<E> collection, E item) {
		assertThat(collection, hasItem(item));
		assertEquals(1, collection.size());
	}
}

