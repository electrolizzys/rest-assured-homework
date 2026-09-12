package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.steps.SwapiSteps;
import org.testng.annotations.Test;

public class SwapiPlanetsTest {

    private final SwapiSteps swapiSteps = new SwapiSteps();

    @Test
    public void validatePlanetsListMostRecentAndFastestRotation() {
        swapiSteps
                .getPlanets().validatePlanetListFields()
                .validateLombokResultPojo().fetchAllPlanetDetails()
                .identifyThreeMostRecentPlanets()
                .validateMostRecentPlanets()
                .findPlanetWithHighestRotationPeriod()
                .validateFastestRotatingPlanet();
    }
}
