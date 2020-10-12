package ro.jademy.database;

import ro.jademy.database.model.Contact;
import ro.jademy.database.model.PhoneNumber;
import ro.jademy.database.model.Phonebook;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class main {

    public static void main(String[] args) {
        Phonebook phonebook = new Phonebook();
        List<PhoneNumber> phoneNumbers= new ArrayList();
        phoneNumbers.add(new PhoneNumber("+40","753808970"));
        phoneNumbers.add(new PhoneNumber("+40","733808970"));

        phonebook.add("George","Iordache",phoneNumbers , Date.valueOf("2015-06-15"));
        //phonebook.getContactList().remove(0);
    }
}
