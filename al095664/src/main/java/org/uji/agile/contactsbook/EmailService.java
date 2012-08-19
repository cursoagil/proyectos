package org.uji.agile.contactsbook;

public interface EmailService {

	public boolean send(Email email, String topic, String body);
	
}
