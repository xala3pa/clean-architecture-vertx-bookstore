package com.xala3pa.books;

import com.xala3pa.books.boundary.CreateBook;
import com.xala3pa.books.boundary.FindAllBooks;
import com.xala3pa.books.controller.VertxBookController;
import com.xala3pa.books.gateway.BookGateway;
import com.xala3pa.books.gateway.InMemoryRepository;
import com.xala3pa.books.interactor.CreateBookInteractor;
import com.xala3pa.books.interactor.FindAllBookInteractor;
import com.xala3pa.books.port.BookValidator;
import com.xala3pa.books.port.IdGenerator;

public class VertxConfig {

  private final BookGateway bookGateway = new InMemoryRepository();
  private final IdGenerator idGenerator = new IdGeneratorUUID();
  private final BookValidator bookValidator = new BookValidatorImpl();
  private final CreateBook createBook = new CreateBookInteractor(bookGateway, bookValidator, idGenerator);
  private final FindAllBooks findAllBooks = new FindAllBookInteractor(bookGateway);

  private final VertxBookController bookController = new VertxBookController(createBook, findAllBooks);

  public VertxBookController getVertxBookController() {
    return bookController;
  }
}
