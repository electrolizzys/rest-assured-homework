package ge.tbc.testautomation.data;

public final class ApiConstants {

    private ApiConstants() {
    }

    public static final class BookStore {
        public static final String BASE_URI = "https://bookstore.toolsqa.com";
        public static final String BOOKS = "/BookStore/v1/Books";
        public static final String BOOK = "/BookStore/v1/Book";
        public static final String ISBN_PARAM = "ISBN";
        public static final String UNAUTHORIZED_MESSAGE = "User not authorized!";
    }

    public static final class PetStore {
        public static final String BASE_URI = "https://petstore.swagger.io/v2";
        public static final String ORDER = "/store/order";
        public static final String PET = "/pet";
        public static final String PET_BY_ID = "/pet/{petId}";
        public static final String LOGIN = "/user/login";
        public static final String USERNAME_PARAM = "username";
        public static final String PASSWORD_PARAM = "password";
        public static final String NAME_PARAM = "name";
        public static final String STATUS_PARAM = "status";
    }

    public static final class OpenLibrary {
        public static final String BASE_URI = "https://openlibrary.org";
        public static final String SEARCH = "/search.json";
        public static final String QUERY_PARAM = "q";
        public static final String KEYWORD = "Harry Potter";
        public static final String EXPECTED_TITLE = "Harry Potter and the Philosopher's Stone";
        public static final String EXPECTED_AUTHOR = "J. K. Rowling";
    }
}
