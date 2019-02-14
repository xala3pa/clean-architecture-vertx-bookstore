package com.xala3pa.books;

import com.xala3pa.books.boundary.CreateBook;
import com.xala3pa.books.boundary.FindAllBooks;
import com.xala3pa.books.boundary.FindBookByISBN;
import com.xala3pa.books.boundary.FindBooksByAuthor;
import com.xala3pa.books.controller.VertxBookController;
import com.xala3pa.books.entity.Book;
import com.xala3pa.books.entity.BookCategory;
import com.xala3pa.books.entity.BookStatus;
import com.xala3pa.books.gateway.BookGateway;
import com.xala3pa.books.gateway.InMemoryRepository;
import com.xala3pa.books.interactor.CreateBookInteractor;
import com.xala3pa.books.interactor.FindAllBookInteractor;
import com.xala3pa.books.interactor.FindBookByISBNInteractor;
import com.xala3pa.books.interactor.FindBooksByAuthorInteractor;
import com.xala3pa.books.port.BookValidator;
import com.xala3pa.books.port.IdGenerator;

import java.util.stream.Stream;

public class VertxConfig {

  private final BookGateway bookGateway = new InMemoryRepository();
  private final IdGenerator idGenerator = new IdGeneratorUUID();
  private final BookValidator bookValidator = new BookValidatorImpl();
  private final CreateBook createBook = new CreateBookInteractor(bookGateway, bookValidator, idGenerator);
  private final FindAllBooks findAllBooks = new FindAllBookInteractor(bookGateway);
  private final FindBookByISBN findBookByISBN = new FindBookByISBNInteractor(bookGateway);
  private final FindBooksByAuthor findBooksByAuthor = new FindBooksByAuthorInteractor(bookGateway);

  private final VertxBookController bookController = new VertxBookController(createBook, findAllBooks, findBookByISBN, findBooksByAuthor);

  public VertxBookController getVertxBookController() {
    return bookController;
  }

  public void initializeData() {
    Book fantasyBook = Book.builder()
            .ID(idGenerator.generate())
            .isbn("978-0-596-52767-9")
            .title("The Art of Agile Development")
            .author("James Shore & Shane warden")
            .bookStatus(BookStatus.READ)
            .bookCategory(BookCategory.TECHNICAL)
            .description("Practical guidelines for anyone using agile development to build valuable software")
            .build();
    Book cookingBook = Book.builder()
            .ID(idGenerator.generate())
            .isbn("978-1-60774-605-8")
            .title("The Pizza Bible")
            .author("Tony Gemignani")
            .bookStatus(BookStatus.READ)
            .bookCategory(BookCategory.COOK)
            .description("The world's Pizza styles, from neapolitan, deep-dish to New York, Detroit, and More")
            .build();

    Stream.of(fantasyBook, cookingBook).forEach(bookGateway::save);
  }
}
