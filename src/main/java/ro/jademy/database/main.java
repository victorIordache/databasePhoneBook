package ro.jademy.database;

import ro.jademy.database.dao.DBContactsDAO;
import ro.jademy.database.dao.contactsDAO;
import ro.jademy.database.model.Contact;
import ro.jademy.database.model.PhoneNumber;
import ro.jademy.database.model.Phonebook;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class main {

    public static void main(String[] args) throws InterruptedException {
        Phonebook phonebook = new Phonebook();
        contactsDAO contactsDAO = new DBContactsDAO();
        phonebook.add("Victor","Iordache", Arrays.asList(new PhoneNumber("+40","753358215"),new PhoneNumber("+40","552152215")) , Date.valueOf("2016-06-15"),"victoras998@gmail.com");
        phonebook.add("George","Iordache", Arrays.asList(new PhoneNumber("+40","753358215"),new PhoneNumber("+40","552152215")) , Date.valueOf("2016-06-15"));
        //phonebook.getContactList().remove(0);
        //phonebook.getContactList().get(0).call();
        //phonebook.removeContact(phonebook.getContactList().get(0));
        phonebook.removeContact(2);
        phonebook.add("Alex","Iordache", Arrays.asList(new PhoneNumber("+40","753358215"),new PhoneNumber("+40","552152215")) , Date.valueOf("2016-06-15"));

        List<Contact> contacts = contactsDAO.findAll();
        System.out.println(contacts.toString());

    }
}
