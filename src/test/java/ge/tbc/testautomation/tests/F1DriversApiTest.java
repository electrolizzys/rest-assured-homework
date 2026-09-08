package ge.tbc.testautomation.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static ge.tbc.testautomation.data.ApiConstants.Ergast.BASE_URI;
import static ge.tbc.testautomation.data.ApiConstants.Ergast.BRAZILIAN;
import static ge.tbc.testautomation.data.ApiConstants.Ergast.BRITISH;
import static ge.tbc.testautomation.data.ApiConstants.Ergast.DRIVERS_2025;
import static ge.tbc.testautomation.data.ApiConstants.Ergast.FIRST_DRIVER_BORN_BEFORE_1990;
import static ge.tbc.testautomation.data.ApiConstants.Ergast.FRENCH;
import static ge.tbc.testautomation.data.ApiConstants.Ergast.GEORGE;
import static ge.tbc.testautomation.data.ApiConstants.Ergast.PIERRE_GASLY;
import static ge.tbc.testautomation.data.ApiConstants.Ergast.SEASON;
import static ge.tbc.testautomation.data.ApiConstants.Ergast.SERIES;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;

public class F1DriversApiTest {

    private static final String DRIVERS = "MRData.DriverTable.Drivers";
    private Response response;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        response = given()
                .accept(ContentType.JSON)
                .queryParam("limit", 100)
                .when()
                .get(DRIVERS_2025)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    @Test
    public void seriesAndSeasonAreCorrect() {
        response.then()
                .body("MRData.series", equalTo(SERIES))
                .body("MRData.DriverTable.season", equalTo(SEASON));
    }

    @Test
    public void driversArraySizeEqualsTotal() {
        int total = Integer.parseInt(response.path("MRData.total"));
        int driverCount = response.path(DRIVERS + ".size()");
        assertThat(driverCount, equalTo(total));
    }

    @Test
    public void firstDriverBornBefore1990HasExpectedFullName() {
        String givenName = jsonPath().getString(
                DRIVERS + ".find { it.dateOfBirth != null && it.dateOfBirth < '1990-01-01' }.givenName");
        String familyName = jsonPath().getString(
                DRIVERS + ".find { it.dateOfBirth != null && it.dateOfBirth < '1990-01-01' }.familyName");
        assertThat(givenName + " " + familyName, equalTo(FIRST_DRIVER_BORN_BEFORE_1990));
    }

    @Test
    public void driversBornAfter2000CountIsAtLeast8() {
        List<String> names = jsonPath().getList(
                DRIVERS + ".findAll { it.dateOfBirth != null && it.dateOfBirth.substring(0, 4).toInteger() > 2000 }"
                        + ".collect { it.givenName + ' ' + it.familyName }");
        assertThat(names, hasSize(greaterThanOrEqualTo(8)));
    }

    @Test
    public void frenchDriversCountIsExactly3() {
        int frenchCount = jsonPath().param("nationality", FRENCH)
                .getInt(DRIVERS + ".findAll { it.nationality == nationality }.size()");
        assertThat(frenchCount, equalTo(3));
    }

    @Test
    public void familyNameStartingWithAOrBCountIsAtLeast5() {
        int count = jsonPath().getInt(
                DRIVERS + ".findAll { it.familyName.startsWith('A') || it.familyName.startsWith('B') }.size()");
        assertThat(count, greaterThanOrEqualTo(5));
    }

    @Test
    public void britishDriversBornAfter1990CountIsAtLeast3() {
        int count = jsonPath()
                .param("nationality", BRITISH)
                .param("minDob", "1990-01-01")
                .getInt(DRIVERS + ".findAll { it.nationality == nationality && it.dateOfBirth != null && it.dateOfBirth > minDob }.size()");
        assertThat(count, greaterThanOrEqualTo(3));
    }

    @Test
    public void driversWithLowPermanentNumberOrLongFamilyName() {
        List<String> names = jsonPath()
                .param("maxNumber", 10)
                .param("minNameLength", 7)
                .getList(DRIVERS + ".findAll { (it.permanentNumber?.isInteger() && it.permanentNumber.toInteger() < maxNumber) || it.familyName.length() > minNameLength }"
                        + ".collect { it.givenName + ' ' + it.familyName }");
        names.forEach(System.out::println);
        assertThat(names, hasSize(greaterThanOrEqualTo(5)));
    }

    @Test
    public void findDriversByNationalityValidations() {
        List<String> britishDrivers = findDriversByNationality(BRITISH);
        assertThat(britishDrivers, hasItem(containsString(GEORGE)));

        List<String> brazilianDrivers = findDriversByNationality(BRAZILIAN);
        assertThat(brazilianDrivers, not(hasSize(0)));

        List<String> frenchDrivers = findDriversByNationality(FRENCH);
        assertThat(frenchDrivers, hasItem(PIERRE_GASLY));
    }

    private List<String> findDriversByNationality(String nationality) {
        return jsonPath()
                .param("nationality", nationality)
                .getList(DRIVERS + ".findAll { it.nationality == nationality }.collect { it.givenName + ' ' + it.familyName }");
    }

    private JsonPath jsonPath() {
        return response.jsonPath();
    }
}
