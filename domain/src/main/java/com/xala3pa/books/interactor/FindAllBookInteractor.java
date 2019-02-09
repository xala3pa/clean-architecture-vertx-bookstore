package com.xala3pa.books.interactor;

import com.xala3pa.books.boundary.FindAllBooks;
import com.xala3pa.books.entity.Book;
import com.xala3pa.books.exception.BooksNotFoundException;
import com.xala3pa.books.gateway.BookGateway;

import java.util.List;
import java.util.Optional;

public class FindAllBookInteractor implements FindAllBooks {
  private BookGateway bookGateway;

  public FindAllBookInteractor(BookGateway bookGateway) {
    this.bookGateway = bookGateway;
  }

  @Override
  public List<Book> getBooks() {
    Optional<List<Book>> books = bookGateway.findAllBooks();

    if (!books.isPresent()) {
      throw new BooksNotFoundException("We are run out of books");
    }
    return books.get();
  }
}
