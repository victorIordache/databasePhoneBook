package ro.jademy.database.model;

import ro.jademy.database.data.Database;
import ro.jademy.database.interfaces.Editable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Phonebook implements Editable {
    private List<Contact> contactList = new ArrayList<Contact>();

    public List<Contact> getContactList() {
        return contactList;
    }

    @Override
    public void add(String firstName, String lastName, List<PhoneNumber> phoneNumberList, Date birthday) {
        // Verify the integrity of a contact

        
        Contact contact = new Contact(firstName,lastName,phoneNumberList,birthday);
        // Add it to the phonebook first then to the database
        getContactList().add(contact);


        String contactsQuery = "INSERT INTO contacts(firstName,lastName,birthday) VALUES (?,?,?)";
        String phoneNumberQuery = "INSERT INTO phonenumbers(phoneNumber, contactID) VALUES (?,(SELECT contactID FROM contacts WHERE firstName='"+contact.getFirstName()+"' AND lastName='"+contact.getLastName()+"' AND birthday='"+contact.getBirthdayString()+"' ))";
        try{
            PreparedStatement contactsPreparedStatement = Database.getConnection().prepareStatement(contactsQuery);
            PreparedStatement phoneNumberPreparedStatement = Database.getConnection().prepareStatement(phoneNumberQuery);
            // Prepare the contacts & phoneNumber statement
            contactsPreparedStatement.setString(1,contact.getFirstName());
            contactsPreparedStatement.setString(2,contact.getLastName());
            contactsPreparedStatement.setDate(3, java.sql.Date.valueOf(contact.getBirthdayString()));

            // Execute the contacts statement
            contactsPreparedStatement.execute();
            // forEach phoneNumber inside the list, add them one by one
            phoneNumberList.forEach(phoneNumber -> {
                try {
                    phoneNumberPreparedStatement.setString(1, phoneNumber.getPhoneNumber());
                    phoneNumberPreparedStatement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });


            // Close the DB connection
            Database.closeConnection(Database.getConnection());
        }catch(SQLException e){
            // PreparedStatement exception. How do I want to deal with it?
        }
    }

    @Override
    public void removeContact(Contact contact) {
        // remove contact from phonebook's list
        contactList.remove(contact);

        // remove contact from database
        String removeContactQuery = "DELETE FROM contacts WHERE firstName='" + contact.getFirstName()+ "' AND lastName='"+ contact.getLastName()+"' AND birthday='"+ contact.getBirthdayString()+"';";
        try {
            PreparedStatement removePreparedStatement = Database.getConnection().prepareStatement(removeContactQuery);
            removePreparedStatement.execute();
            Database.closeConnection(Database.getConnection());
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
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
