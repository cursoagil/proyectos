package org.uji.agile.contactsbook.tests;

import org.junit.Before;
import org.junit.Test;
import org.uji.agile.contactsbook.Address;
import org.uji.agile.contactsbook.Email;
import org.uji.agile.contactsbook.Person;
import org.uji.agile.contactsbook.Phone;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;
import static org.mockito.Mockito.mock;

public class PersonTest {

	private Person person;

	@Before
	public void setUp() {
		person = new Person();
	}
	
	@Test
	public void assignedPhonesToPeopleAreRecoverable() {
		Phone phone = mock(Phone.class);
		person.addPhone(phone);
		assertThat(person.getPhones(), hasItem(phone));
	}
	
	@Test
	public void assignedEmailsToPeopleAreRecoverable() {
		Email email = mock(Email.class);
		person.addEmail(email);
		assertThat(person.getEmails(), hasItem(email));
	}

	@Test
	public void addressCanBeAddressedToManyPeople() {
		Person otherPerson = new Person("Manu");
		Address address = mock(Address.class);
		
		person.addAddress(address);
		otherPerson.addAddress(address);
		
		assertThat(person.getAddresses(), hasItem(address));
		assertThat(otherPerson.getAddresses(), hasItem(address));
	}
}