package ge.tbc.testautomation.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ge.tbc.testautomation.data.ApiConstants.PetStore.AVAILABLE_STATUS;
import static ge.tbc.testautomation.data.ApiConstants.PetStore.BASE_URI;
import static ge.tbc.testautomation.data.ApiConstants.PetStore.EXAMPLE_IMAGE;
import static ge.tbc.testautomation.data.ApiConstants.PetStore.FIND_BY_STATUS;
import static ge.tbc.testautomation.data.ApiConstants.PetStore.LOGIN;
import static ge.tbc.testautomation.data.ApiConstants.PetStore.NAME_PARAM;
import static ge.tbc.testautomation.data.ApiConstants.PetStore.ORDER;
import static ge.tbc.testautomation.data.ApiConstants.PetStore.PASSWORD_PARAM;
import static ge.tbc.testautomation.data.ApiConstants.PetStore.PET;
import static ge.tbc.testautomation.data.ApiConstants.PetStore.PET_BY_ID;
import static ge.tbc.testautomation.data.ApiConstants.PetStore.SOLD_STATUS;
import static ge.tbc.testautomation.data.ApiConstants.PetStore.STATUS_PARAM;
import static ge.tbc.testautomation.data.ApiConstants.PetStore.UPLOAD_IMAGE;
import static ge.tbc.testautomation.data.ApiConstants.PetStore.USERNAME_PARAM;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PetStoreApiTest {

    private int petId;
    private String petName;
    private String photoUrl;
    private String updatedPetName;

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
                .post(PET_BY_ID, 123456789L)
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
        assertThat("Login message should contain at least 10 significant numbers: " + message, matcher.find(), is(true));
        System.out.println("Extracted session number: " + matcher.group(1));
    }

    @Test
    public void addNewPetToStore() {
        Faker faker = new Faker();
        petId = ThreadLocalRandom.current().nextInt(100_000, 1_000_000_000);
        petName = faker.dog().name();
        photoUrl = faker.internet().url();

        JSONObject pet = new JSONObject();
        pet.put("id", petId);
        pet.put("name", petName);
        pet.put("status", AVAILABLE_STATUS);
        pet.put("photoUrls", new JSONArray().put(photoUrl));

        given()
                .contentType(ContentType.JSON)
                .body(pet.toString())
                .when()
                .post(PET)
                .then()
                .statusCode(200)
                .body("id", equalTo(petId))
                .body("name", equalTo(petName))
                .body("status", equalTo(AVAILABLE_STATUS))
                .body("photoUrls", hasItem(photoUrl));
    }

    @Test(dependsOnMethods = "addNewPetToStore")
    public void findPetsByStatusContainsCreatedPet() {
        Response findResponse = given()
                .queryParam(STATUS_PARAM, AVAILABLE_STATUS)
                .when()
                .get(FIND_BY_STATUS)
                .then()
                .statusCode(200)
                .body("id", hasItem(petId))
                .extract()
                .response();

        Map<String, Object> createdPet = findResponse.path("find { it.id == " + petId + " }");
        assertThat(createdPet, notNullValue());
        assertThat(createdPet.get("name"), equalTo(petName));
        assertThat(createdPet.get("status"), equalTo(AVAILABLE_STATUS));
        @SuppressWarnings("unchecked")
        List<String> photoUrls = (List<String>) createdPet.get("photoUrls");
        assertThat(photoUrls, hasItem(photoUrl));
    }

    @Test(dependsOnMethods = "findPetsByStatusContainsCreatedPet")
    public void updatePetWithFormDataToSold() {
        updatedPetName = petName + "-sold";

        given()
                .contentType(ContentType.URLENC)
                .formParam(NAME_PARAM, updatedPetName)
                .formParam(STATUS_PARAM, SOLD_STATUS)
                .when()
                .post(PET_BY_ID, petId)
                .then()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "updatePetWithFormDataToSold")
    public void getPetAndVerifyNameAndStatusChanged() {
        given()
                .when()
                .get(PET_BY_ID, petId)
                .then()
                .statusCode(200)
                .body("name", equalTo(updatedPetName))
                .body("status", equalTo(SOLD_STATUS));
    }

    @Test(dependsOnMethods = "addNewPetToStore")
    public void uploadPetImage() throws URISyntaxException {
        String additionalMetadata = "homework-pet-photo";
        File imageFile = new File(Objects.requireNonNull(
                getClass().getClassLoader().getResource(EXAMPLE_IMAGE)).toURI());
        long fileSize = imageFile.length();

        given()
                .header("Accept", "application/json")
                .multiPart("additionalMetadata", additionalMetadata)
                .multiPart("file", imageFile, "image/jpeg")
                .when()
                .post(UPLOAD_IMAGE, petId)
                .then()
                .statusCode(200)
                .body("message", containsString(additionalMetadata))
                .body("message", containsString(EXAMPLE_IMAGE))
                .body("message", containsString(fileSize + " bytes"));
    }
}
