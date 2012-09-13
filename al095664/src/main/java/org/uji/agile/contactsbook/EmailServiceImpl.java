package org.uji.agile.contactsbook;

public class EmailServiceImpl implements EmailService {

	@Override
	public boolean send(Email email, String topic, String body) {
		boolean topicOrBodyAreEmpty = ("".equals(topic) || "".equals(body));
		return !topicOrBodyAreEmpty;
	}

}
