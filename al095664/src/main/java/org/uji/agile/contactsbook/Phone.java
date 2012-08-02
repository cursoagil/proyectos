package org.uji.agile.contactsbook;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Phone implements Serializable {

	private static final long serialVersionUID = -1303453661987219280L;
	private static Map<String,Phone> phoneInstances;

	private String phonenumber;
	
	static {
		phoneInstances = new HashMap<String, Phone>();
	}
	
	public Phone(String phone) {
		this.phonenumber = phone;
	}
	
	public static Phone create(String phone) {
		if (!phoneInstances.containsKey(phone)) {
			phoneInstances.put(phone, new Phone(phone));	
		}
		return phoneInstances.get(phone);
	}
	@Override
	public boolean equals(Object otherPhone) {
		if (!(otherPhone instanceof Phone)) {
			return false;
		}
		
		Phone castedOtherPhone = (Phone) otherPhone;
		return this.phonenumber.equals(castedOtherPhone.phonenumber);
	}
	
	public String toString() {
		return phonenumber;
	}
}
