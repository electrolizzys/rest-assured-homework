package ge.tbc.testautomation.api.client;

import io.restassured.response.Response;

import static ge.tbc.testautomation.data.ApiConstants.BookStore.BOOKS;
import static io.restassured.RestAssured.given;

public class BookStoreApi extends BaseApi {

    public Response getBooks() {
        return given()
                .spec(BOOKSTORE_SPEC)
                .when()
                .get(BOOKS);
    }
}
