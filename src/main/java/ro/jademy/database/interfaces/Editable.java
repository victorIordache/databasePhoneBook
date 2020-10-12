package ro.jademy.database.interfaces;

import ro.jademy.database.model.Contact;
import ro.jademy.database.model.PhoneNumber;

import java.util.Date;
import java.util.List;

public interface Editable {

    void add(String firstName, String lastName, List<PhoneNumber> phoneNumberList, Date birthday);

    void removeContact(Contact contact);

    void edit();
}
