package ge.tbc.testautomation.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ge.tbc.testautomation.data.ApiConstants;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;

public abstract class BaseApi {

    protected static final RestAssuredConfig JACKSON_CONFIG = RestAssuredConfig.newConfig()
            .objectMapperConfig(objectMapperConfig()
                    .defaultObjectMapperType(ObjectMapperType.JACKSON_2)
                    .jackson2ObjectMapperFactory((type, charset) -> {
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.registerModule(new JavaTimeModule());
                        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                        return objectMapper;
                    }));

    static {
        RestAssured.filters(new AllureRestAssured());
        RestAssured.config = JACKSON_CONFIG;
    }

    public static final RequestSpecification ESCUELA_SPEC = new RequestSpecBuilder()
            .setBaseUri(ApiConstants.Escuela.BASE_URI)
            .setContentType(ContentType.JSON)
            .setConfig(JACKSON_CONFIG).log(LogDetail.ALL).build()
            .filter(new ResponseLoggingFilter());

    public static final RequestSpecification BOOKING_SPEC = new RequestSpecBuilder()
            .setBaseUri(ApiConstants.Booking.BASE_URI)
            .setContentType(ContentType.JSON)
            .addHeader("Accept", "application/json")
            .addHeader("User-Agent", "Mozilla/5.0")
            .setConfig(JACKSON_CONFIG.encoderConfig(EncoderConfig.encoderConfig()
                    .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
            .log(LogDetail.ALL)
            .build()
            .filter(new ResponseLoggingFilter());

    public static final RequestSpecification BOOKSTORE_SPEC = new RequestSpecBuilder()
            .setBaseUri(ApiConstants.BookStore.BASE_URI)
            .setContentType(ContentType.JSON)
            .setConfig(JACKSON_CONFIG)
            .log(LogDetail.ALL)
            .build()
            .filter(new ResponseLoggingFilter());

    public static final RequestSpecification F1_SPEC = new RequestSpecBuilder()
            .setBaseUri(ApiConstants.Ergast.BASE_URI)
            .setAccept(ContentType.JSON)
            .setConfig(JACKSON_CONFIG)
            .log(LogDetail.ALL)
            .build()
            .filter(new ResponseLoggingFilter());

    public static final RequestSpecification SWAPI_SPEC = new RequestSpecBuilder()
            .setBaseUri(ApiConstants.Swapi.BASE_URI)
            .setAccept(ContentType.JSON)
            .setConfig(JACKSON_CONFIG)
            .log(LogDetail.ALL)
            .build()
            .filter(new ResponseLoggingFilter());

    public static final RequestSpecification PETSTORE_V3_SPEC = new RequestSpecBuilder()
            .setBaseUri(ApiConstants.PetStoreV3.BASE_URI)
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .setConfig(JACKSON_CONFIG)
            .log(LogDetail.ALL)
            .build()
            .filter(new ResponseLoggingFilter());
}
