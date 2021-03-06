package com.xala3pa.books.interactor;

import com.xala3pa.books.boundary.FindAllBooks;
import com.xala3pa.books.entity.Book;
import com.xala3pa.books.exception.BooksNotFoundException;
import com.xala3pa.books.gateway.BookGateway;

import java.util.List;
import java.util.Optional;

public class FindAllBooksInteractor implements FindAllBooks {
  private BookGateway bookGateway;

  public FindAllBooksInteractor(BookGateway bookGateway) {
    this.bookGateway = bookGateway;
  }

  @Override
  public List<Book> getBooks() throws BooksNotFoundException {
    Optional<List<Book>> books = bookGateway.findAllBooks();

    if (!books.isPresent() || books.get().isEmpty()) {
      throw new BooksNotFoundException("We are run out of books");
    }
    return books.get();
  }
}
