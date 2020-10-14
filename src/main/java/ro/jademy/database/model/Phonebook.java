package ro.jademy.database.model;

import ro.jademy.database.dao.DBContactsDAO;
import ro.jademy.database.dao.contactsDAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Phonebook extends DBContactsDAO {
    private List<Contact> contactList = new ArrayList<Contact>();
    private contactsDAO contactsDAO = new DBContactsDAO();

    public List<Contact> getContactList() {
        return contactList;
    }

    public void add(String firstName, String lastName, List<PhoneNumber> phoneNumberList, Date birthday) {
        // TODO: Verify the integrity of a contact

        Contact contact = new Contact(firstName,lastName,phoneNumberList,birthday);
        contactsDAO.create(contact);
        contact.setContactID();
        // Add it to the phonebook first then to the database
        getContactList().add(contact);
    }
    public void add(String firstName, String lastName, List<PhoneNumber> phoneNumberList, Date birthday, String email) {
        // TODO: Verify the integrity of a contact

        Contact contact = new Contact(firstName,lastName,phoneNumberList,birthday,email);
        contactsDAO.create(contact);
        contact.setContactID();
        // Add it to the phonebook first then to the database
        getContactList().add(contact);
    }

    public void removeContact(Contact contact) {
        // remove contact from phonebook's list
        contactList.remove(contact);

        // remove contact from database
        contactsDAO.remove(contact);
    }

    public void edit() {

    }


    public void backup(){}
        //AlarmManager so I can do a backup every 24h. (Runs even the app is closed.

        /*
        This will run only when the app is on!

        Timer myTimer = new Timer ();
        TimerTask myTask = new TimerTask () {
            @Override
            public void run () {
                // your code
                callSpeak().execute() // Your method
            }
        };

        myTimer.scheduleAtFixedRate(myTask , 0l, 5 * (60*1000)); // Runs every 5 mins
    */

}
