package ge.tbc.testautomation.data.models.responses.booking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatedBookingResponse {
    private Integer bookingid;
    private BookingResponse booking;
}
