package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.models.response.Driver;
import ge.tbc.testautomation.steps.F1PojoSteps;
import org.testng.annotations.Test;

public class F1DriverPojoTest {

    private final F1PojoSteps f1PojoSteps = new F1PojoSteps();

    @Test
    public void firstDriverMatchesInitializedPojo() {
        Driver expectedDriver = new Driver("albon", 23);

        f1PojoSteps
                .getFirstDriverAsPojo()
                .validateDriverMatches(expectedDriver);
    }
}
