package com.xala3pa.books.interactor;

import com.xala3pa.books.entity.Book;
import com.xala3pa.books.entity.BookCategory;
import com.xala3pa.books.entity.BookStatus;
import com.xala3pa.books.exception.BookAlreadyExistsException;
import com.xala3pa.books.exception.BookValidationException;
import com.xala3pa.books.exception.BooksNotFoundException;
import com.xala3pa.books.gateway.BookGateway;
import com.xala3pa.books.port.BookValidator;
import com.xala3pa.books.port.IdGenerator;
import org.assertj.core.condition.AnyOf;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static java.util.Date.from;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateBookInteractorTest {

  private static final String CLEAN_ARCHITECTURE = "Clean Architecture";
  private static final String ISBN_CLEAN_ARCH = "9780134494166";
  private static final String ROBERT_C_MARTIN = "Robert C. Martin";
  private static final String UNKNOWN_AUTHOR = "unkown";

  private Book cleanArchitectureBook;

  @Mock
  private BookGateway bookGateway;
  @Mock
  private IdGenerator idGenerator;
  @Mock
  private BookValidator bookValidator;

  @InjectMocks
  private CreateBookInteractor createBookInteractor = new CreateBookInteractor(bookGateway, bookValidator, idGenerator);

  @BeforeEach
  void setUp() {
    cleanArchitectureBook = Book.builder()
            .title(CLEAN_ARCHITECTURE)
            .isbn(ISBN_CLEAN_ARCH)
            .author(ROBERT_C_MARTIN)
            .description("Clean Architecture, a Craftsman's Guide to Software Structure and Design")
            .bookCategory(BookCategory.TECHNICAL)
            .bookStatus(BookStatus.READING)
            .build();
  }

  @Test
  void should_persist_the_book_when_we_create_one() {

    String bookId = UUID.randomUUID().toString();
    Book bookPersisted = cleanArchitectureBook;
    bookPersisted.setID(bookId);


    doNothing().when(bookValidator).validate(cleanArchitectureBook);
    when(idGenerator.generate()).thenReturn(bookId);
    when(bookGateway.findBookByISBN(ISBN_CLEAN_ARCH)).thenReturn(Optional.empty());
    when(bookGateway.save(any(Book.class))).thenReturn(bookPersisted);

    Book bookAdded = createBookInteractor.save(cleanArchitectureBook);

    assertThat(bookAdded.getID()).isEqualTo(bookPersisted.getID());
  }

  @Test
  void should_throw_a_BookAlreadyExistsException_when_book_already_exist() {
    doNothing().when(bookValidator).validate(cleanArchitectureBook);
    when(bookGateway.findBookByISBN(ISBN_CLEAN_ARCH)).thenReturn(Optional.of(cleanArchitectureBook));

    Assertions.assertThrows(BookAlreadyExistsException.class, () -> createBookInteractor.save(cleanArchitectureBook));
  }

  @Test
  void should_return_a_validation_error_when_book_data_is_not_correct() {
    doThrow(BookValidationException.class).when(bookValidator).validate(cleanArchitectureBook);

    Assertions.assertThrows(BookValidationException.class, () -> createBookInteractor.save(cleanArchitectureBook));
  }
}