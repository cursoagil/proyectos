package org.uji.agile.contactsbook;

public interface EmailService {

	boolean send(Email email, String topic, String body);
	
}
