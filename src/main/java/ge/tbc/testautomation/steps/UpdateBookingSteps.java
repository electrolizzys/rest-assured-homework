package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.api.client.BookingApi;
import ge.tbc.testautomation.data.models.request.BookingAuthRequest;
import ge.tbc.testautomation.data.models.requests.booking.UpdateBookingRequest;
import ge.tbc.testautomation.data.models.response.BookingTokenResponse;
import ge.tbc.testautomation.data.models.responses.booking.BookingResponse;
import ge.tbc.testautomation.data.models.responses.booking.CreatedBookingResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class UpdateBookingSteps {

    private final BookingApi bookingApi = new BookingApi();
    private int bookingId;
    private UpdateBookingRequest expectedBooking;

    @Step("Create Restful Booker token")
    public String createToken(String username, String password) {
        Response response = bookingApi.createToken(new BookingAuthRequest(username, password));
        response.then().statusCode(200);
        String token = response.as(BookingTokenResponse.class).getToken();
        assertThat(token, notNullValue());
        return token;
    }

    @Step("Create booking to update")
    public UpdateBookingSteps createBooking(UpdateBookingRequest booking) {
        Response response = bookingApi.createBooking(booking);
        response.then().statusCode(200);
        CreatedBookingResponse created = response.as(CreatedBookingResponse.class);
        bookingId = created.getBookingid();
        assertThat(bookingId, greaterThan(0));
        return this;
    }

    @Step("Update booking with token")
    public UpdateBookingSteps updateBooking(String token, UpdateBookingRequest booking) {
        expectedBooking = booking;
        Response response = bookingApi.updateBooking(bookingId, token, booking);
        response.then().statusCode(200);
        BookingResponse updated = response.as(BookingResponse.class);
        validateBookingFields(updated);
        return this;
    }

    @Step("Get booking and validate updated fields")
    public UpdateBookingSteps validateUpdatedBookingViaGet() {
        Response response = bookingApi.getBooking(bookingId);
        response.then().statusCode(200);
        validateBookingFields(response.as(BookingResponse.class));
        return this;
    }

    private void validateBookingFields(BookingResponse actual) {
        assertThat(actual.getFirstname(), equalTo(expectedBooking.getFirstname()));
        assertThat(actual.getLastname(), equalTo(expectedBooking.getLastname()));
        assertThat(actual.getTotalprice(), equalTo(expectedBooking.getTotalprice()));
        assertThat(actual.getDepositpaid(), equalTo(expectedBooking.getDepositpaid()));
        assertThat(actual.getAdditionalneeds(), equalTo(expectedBooking.getAdditionalneeds()));
        assertThat(actual.getBookingdates().getCheckin(), equalTo(expectedBooking.getBookingdates().getCheckin()));
        assertThat(actual.getBookingdates().getCheckout(), equalTo(expectedBooking.getBookingdates().getCheckout()));
        assertThat(actual.getFirstname(), notNullValue());
        assertThat(expectedBooking.getSaleprice(), notNullValue());
        assertThat(expectedBooking.getPassportNo(), notNullValue());
        assertThat(actual.getClass().getDeclaredFields().length > 0, equalTo(true));
        assertThat(getIgnoredFieldFromResponse(actual, "saleprice"), nullValue());
        assertThat(getIgnoredFieldFromResponse(actual, "passportNo"), nullValue());
    }

    private Object getIgnoredFieldFromResponse(BookingResponse actual, String fieldName) {
        try {
            var field = actual.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(actual);
        } catch (NoSuchFieldException ignored) {
            return null;
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
