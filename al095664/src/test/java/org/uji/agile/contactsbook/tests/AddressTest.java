package org.uji.agile.contactsbook.tests;

import org.junit.Test;
import org.uji.agile.contactsbook.Address;

import static org.junit.Assert.*;

public class AddressTest {

	@Test
	public void equalsShouldReturnTrueWhenAddressesHaveTheSameValue() {
		assertEquals(new Address("C/ La Huerta nº 12"),
					 new Address("C/ La Huerta nº 12"));
	}
	
	@Test
	public void equalsShouldReturnFalseWhenAddressesHaveDifferentValues() {
		assertFalse(new Address("C/ Jaume I nº 1").equals(new Address("C/ Jaume I nº 2")));
	}
	
	@Test
	public void equalsShouldReturnFalseWhenObjectsAreNotAddresses() {
		assertFalse(new Address("C/ Jaume I nº 1").equals("C/ Jaume I nº 1"));
	}
	
	@Test
	public void equalsShouldReturnTrueWhenDifferentInstancesButSameAddressValue() {
		Address first = new Address("C/ La Huerta nº 12"),
		        second = new Address("C/ La Huerta nº 12");
		
		assertEquals(first.hashCode(), second.hashCode());
	}
	
}
