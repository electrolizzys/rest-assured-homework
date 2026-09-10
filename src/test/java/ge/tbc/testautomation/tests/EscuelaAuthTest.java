package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.models.request.EscuelaUserRequest;
import ge.tbc.testautomation.steps.EscuelaSteps;
import net.datafaker.Faker;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.ApiConstants.Escuela.AVATAR;

public class EscuelaAuthTest {

    private final EscuelaSteps escuelaSteps = new EscuelaSteps();
    private final Faker faker = new Faker();

    @Test
    public void createUserLoginAndValidateProfile() {
        EscuelaUserRequest user = new EscuelaUserRequest(
                faker.name().fullName(),
                faker.internet().uuid().substring(0, 8) + "@mail.com",
                "Pass1234", AVATAR);

        escuelaSteps
                .createUser(user).loginWithCreatedUser()
                .validateTokensArePresent()
                .getProfileWithAccessToken()
                .validateProfileMatchesCreatedUser();
    }
}
