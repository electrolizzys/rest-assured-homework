package ge.tbc.testautomation.data.models.request;

public class BookingAuthRequest {
    private String username;
    private String password;

    public BookingAuthRequest() {
    }

    public BookingAuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
