package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.api.client.BookingApi;
import ge.tbc.testautomation.data.models.request.BookingAuthRequest;
import ge.tbc.testautomation.data.models.request.BookingRequest;
import ge.tbc.testautomation.data.models.request.PartialBookingRequest;
import ge.tbc.testautomation.data.models.response.BookingTokenResponse;
import ge.tbc.testautomation.data.models.response.CreateBookingResponse;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
//frmfornoforf
public class BookingSteps {

    private final BookingApi bookingApi = new BookingApi();
    private String token;
    private int bookingId;
    private BookingRequest expectedBooking;

    public BookingSteps createToken(String username, String password) {
        Response response = bookingApi.createToken(new BookingAuthRequest(username, password));
        response.then().statusCode(200);
        BookingTokenResponse tokenResponse = response.as(BookingTokenResponse.class);
        token = tokenResponse.getToken();
        assertThat(token, notNullValue());
        return this;
    }

    public BookingSteps createBooking(BookingRequest booking) {
        expectedBooking = booking;
        Response response = bookingApi.createBooking(booking);
        response.then().statusCode(200);
        CreateBookingResponse created = response.as(CreateBookingResponse.class);
        bookingId = created.getBookingid();
        assertThat(bookingId, Matchers.greaterThan(0));
        return this;
    }

    public BookingSteps validateBookingViaGet() {
        Response response = bookingApi.getBooking(bookingId);
        response.then().statusCode(200);
        BookingRequest actual = response.as(BookingRequest.class);
        assertThat(actual.getFirstname(), equalTo(expectedBooking.getFirstname()));
        assertThat(actual.getLastname(), equalTo(expectedBooking.getLastname()));
        assertThat(actual.getTotalprice(), equalTo(expectedBooking.getTotalprice()));
        assertThat(actual.isDepositpaid(), equalTo(expectedBooking.isDepositpaid()));
        assertThat(actual.getAdditionalneeds(), equalTo(expectedBooking.getAdditionalneeds()));
        assertThat(actual.getBookingdates().getCheckin(), equalTo(expectedBooking.getBookingdates().getCheckin()));
        assertThat(actual.getBookingdates().getCheckout(), equalTo(expectedBooking.getBookingdates().getCheckout()));
        return this;
    }

    public BookingSteps partialUpdateBooking(String firstname, String lastname) {
        expectedBooking.setFirstname(firstname);
        expectedBooking.setLastname(lastname);
        PartialBookingRequest patch = new PartialBookingRequest(firstname, lastname);

        Response response = bookingApi.partialUpdateBooking(bookingId, token, patch);
        response.then().statusCode(200);
        return this;
    }

    public BookingSteps deleteBooking() {
        Response response = bookingApi.deleteBooking(bookingId, token);
        response.then().statusCode(Matchers.anyOf(Matchers.is(200), Matchers.is(201)));
        return this;
    }

    public BookingSteps validateBookingIsDeleted() {
        bookingApi.getBooking(bookingId).then().statusCode(404);
        return this;
    }
}
