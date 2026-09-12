package ge.tbc.testautomation.api.client;

import io.restassured.response.Response;

import static ge.tbc.testautomation.data.ApiConstants.Swapi.FORMAT_PARAM;
import static ge.tbc.testautomation.data.ApiConstants.Swapi.JSON_FORMAT;
import static ge.tbc.testautomation.data.ApiConstants.Swapi.PLANETS;
import static io.restassured.RestAssured.given;

public class SwapiApi extends BaseApi {

    public Response getPlanets() {
        return given()
                .spec(SWAPI_SPEC)
                .queryParam(FORMAT_PARAM, JSON_FORMAT)
                .when()
                .get(PLANETS);
    }

    public Response getPlanetByUrl(String url) {
        return given()
                .spec(SWAPI_SPEC).when().get(url);
    }
}
