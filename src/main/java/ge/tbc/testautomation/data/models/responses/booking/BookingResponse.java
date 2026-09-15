package ge.tbc.testautomation.data.models.responses.booking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ge.tbc.testautomation.data.models.requests.booking.BookingDatesRequest;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingResponse {
    private String firstname;
    private String lastname;
    private Integer totalprice;
    private Boolean depositpaid;
    private BookingDatesRequest bookingdates;
    private String additionalneeds;
}
