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
        public static final String SECOND_LAST_BOOK_AUTHOR = "Marijn Haverbeke";
        public static final String LAST_BOOK_AUTHOR = "Nicholas C. Zakas";
    }

    public static final class Escuela {
        public static final String BASE_URI = "https://api.escuelajs.co/api";
        public static final String USERS = "/v1/users";
        public static final String LOGIN = "/v1/auth/login";
        public static final String PROFILE = "/v1/auth/profile";
        public static final String AVATAR = "https://picsum.photos/800";
    }

    public static final class Booking {
        public static final String BASE_URI = "https://restful-booker.herokuapp.com";
        public static final String AUTH = "/auth";
        public static final String BOOKING = "/booking";
        public static final String BOOKING_BY_ID = "/booking/{id}";
        public static final String USERNAME = "admin";
        public static final String PASSWORD = "password123";
    }

    public static final class Swapi {
        public static final String BASE_URI = "https://swapi.tech/api";
        public static final String PLANETS = "/planets/";
        public static final String FORMAT_PARAM = "format";
        public static final String JSON_FORMAT = "json";
        public static final String OK_MESSAGE = "ok";
        public static final String FIRST_PLANET_NAME = "Tatooine";
    }

    public static final class PetStoreV3 {
        public static final String BASE_URI = "https://petstore3.swagger.io/api/v3";
        public static final String ORDER = "/store/order";
        public static final String PLACED_STATUS = "placed";
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
