package tests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.empty;
import static data.ApiConstants.OpenLibrary.BASE_URI;
import static data.ApiConstants.OpenLibrary.EXPECTED_AUTHOR;
import static data.ApiConstants.OpenLibrary.EXPECTED_TITLE;
import static data.ApiConstants.OpenLibrary.KEYWORD;
import static data.ApiConstants.OpenLibrary.QUERY_PARAM;
import static data.ApiConstants.OpenLibrary.SEARCH;

public class OpenLibraryApiTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
    }

    @Test
    public void searchHarryPotterReturnsExpectedFirstBook() {
        given()
                .header("User-Agent", "rest-assured-homework/1.0")
                .queryParam(QUERY_PARAM, KEYWORD)
                .when()
                .get(SEARCH)
                .then()
                .statusCode(200)
                .body("numFound", greaterThan(0))
                .body("docs", not(empty()))
                .body("docs[0].title", equalTo(EXPECTED_TITLE))
                .body("docs[0].author_name", hasItem(EXPECTED_AUTHOR));
    }
}
