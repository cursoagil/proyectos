package org.uji.agile.contactsbook;

public interface Storage {
	boolean save( StorageSerializable storageable );
	StorageSerializable read(String identifier) throws NotFoundException;
}	
