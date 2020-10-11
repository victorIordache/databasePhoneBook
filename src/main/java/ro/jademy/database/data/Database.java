package ro.jademy.database.data;

import ro.jademy.database.model.Contact;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {
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

/*    public static List getData(){
        List<Contact> contactList = new ArrayList();
        try{
            Statement myStmt = getConnection().createStatement();
            ResultSet resultSet = myStmt.executeQuery("SELECT * FROM employees");
            while(resultSet.next()){
                contactList.add(new Contact());
            }
        }catch(java.sql.SQLException e){
            e.printStackTrace();
        }
        return contactList;
    }*/

    public static void closeConnection(Connection connection){
        try{
            connection.close();
        }catch(java.sql.SQLException e){
            System.out.println("Connection error.");
        }
    }


}
