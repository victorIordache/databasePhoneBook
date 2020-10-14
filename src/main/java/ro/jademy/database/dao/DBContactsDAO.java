package ro.jademy.database.dao;

import ro.jademy.database.model.Contact;
import ro.jademy.database.model.PhoneNumber;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBContactsDAO implements contactsDAO{
    private static String databaseURL = "jdbc:mysql://localhost:3306/jademydatabase";
    private static String userDB = "root";
    private static String passwordDB = "password";

    public static Connection getConnection(){
        Connection myConn = null;
        try{
            myConn = DriverManager.getConnection(databaseURL,userDB,passwordDB);
        }catch(Exception exc){
            exc.printStackTrace();
        }
        return myConn;
    }

    public static void closeConnection(Connection connection){
        try{
            connection.close();
        }catch(java.sql.SQLException e){
            System.out.println("Connection error.");
        }
    }

    @Override
    public List<Contact> findAll() {
        List<Contact> contactList = new ArrayList<>();
        try{
            Statement stmt = DBContactsDAO.getConnection().createStatement();
            ResultSet contactsResult = stmt.executeQuery("SELECT * FROM contacts");
            while(contactsResult.next()){
                int contactID = contactsResult.getInt("contactID");
                String firstName = contactsResult.getString("firstName");
                String lastName = contactsResult.getString("lastName");
                Date birthday = contactsResult.getDate("birthday");
                String email = contactsResult.getString("email");

                // Add it to my contactList ( without phonenumber )
                contactList.add(new Contact(contactID,firstName,lastName,birthday,email));

                // Add the phone numbers for every contact
                Statement getPhone = DBContactsDAO.getConnection().createStatement();
                ResultSet getPhoneNumbers = getPhone.executeQuery("SELECT countryCode, phoneNumber " +
                        "FROM phonenumbers " +
                        "INNER JOIN contacts " +
                        "ON phonenumbers.contactID = contacts.contactID " +
                        "WHERE firstName = '" + contactList.get(contactList.size()-1).getFirstName() + "' " +
                        "AND lastName = '"+ contactList.get(contactList.size()-1).getLastName() +"' " +
                        "AND birthday = '" + contactList.get(contactList.size()-1).getBirthdayString() + "' " +
                        "AND email = '" + contactList.get(contactList.size()-1).getEmail() +"';");
                while(getPhoneNumbers.next()){
                    String countryCode = getPhoneNumbers.getString("countryCode");
                    String phoneNumber = getPhoneNumbers.getString("phoneNumber");
                    contactList.get(contactList.size()-1).getPhoneNumberList().add(new PhoneNumber(countryCode,phoneNumber));
                }
            }
            return contactList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Contact findById(int itemId) {
        return null;
    }
    public Contact findByEmail(String Email){
        return null;
    }

    @Override
    public void create(Contact contact) {
        String contactsQuery = "INSERT INTO contacts(firstName,lastName,birthday,email) VALUES (?,?,?,?)";
        String phoneNumberQuery = "INSERT INTO phonenumbers(countryCode, phoneNumber, contactID) VALUES (?, ?,(SELECT contactID FROM contacts WHERE firstName='"+contact.getFirstName()+"' AND lastName='"+contact.getLastName()+"' AND birthday='"+contact.getBirthdayString()+"' ))";
        try{
            PreparedStatement contactsPreparedStatement = DBContactsDAO.getConnection().prepareStatement(contactsQuery);
            PreparedStatement phoneNumberPreparedStatement = DBContactsDAO.getConnection().prepareStatement(phoneNumberQuery);
            // Prepare the contacts & phoneNumber statement
            contactsPreparedStatement.setString(1,contact.getFirstName());
            contactsPreparedStatement.setString(2,contact.getLastName());
            contactsPreparedStatement.setDate(3, java.sql.Date.valueOf(contact.getBirthdayString()));
            contactsPreparedStatement.setString(4,contact.getEmail());

            // Execute the contacts statement
            contactsPreparedStatement.execute();
            // forEach phoneNumber inside the list, add them one by one
            contact.getPhoneNumberList().forEach(phoneNumber -> {
                try {
                    phoneNumberPreparedStatement.setString(1, phoneNumber.getCountryCode());
                    phoneNumberPreparedStatement.setString(2, phoneNumber.getPhoneNumber());
                    phoneNumberPreparedStatement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            // Close the DB connection
            DBContactsDAO.closeConnection(DBContactsDAO.getConnection());
        }catch(SQLException e){
            // PreparedStatement exception. How do I want to deal with it?
        }
    }

    @Override
    public void update(Contact contact) {

    }

    @Override
    public void remove(Contact contact) {
        String removeContactQuery;
        if(contact.getEmail() == null ){
            removeContactQuery = "DELETE FROM contacts WHERE firstName='" + contact.getFirstName()+ "' AND lastName='"+ contact.getLastName()+"' AND birthday='"+ contact.getBirthdayString()+"' AND email IS NULL;";
        }else {
            removeContactQuery = "DELETE FROM contacts WHERE firstName='" + contact.getFirstName()+ "' AND lastName='"+ contact.getLastName()+"' AND birthday='"+ contact.getBirthdayString()+"' AND email = '"+ contact.getEmail() + "';";
        }
        try {
            PreparedStatement removePreparedStatement = DBContactsDAO.getConnection().prepareStatement(removeContactQuery);
            removePreparedStatement.execute();
            DBContactsDAO.closeConnection(DBContactsDAO.getConnection());
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int contactID) {
        String removeByID = "DELETE FROM contacts WHERE contactID = '"+ contactID +"'";
        try{
            PreparedStatement removeByIDStatement = DBContactsDAO.getConnection().prepareStatement(removeByID);
            removeByIDStatement.execute();
            System.out.println("Succesfully removed contact with ID: " + contactID);
            DBContactsDAO.closeConnection(DBContactsDAO.getConnection());
        }catch(SQLException e){
            System.out.println("removing error by ID");
        }
    }
}
