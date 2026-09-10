package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.api.client.BookStoreApi;
import ge.tbc.testautomation.data.models.response.Book;
import ge.tbc.testautomation.data.models.response.BooksResponse;
import io.restassured.response.Response;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class BookStorePojoSteps {

    private final BookStoreApi bookStoreApi = new BookStoreApi();
    private BooksResponse booksResponse;

    public BookStorePojoSteps getAllBooks() {
        Response response = bookStoreApi.getBooks();
        response.then().statusCode(200);
        booksResponse = response.as(BooksResponse.class);
        return this;
    }

    public BookStorePojoSteps validateAllPagesLessThan(int maxPages) {
        List<Integer> pages = booksResponse.getBooks().stream().map(Book::getPages).toList();
        assertThat(pages, everyItem(lessThan(maxPages)));
        return this;
    }

    public BookStorePojoSteps validateLastTwoAuthors(String secondLastAuthor, String lastAuthor) {
        List<Book> books = booksResponse.getBooks();
        Book secondLast = books.get(books.size() - 2);
        Book last = books.get(books.size() - 1);
        assertThat(secondLast.getAuthor(), equalTo(secondLastAuthor));
        assertThat(last.getAuthor(), equalTo(lastAuthor));
        return this;
    }
}
