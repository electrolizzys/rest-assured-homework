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
        public static final String FIRST_BOOK_AUTHOR = "Richard E. Silverman";
        public static final String SECOND_BOOK_AUTHOR = "Addy Osmani";
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
        public static final String FIND_BY_STATUS = "/pet/findByStatus";
        public static final String UPLOAD_IMAGE = "/pet/{petId}/uploadImage";
        public static final String EXAMPLE_IMAGE = "exampleimage.jpg";
        public static final String AVAILABLE_STATUS = "available";
        public static final String SOLD_STATUS = "sold";
    }

    public static final class Ergast {
        public static final String BASE_URI = "https://api.jolpi.ca";
        public static final String DRIVERS_2025 = "/ergast/f1/2025/drivers/";
        public static final String SERIES = "f1";
        public static final String SEASON = "2025";
        public static final String FIRST_DRIVER_BORN_BEFORE_1990 = "Fernando Alonso";
        public static final String BRITISH = "British";
        public static final String BRAZILIAN = "Brazilian";
        public static final String FRENCH = "French";
        public static final String GEORGE = "George";
        public static final String PIERRE_GASLY = "Pierre Gasly";
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
