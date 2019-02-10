package com.xala3pa.books.interactor;

import com.xala3pa.books.boundary.FindBooksByAuthor;
import com.xala3pa.books.entity.Book;
import com.xala3pa.books.exception.BooksNotFoundException;
import com.xala3pa.books.gateway.BookGateway;

import java.util.List;
import java.util.Optional;

public class FindBooksByAuthorInteractor implements FindBooksByAuthor {
  private BookGateway bookGateway;

  public FindBooksByAuthorInteractor(BookGateway bookGateway) {
    this.bookGateway = bookGateway;
  }

  @Override
  public List<Book> getBooks(String author) {
    Optional<List<Book>> books = bookGateway.findBooksByAuthor(author);
    if (!books.isPresent()) {
      throw new BooksNotFoundException("No books found written by " + author);
    }
    return books.get();
  }
}
