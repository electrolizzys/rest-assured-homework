package ge.tbc.testautomation.api.client;

import ge.tbc.testautomation.data.models.request.BookingAuthRequest;
import ge.tbc.testautomation.data.models.request.BookingRequest;
import ge.tbc.testautomation.data.models.request.PartialBookingRequest;
import io.restassured.response.Response;

import static ge.tbc.testautomation.data.ApiConstants.Booking.AUTH;
import static ge.tbc.testautomation.data.ApiConstants.Booking.BOOKING;
import static ge.tbc.testautomation.data.ApiConstants.Booking.BOOKING_BY_ID;
import static io.restassured.RestAssured.given;

public class BookingApi extends BaseApi {

    public Response createToken(BookingAuthRequest authRequest) {
        return given()
                .spec(BOOKING_SPEC)
                .body(authRequest)
                .when()
                .post(AUTH);
    }

    public Response createBooking(BookingRequest booking) {
        return given()
                .spec(BOOKING_SPEC)
                .body(booking)
                .when()
                .post(BOOKING);
    }

    public Response getBooking(int bookingId) {
        return given()
                .spec(BOOKING_SPEC)
                .pathParam("id", bookingId)
                .when()
                .get(BOOKING_BY_ID);
    }

    public Response partialUpdateBooking(int bookingId, String token, PartialBookingRequest booking) {
        return given()
                .spec(BOOKING_SPEC)
                .cookie("token", token)
                .pathParam("id", bookingId)
                .body(booking)
                .when()
                .patch(BOOKING_BY_ID);
    }

    public Response deleteBooking(int bookingId, String token) {
        return given()
                .spec(BOOKING_SPEC)
                .cookie("token", token)
                .pathParam("id", bookingId)
                .when()
                .delete(BOOKING_BY_ID);
    }
}
