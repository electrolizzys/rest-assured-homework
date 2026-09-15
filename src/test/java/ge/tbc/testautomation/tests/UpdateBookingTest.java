package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.models.requests.booking.BookingDatesRequest;
import ge.tbc.testautomation.data.models.requests.booking.UpdateBookingRequest;
import ge.tbc.testautomation.steps.UpdateBookingSteps;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.ApiConstants.Booking.PASSWORD;
import static ge.tbc.testautomation.data.ApiConstants.Booking.USERNAME;

public class UpdateBookingTest {

    private final UpdateBookingSteps updateBookingSteps = new UpdateBookingSteps();
    private String token;

    @BeforeClass
    public void createToken() {
        token = updateBookingSteps.createToken(USERNAME, PASSWORD);
    }

    @Test
    public void updateBookingAndValidateFields() {
        UpdateBookingRequest initialBooking = UpdateBookingRequest.builder()
                .firstname("Jim").lastname("Brown").totalprice(111)
                .depositpaid(true)
                .bookingdates(new BookingDatesRequest("2026-01-01", "2026-01-10"))
                .additionalneeds("Breakfast").saleprice(99.99).passportNo("P1234567")
                .build();

        UpdateBookingRequest updatedBooking = UpdateBookingRequest.builder()
                .firstname("Updated").lastname("Guest").totalprice(250)
                .depositpaid(false)
                .bookingdates(new BookingDatesRequest("2026-02-01", "2026-02-14"))
                .additionalneeds("Late checkout").saleprice(150.50)
                .passportNo("P7654321").build();

        updateBookingSteps
                .createBooking(initialBooking)
                .updateBooking(token, updatedBooking)
                .validateUpdatedBookingViaGet();
    }
}
