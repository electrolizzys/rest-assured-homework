package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.models.request.BookingDates;
import ge.tbc.testautomation.data.models.request.BookingRequest;
import ge.tbc.testautomation.steps.BookingSteps;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.ApiConstants.Booking.PASSWORD;
import static ge.tbc.testautomation.data.ApiConstants.Booking.USERNAME;

public class RestfulBookerTest {

    private final BookingSteps bookingSteps = new BookingSteps();

    @Test
    public void createPartialUpdateAndDeleteBooking() {
        BookingRequest booking = new BookingRequest(
                "Jim",
                "Brown",
                111,
                true,
                new BookingDates("2026-01-01", "2026-01-10"),
                "Breakfast"
        );

        bookingSteps
                .createToken(USERNAME, PASSWORD)
                .createBooking(booking)
                .validateBookingViaGet()
                .partialUpdateBooking("Updated", "Name")
                .validateBookingViaGet()
                .deleteBooking()
                .validateBookingIsDeleted();
    }
}
