package ro.jademy.database.model;

import com.mysql.cj.protocol.Resultset;
import ro.jademy.database.dao.DBContactsDAO;
import ro.jademy.database.dao.contactsDAO;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Contact {
    private int contactID;
    private String firstName;
    private String lastName;
    //A contact might have more than 1 phone number.
    private List<PhoneNumber> phoneNumberList = new ArrayList<>();
    private Date birthday;
    private String email;

    public Contact(int contactID, String firstName, String lastName, Date birthday,String email) {
        this.contactID = contactID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
    }
    public Contact(String firstName, String lastName, List<PhoneNumber> phoneNumberList, Date birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumberList = phoneNumberList;
        this.birthday = birthday;
    }
    public Contact(String firstName, String lastName, List<PhoneNumber> phoneNumberList, Date birthday, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumberList = phoneNumberList;
        this.birthday = birthday;
        this.email = email;
    }
    public int getContactID(){return contactID;}

    public String getEmail() {
        return email;
    }
    public boolean validateEmail(){
        Pattern emailPattern = Pattern.compile("^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$");
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    public void setContactID(){
        contactsDAO contactsDAO = new DBContactsDAO();
        String getContactID;
        if(getEmail() == null){
             getContactID ="SELECT contacts.contactID FROM contacts  \n" +
                    "RIGHT JOIN phonenumbers ON contacts.contactID = phonenumbers.contactID\n" +
                    "WHERE firstName = '" + getFirstName() + "' " +
                    "AND lastName = '"+ getLastName()+"' " +
                    "AND birthday = '"+ getBirthdayString()+"' " +
                    "AND email IS NULL LIMIT 1;";
        }else{
             getContactID ="SELECT contacts.contactID FROM contacts  \n" +
                    "RIGHT JOIN phonenumbers ON contacts.contactID = phonenumbers.contactID\n" +
                    "WHERE firstName = '" + getFirstName() + "' " +
                    "AND lastName = '"+ getLastName()+"' " +
                    "AND birthday = '"+ getBirthdayString()+"' " +
                    "AND email = '" + getEmail() +"' LIMIT 1;";
        }
        try {
            PreparedStatement preparedStatement = DBContactsDAO.getConnection().prepareStatement(getContactID);
            ResultSet resultSet = preparedStatement.executeQuery();
            //Positioning the cursor at the first line of resultSet
            resultSet.next();
            // Setting the contactID for each contact.
            contactID = resultSet.getInt("contactID");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getFirstName() {
        return firstName;
    }



    public String getLastName() {
        return lastName;
    }



    public List<PhoneNumber> getPhoneNumberList() {
        return phoneNumberList;
    }

    public Date getBirthday() {
        return birthday;
    }

    // The reason for changing the function's name also.
        // An overloaded method may or may not have different return types.
        // But return type alone is not sufficient for the compiler to determine which method is to be executed at run time.
    public String getBirthdayString() {
        return birthday.toString();
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return firstName.equals(contact.firstName) &&
                lastName.equals(contact.lastName) &&
                phoneNumberList.equals(contact.phoneNumberList) &&
                birthday.equals(contact.birthday) &&
                email.equals(contact.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, phoneNumberList, birthday);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactID= '" + contactID + '\'' +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumberList=" + phoneNumberList +
                ", birthday=" + birthday +
                ", email=" + email +
                '}';
    }
}
