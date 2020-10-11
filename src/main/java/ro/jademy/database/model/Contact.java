package ro.jademy.database.model;

import ro.jademy.database.data.Database;
import ro.jademy.database.interfaces.Editable;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Contact implements Editable {
    private String firstName;
    private String lastName;
    //A contact might have more than 1 phone number.
    private List<PhoneNumber> phoneNumberList;
    private Date birthday;

    public Contact(String firstName, String lastName, List<PhoneNumber> phoneNumberList,Date birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumberList = phoneNumberList;
        this.birthday = birthday;
    }

    public void call(){
        System.out.println("You're about to call " + firstName + " " + lastName);
        try{
            Thread.sleep(2000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        // Calling a contact
        // Check if the contact has a number, no point of going further if it doesn't.
        // Most contacts have a single number so this is why the else if comes before else.
        if(phoneNumberList.size()==0){
            System.out.println("This contact doesn't have a number.");
        }else if(phoneNumberList.size() == 1){
            System.out.println("Calling: " + phoneNumberList.get(0).toString());
        }else {
            System.out.println("What number would you like to call?");
            phoneNumberList.forEach(phoneNumber -> System.out.println(phoneNumber.toString()));
            Scanner scanner = new Scanner(System.in);
            String response = scanner.next();
            System.out.println("Calling: " + phoneNumberList.get(Integer.parseInt(response)-1).toString());
        }
    }

    @Override
    public void add(String firstName, String lastName, List<PhoneNumber> phoneNumberList,Date birthday) {
        Contact contact = new Contact(firstName,lastName,phoneNumberList,birthday);
        String contactsQuery = "INSERT INTO contacts(firstName,lastName,birthday) VALUES (?,?,?)";
        String phoneNumberQuery = "INSERT INTO phonenumbers(phoneNumber) VALUES (?)";
        try{
            PreparedStatement contactsPreparedStatement = Database.getConnection().prepareStatement(contactsQuery);
            PreparedStatement phoneNumberPreparedStatement = Database.getConnection().prepareStatement(contactsQuery);
            // Prepare the contacts & phoneNumber statement
            contactsPreparedStatement.setString(1,contact.firstName);
            contactsPreparedStatement.setString(2,contact.lastName);
            contactsPreparedStatement.setDate(3, java.sql.Date.valueOf(String.valueOf(birthday)));

            // forEach phoneNumber inside the list, add them one by one
            phoneNumberList.forEach(phoneNumber -> {
                try {
                    phoneNumberPreparedStatement.setString(1, phoneNumber.getPhoneNumber());
                    phoneNumberPreparedStatement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            // Execute the contacts statement
            contactsPreparedStatement.execute();

            // Close the DB connection
            Database.getConnection().close();
        }catch(SQLException e){
            // PreparedStatement exception. How do I want to deal with it?
        }

    }

    @Override
    public void remove() {

    }

    @Override
    public void edit() {

    }
}
