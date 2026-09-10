package ge.tbc.testautomation.data.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Driver {
    private String driverId;
    private int permanentNumber;

    public Driver() {
    }

    public Driver(String driverId, int permanentNumber) {
        this.driverId = driverId;
        this.permanentNumber = permanentNumber;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public int getPermanentNumber() {
        return permanentNumber;
    }

    public void setPermanentNumber(int permanentNumber) {
        this.permanentNumber = permanentNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Driver driver = (Driver) o;
        return permanentNumber == driver.permanentNumber
                && Objects.equals(driverId, driver.driverId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driverId, permanentNumber);
    }
}
