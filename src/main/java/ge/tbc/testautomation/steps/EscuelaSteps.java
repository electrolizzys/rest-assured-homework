package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.api.client.EscuelaApi;
import ge.tbc.testautomation.data.models.request.EscuelaLoginRequest;
import ge.tbc.testautomation.data.models.request.EscuelaUserRequest;
import ge.tbc.testautomation.data.models.response.EscuelaAuthResponse;
import ge.tbc.testautomation.data.models.response.EscuelaUserResponse;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

public class EscuelaSteps {

    private final EscuelaApi escuelaApi = new EscuelaApi();
    private EscuelaUserRequest createdUser;
    private EscuelaUserResponse userResponse;
    private EscuelaAuthResponse authResponse;
    private EscuelaUserResponse profile;

    public EscuelaSteps createUser(EscuelaUserRequest user) {
        createdUser = user;
        Response response = escuelaApi.createUser(user);
        response.then().statusCode(Matchers.anyOf(Matchers.is(200), Matchers.is(201)));
        userResponse = response.as(EscuelaUserResponse.class);
        return this;
    }

    public EscuelaSteps loginWithCreatedUser() {
        EscuelaLoginRequest loginRequest = new EscuelaLoginRequest(createdUser.getEmail(), createdUser.getPassword());
        Response response = escuelaApi.login(loginRequest);
        response.then().statusCode(Matchers.anyOf(Matchers.is(200), Matchers.is(201)));
        authResponse = response.as(EscuelaAuthResponse.class);
        return this;
    }

    public EscuelaSteps validateTokensArePresent() {
        assertThat(authResponse.getAccessToken(), notNullValue());
        assertThat(authResponse.getAccessToken(), not(equalTo("")));
        assertThat(authResponse.getRefreshToken(), notNullValue());
        assertThat(authResponse.getRefreshToken(), not(equalTo("")));
        return this;
    }

    public EscuelaSteps getProfileWithAccessToken() {
        Response response = escuelaApi.getProfile(authResponse.getAccessToken());
        response.then().statusCode(200);
        profile = response.as(EscuelaUserResponse.class);
        return this;
    }

    public EscuelaSteps validateProfileMatchesCreatedUser() {
        assertThat(profile.getEmail(), equalTo(createdUser.getEmail()));
        assertThat(profile.getName(), equalTo(createdUser.getName()));
        assertThat(profile.getAvatar(), equalTo(createdUser.getAvatar()));
        assertThat(profile.getId(), equalTo(userResponse.getId()));
        return this;
    }
}
