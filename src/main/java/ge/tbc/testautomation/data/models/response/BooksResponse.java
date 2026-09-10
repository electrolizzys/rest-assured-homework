package ge.tbc.testautomation.data.models.response;

import java.util.List;

public class BooksResponse {
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
