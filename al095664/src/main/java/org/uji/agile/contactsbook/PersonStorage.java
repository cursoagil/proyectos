package org.uji.agile.contactsbook;

public interface PersonStorage {
	boolean save( Person storageable );
	Person read(String identifier) throws NotFoundIdentifierException;
	boolean exists(String identifier);
	void removeAll();
}	
