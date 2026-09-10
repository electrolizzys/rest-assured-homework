package ge.tbc.testautomation.api.client;

import ge.tbc.testautomation.data.models.request.EscuelaLoginRequest;
import ge.tbc.testautomation.data.models.request.EscuelaUserRequest;
import io.restassured.response.Response;

import static ge.tbc.testautomation.data.ApiConstants.Escuela.LOGIN;
import static ge.tbc.testautomation.data.ApiConstants.Escuela.PROFILE;
import static ge.tbc.testautomation.data.ApiConstants.Escuela.USERS;
import static io.restassured.RestAssured.given;

public class EscuelaApi extends BaseApi {

    public Response createUser(EscuelaUserRequest user) {
        return given()
                .spec(ESCUELA_SPEC)
                .body(user)
                .when()
                .post(USERS);
    }

    public Response login(EscuelaLoginRequest loginRequest) {
        return given()
                .spec(ESCUELA_SPEC)
                .body(loginRequest)
                .when()
                .post(LOGIN);
    }

    public Response getProfile(String accessToken) {
        return given()
                .spec(ESCUELA_SPEC)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get(PROFILE);
    }
}
