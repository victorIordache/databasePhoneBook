package ro.jademy.database.model;

public class Owner {
    private String firstName;
    private String LastName;
    private Address Address;

    public String getFirstName(){
        return firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


}
