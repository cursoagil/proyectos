package org.uji.agile.contactsbook;

import java.util.List;

public interface PersonDAO {
	boolean save( Person storageable );
	Person read(String identifier) throws NotFoundIdentifierException;
	boolean exists(String identifier);
	void removeAll();
	List<Person> search(String substring);
}	
