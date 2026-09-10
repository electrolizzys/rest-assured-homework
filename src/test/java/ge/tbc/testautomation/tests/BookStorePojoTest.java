package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.steps.BookStorePojoSteps;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.ApiConstants.BookStore.LAST_BOOK_AUTHOR;
import static ge.tbc.testautomation.data.ApiConstants.BookStore.SECOND_LAST_BOOK_AUTHOR;

public class BookStorePojoTest {

    private final BookStorePojoSteps bookStoreSteps = new BookStorePojoSteps();

    @Test
    public void getBooksAsPojoAndValidateAuthorsAndPages() {
        bookStoreSteps
                .getAllBooks()
                .validateAllPagesLessThan(1000)
                .validateLastTwoAuthors(SECOND_LAST_BOOK_AUTHOR, LAST_BOOK_AUTHOR);
    }
}
