package ge.tbc.testautomation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static ge.tbc.testautomation.data.ApiConstants.BookStore.BASE_URI;
import static ge.tbc.testautomation.data.ApiConstants.BookStore.BOOK;
import static ge.tbc.testautomation.data.ApiConstants.BookStore.BOOKS;
import static ge.tbc.testautomation.data.ApiConstants.BookStore.ISBN_PARAM;
import static ge.tbc.testautomation.data.ApiConstants.BookStore.UNAUTHORIZED_MESSAGE;

public class BookStoreApiTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
    }

    @Test
    public void getBooksThenValidateFirstAndSecondByIsbn() {
        Response booksResponse = given()
                .when()
                .get(BOOKS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        String firstIsbn = booksResponse.path("books[0].isbn");
        String firstAuthor = booksResponse.path("books[0].author");
        String secondIsbn = booksResponse.path("books[1].isbn");
        String secondAuthor = booksResponse.path("books[1].author");
        validateBookByIsbn(firstIsbn, firstAuthor);
        validateBookByIsbn(secondIsbn, secondAuthor);
    }

    @DataProvider(name = "bookIndexAndIsbn")
    public Object[][] bookIndexAndIsbn() {
        Response booksResponse = given()
                .when().get(BOOKS).then()
                .statusCode(200).extract().response();

        return new Object[][]{
                {0, booksResponse.path("books[0].isbn"), booksResponse.path("books[0].author")},
                {1, booksResponse.path("books[1].isbn"), booksResponse.path("books[1].author")}
        };
    }

    @Test(dataProvider = "bookIndexAndIsbn")
    public void getBookByIndexAndIsbn(int index, String isbn, String expectedAuthor) {
        given()
                .when().get(BOOKS)
                .then().statusCode(200)
                .body("books[" + index + "].isbn", equalTo(isbn));

        validateBookByIsbn(isbn, expectedAuthor);
    }

    @Test
    public void deleteBookWithoutAuthReturnsUnauthorized() {
        String body = """
                {
                  "isbn": "9781449325862",
                  "userId": "11111111-1111-1111-1111-111111111111"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(body).when().delete(BOOK)
                .then().statusCode(401)
                .body("message", equalTo(UNAUTHORIZED_MESSAGE));
    }

    private void validateBookByIsbn(String isbn, String expectedAuthor) {
        given()
                .queryParam(ISBN_PARAM, isbn)
                .when()
                .get(BOOK)
                .then()
                .statusCode(200)
                .body("isbn", equalTo(isbn))
                .body("author", equalTo(expectedAuthor))
                .body("title", notNullValue())
                .body("publish_date", notNullValue())
                .body("pages", notNullValue());
    }
}
