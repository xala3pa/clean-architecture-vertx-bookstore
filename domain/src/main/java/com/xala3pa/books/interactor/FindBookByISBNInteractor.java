package com.xala3pa.books.interactor;

import com.xala3pa.books.boundary.FindBookByISBN;
import com.xala3pa.books.entity.Book;
import com.xala3pa.books.exception.BooksNotFoundException;
import com.xala3pa.books.gateway.BookGateway;

import java.util.Optional;

public class FindBookByISBNInteractor implements FindBookByISBN {
  private BookGateway bookGateway;

  public FindBookByISBNInteractor(BookGateway bookGateway) {
    this.bookGateway = bookGateway;
  }

  @Override
  public Book getBooks(String ISBN) {
    Optional<Book> book = bookGateway.getBookByISBN(ISBN);

    if (!book.isPresent()) {
      throw new BooksNotFoundException("No book found with this ISBN: " + ISBN);
    }
    return book.get();
  }
}
