package com.xala3pa.books.interactor;

import com.xala3pa.books.boundary.CreateBook;
import com.xala3pa.books.entity.Book;
import com.xala3pa.books.exception.BookAlreadyExistsException;
import com.xala3pa.books.gateway.BookGateway;
import com.xala3pa.books.port.BookValidator;
import com.xala3pa.books.port.IdGenerator;
import java.time.Instant;
import static java.sql.Date.*;

public class CreateBookInteractor implements CreateBook {
  private BookGateway bookGateway;
  private BookValidator bookValidator;
  private IdGenerator idGenerator;

  public CreateBookInteractor(BookGateway bookGateway, BookValidator bookValidator, IdGenerator idGenerator) {
    this.bookGateway = bookGateway;
    this.bookValidator = bookValidator;
    this.idGenerator = idGenerator;
  }

  @Override
  public Book save(Book book) {
    bookValidator.validate(book);

    if (bookGateway.getBookByISBN(book.getIsbn()).isPresent()) {
      throw new BookAlreadyExistsException("Book already exists in our system");
    }
    Book newBook = Book.builder()
            .ID(idGenerator.generate())
            .isbn(book.getIsbn())
            .title(book.getTitle())
            .author(book.getAuthor())
            .bookCategory(book.getBookCategory())
            .bookStatus(book.getBookStatus())
            .dateAdded(from(Instant.now()))
            .build();

    return bookGateway.save(newBook);
  }
}
