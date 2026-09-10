package ge.tbc.testautomation.api.client;

import io.restassured.response.Response;

import static ge.tbc.testautomation.data.ApiConstants.Ergast.DRIVERS_2025;
import static io.restassured.RestAssured.given;

public class F1Api extends BaseApi {

    public Response getDrivers() {
        return given()
                .spec(F1_SPEC)
                .queryParam("limit", 100)
                .when()
                .get(DRIVERS_2025);
    }
}
