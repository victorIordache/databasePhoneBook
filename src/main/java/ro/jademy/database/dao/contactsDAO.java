package ro.jademy.database.dao;

import ro.jademy.database.model.Contact;

import java.util.List;

public interface contactsDAO extends entityDAO<Contact> {

    List<Contact> findAll();

    Contact findById(int contactId);

    void create(Contact contact);

    void update(Contact contact);

    void remove(Contact contact);

    void remove(int contactID);
}
