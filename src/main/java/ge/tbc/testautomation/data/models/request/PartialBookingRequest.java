package ge.tbc.testautomation.data.models.request;

public class PartialBookingRequest {
    private String firstname;
    private String lastname;

    public PartialBookingRequest() {
    }

    public PartialBookingRequest(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
