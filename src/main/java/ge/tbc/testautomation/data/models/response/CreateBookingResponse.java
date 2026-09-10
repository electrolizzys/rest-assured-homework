package ge.tbc.testautomation.data.models.response;

import ge.tbc.testautomation.data.models.request.BookingRequest;

public class CreateBookingResponse {
    private int bookingid;
    private BookingRequest booking;

    public int getBookingid() {
        return bookingid;
    }

    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }

    public BookingRequest getBooking() {
        return booking;
    }

    public void setBooking(BookingRequest booking) {
        this.booking = booking;
    }
}
