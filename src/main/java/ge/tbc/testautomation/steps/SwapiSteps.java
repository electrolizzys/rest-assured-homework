package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.api.client.SwapiApi;
import ge.tbc.testautomation.data.models.responses.swapi.PlanetDetail;
import ge.tbc.testautomation.data.models.responses.swapi.PlanetDetailResponse;
import ge.tbc.testautomation.data.models.responses.swapi.PlanetProperties;
import ge.tbc.testautomation.data.models.responses.swapi.PlanetResult;
import ge.tbc.testautomation.data.models.responses.swapi.PlanetsResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static ge.tbc.testautomation.data.ApiConstants.Swapi.FIRST_PLANET_NAME;
import static ge.tbc.testautomation.data.ApiConstants.Swapi.OK_MESSAGE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class SwapiSteps {

    private final SwapiApi swapiApi = new SwapiApi();
    private PlanetsResponse planetsResponse;
    private List<PlanetDetail> planetDetails;
    private List<PlanetDetail> mostRecentPlanets;
    private PlanetDetail fastestRotatingPlanet;

    @Step("Get planets list as record POJO")
    public SwapiSteps getPlanets() {
        Response response = swapiApi.getPlanets();
        response.then().statusCode(200);
        planetsResponse = response.as(PlanetsResponse.class);
        return this;
    }

    @Step("Validate at least five planet list fields")
    public SwapiSteps validatePlanetListFields() {
        assertThat(planetsResponse.message(), equalTo(OK_MESSAGE));
        assertThat(planetsResponse.totalRecords(), greaterThanOrEqualTo(10));
        assertThat(planetsResponse.totalPages(), greaterThanOrEqualTo(1));
        assertThat(planetsResponse.results(), notNullValue());
        assertThat(planetsResponse.results(), hasSize(greaterThanOrEqualTo(3)));
        assertThat(planetsResponse.timestamp(), notNullValue());
        assertThat(planetsResponse.results().getFirst().getName(), equalTo(FIRST_PLANET_NAME));
        return this;
    }

    @Step("Fetch every planet from results.url")
    public SwapiSteps fetchAllPlanetDetails() {
        planetDetails = planetsResponse.results().stream()
                .map(PlanetResult::getUrl)
                .map(this::getPlanetDetail)
                .toList();
        return this;
    }

    @Step("Identify the three most recent planets by created timestamp")
    public SwapiSteps identifyThreeMostRecentPlanets() {
        mostRecentPlanets = planetDetails.stream()
                .sorted(Comparator
                        .comparing((PlanetDetail detail) -> detail.getProperties().getCreated(),
                                Comparator.nullsLast(Comparator.reverseOrder()))
                        .thenComparing(detail -> Integer.parseInt(detail.getUid()), Comparator.reverseOrder()))
                .limit(3).toList();
        return this;
    }

    @Step("Validate the three most recent planets")
    public SwapiSteps validateMostRecentPlanets() {
        assertThat(mostRecentPlanets, hasSize(3));
        mostRecentPlanets.forEach(detail -> {
            PlanetProperties properties = detail.getProperties();
            assertThat(properties.getName(), not(emptyOrNullString()));
            assertThat(properties.getCreated(), notNullValue());
            assertThat(properties.getUrl(), not(emptyOrNullString()));
        });

        LocalDateTime first = mostRecentPlanets.get(0).getProperties().getCreated();
        LocalDateTime second = mostRecentPlanets.get(1).getProperties().getCreated();
        LocalDateTime third = mostRecentPlanets.get(2).getProperties().getCreated();
        assertThat(first.isBefore(second), equalTo(false));
        assertThat(second.isBefore(third), equalTo(false));
        return this;
    }

    @Step("Find the planet with the highest rotation_period")
    public SwapiSteps findPlanetWithHighestRotationPeriod() {
        fastestRotatingPlanet = planetDetails.stream()
                .filter(detail -> isNumeric(detail.getProperties().getRotationPeriod()))
                .max(Comparator.comparingInt(detail -> Integer.parseInt(detail.getProperties().getRotationPeriod())))
                .orElseThrow(() -> new AssertionError("No planet with a numeric rotation_period was found"));
        return this;
    }

    @Step("Validate the planet with the highest rotation_period")
    public SwapiSteps validateFastestRotatingPlanet() {
        PlanetProperties properties = fastestRotatingPlanet.getProperties();
        assertThat(fastestRotatingPlanet.getUid(), not(emptyOrNullString()));
        assertThat(properties.getName(), not(emptyOrNullString()));
        assertThat(properties.getRotationPeriod(), not(emptyOrNullString()));
        assertThat(properties.getUrl(), not(emptyOrNullString()));
        assertThat(properties.getCreated(), notNullValue());

        int topRotation = Integer.parseInt(properties.getRotationPeriod());
        planetDetails.stream()
                .filter(detail -> isNumeric(detail.getProperties().getRotationPeriod()))
                .forEach(detail -> assertThat(
                        Integer.parseInt(detail.getProperties().getRotationPeriod()),
                        greaterThanOrEqualTo(0)));
        assertThat(topRotation, greaterThanOrEqualTo(
                planetDetails.stream()
                        .filter(detail -> isNumeric(detail.getProperties().getRotationPeriod()))
                        .mapToInt(detail -> Integer.parseInt(detail.getProperties().getRotationPeriod()))
                        .max().orElse(0)));
        return this;
    }

    @Step("Validate Lombok planet result fields")
    public SwapiSteps validateLombokResultPojo() {
        PlanetResult firstResult = planetsResponse.results().getFirst();
        assertThat(firstResult.getUid(), equalTo("1"));
        assertThat(firstResult.getName(), equalTo(FIRST_PLANET_NAME));
        assertThat(firstResult.getUrl(), not(emptyOrNullString()));
        assertThat(firstResult.getUrl(), not(nullValue()));
        return this;
    }

    private PlanetDetail getPlanetDetail(String url) {
        Response response = swapiApi.getPlanetByUrl(url);
        response.then().statusCode(200);
        PlanetDetailResponse detailResponse = response.as(PlanetDetailResponse.class);
        assertThat(detailResponse.message(), equalTo(OK_MESSAGE));
        assertThat(detailResponse.result(), notNullValue());
        return detailResponse.result();
    }

    private boolean isNumeric(String value) {
        return value != null && value.matches("\\d+");
    }
}
