package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertTrue;
import static data.ApiConstants.PetStore.BASE_URI;
import static data.ApiConstants.PetStore.LOGIN;
import static data.ApiConstants.PetStore.NAME_PARAM;
import static data.ApiConstants.PetStore.ORDER;
import static data.ApiConstants.PetStore.PASSWORD_PARAM;
import static data.ApiConstants.PetStore.PET;
import static data.ApiConstants.PetStore.PET_BY_ID;
import static data.ApiConstants.PetStore.STATUS_PARAM;
import static data.ApiConstants.PetStore.USERNAME_PARAM;

public class PetStoreApiTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
    }

    @Test
    public void placeOrderAndValidateResponse() {
        String orderJson = """
                {
                  "id": 10,
                  "petId": 198772,
                  "quantity": 2,
                  "shipDate": "2026-09-04T00:00:00.000Z",
                  "status": "placed",
                  "complete": true
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(orderJson)
                .when()
                .post(ORDER)
                .then()
                .statusCode(200)
                .body("id", equalTo(10))
                .body("petId", equalTo(198772))
                .body("quantity", equalTo(2))
                .body("shipDate", notNullValue())
                .body("status", equalTo("placed"))
                .body("complete", equalTo(true));
    }

    @Test
    public void updatePetWithFormData() {
        long petId = 123456789L;

        String petJson = """
                {
                  "id": 123456789,
                  "name": "doggie",
                  "photoUrls": ["url"],
                  "status": "available"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(petJson)
                .when()
                .post(PET)
                .then()
                .statusCode(200);

        given()
                .contentType(ContentType.URLENC)
                .formParam(NAME_PARAM, "snoopy")
                .formParam(STATUS_PARAM, "sold")
                .when()
                .post(PET_BY_ID, petId)
                .then()
                .statusCode(200)
                .body("code", notNullValue())
                .body("type", notNullValue())
                .body("message", notNullValue());
    }

    @Test
    public void updateMissingPetReturns404() {
        given()
                .contentType(ContentType.URLENC)
                .formParam(NAME_PARAM, "ghost")
                .formParam(STATUS_PARAM, "available")
                .when()
                .post(PET_BY_ID, 0)
                .then()
                .statusCode(404)
                .body("code", equalTo(404));
    }

    @Test
    public void loginAndExtractSessionNumber() {
        Response response = given()
                .queryParam(USERNAME_PARAM, "testuser")
                .queryParam(PASSWORD_PARAM, "testpass")
                .when()
                .get(LOGIN)
                .then()
                .statusCode(200)
                .extract()
                .response();
        String message = response.path("message");
        Matcher matcher = Pattern.compile("(\\d{10,})").matcher(message);
        assertTrue(matcher.find(), "Login message should contain at least 10 significant numbers: " + message);
        String sessionNumber = matcher.group(1);
        System.out.println("Extracted session number: " + sessionNumber);
    }
}
