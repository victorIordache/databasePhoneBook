package ro.jademy.database.model;

public class Owner {
    private String firstName;
    private String lastName;
    private String nationality;
    private Address Address;

    public String getFirstName(){
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


}
