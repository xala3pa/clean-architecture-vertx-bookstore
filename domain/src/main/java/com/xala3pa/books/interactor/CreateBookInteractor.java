package com.xala3pa.books.interactor;

import com.xala3pa.books.boundary.CreateBook;
import com.xala3pa.books.entity.Book;
import com.xala3pa.books.gateway.BookGateway;

public class CreateBookInteractor implements CreateBook {
  private BookGateway bookGateway;

  public CreateBookInteractor(BookGateway bookGateway) {
    this.bookGateway = bookGateway;
  }

  @Override
  public Book save(Book book) {
   return bookGateway.save(book);
  }
}
