package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.api.client.F1Api;
import ge.tbc.testautomation.data.models.response.Driver;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class F1PojoSteps {

    private final F1Api f1Api = new F1Api();
    private Driver actualDriver;

    public F1PojoSteps getFirstDriverAsPojo() {
        Response response = f1Api.getDrivers();
        response.then().statusCode(200);
        JsonPath jsonPath = response.jsonPath();
        actualDriver = jsonPath.getObject("MRData.DriverTable.Drivers[0]", Driver.class);
        return this;
    }

    public F1PojoSteps validateDriverMatches(Driver expectedDriver) {
        assertThat(actualDriver, equalTo(expectedDriver));
        return this;
    }
}
