package ro.jademy.database.model;

public class Address {
    String addressLine;
    String city;
    Integer postCode;

    public Address(String addressLine, String city, Integer postCode) {
        this.addressLine = addressLine;
        this.city = city;
        this.postCode = postCode;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPostCode() {
        return postCode;
    }

    public void setPostCode(Integer postCode) {
        this.postCode = postCode;
    }
}
