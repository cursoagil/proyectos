package org.uji.agile.contactsbook;

import java.util.HashMap;
import java.util.Map;

public class Phone {

	private static Map<String,Phone> phoneInstances;

	static {
		phoneInstances = new HashMap<String, Phone>();
	}
	
	public Phone(String phone) {
		
	}
	
	public static Phone create(String phone) {
		if (!phoneInstances.containsKey(phone)) {
			phoneInstances.put(phone, new Phone(phone));	
		}
		return phoneInstances.get(phone);
	}
	
}
