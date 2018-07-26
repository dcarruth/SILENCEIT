public class StreetAddress {
    private int zipCode;
    private String street;
    private String city;
    private String state;

    StreetAddress(){
        zipCode = 0;
        street = null;
        city = null;
        state = null;
    }

    StreetAddress(int zipCode, String street, String city, String state) {
        this.zipCode = zipCode;
        this.street = street;
        this.city = city;
        this.state = state;
    }


    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
