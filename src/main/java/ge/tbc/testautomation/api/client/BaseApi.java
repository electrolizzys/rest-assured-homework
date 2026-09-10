package ge.tbc.testautomation.api.client;

import ge.tbc.testautomation.data.ApiConstants;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public abstract class BaseApi {

    public static final RequestSpecification ESCUELA_SPEC = new RequestSpecBuilder()
            .setBaseUri(ApiConstants.Escuela.BASE_URI)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build()
            .filter(new ResponseLoggingFilter());

    public static final RequestSpecification BOOKING_SPEC = new RequestSpecBuilder()
            .setBaseUri(ApiConstants.Booking.BASE_URI)
            .setContentType(ContentType.JSON)
            .addHeader("Accept", "application/json")
            .addHeader("User-Agent", "Mozilla/5.0")
            .setConfig(RestAssuredConfig.newConfig()
                    .encoderConfig(EncoderConfig.encoderConfig()
                            .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
            .log(LogDetail.ALL)
            .build()
            .filter(new ResponseLoggingFilter());

    public static final RequestSpecification BOOKSTORE_SPEC = new RequestSpecBuilder()
            .setBaseUri(ApiConstants.BookStore.BASE_URI)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build()
            .filter(new ResponseLoggingFilter());

    public static final RequestSpecification F1_SPEC = new RequestSpecBuilder()
            .setBaseUri(ApiConstants.Ergast.BASE_URI)
            .setAccept(ContentType.JSON)
            .log(LogDetail.ALL)
            .build()
            .filter(new ResponseLoggingFilter());
}
