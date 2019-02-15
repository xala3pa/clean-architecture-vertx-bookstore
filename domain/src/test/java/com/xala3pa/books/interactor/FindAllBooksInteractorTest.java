package com.xala3pa.books.interactor;

import com.xala3pa.books.entity.Book;
import com.xala3pa.books.entity.BookCategory;
import com.xala3pa.books.entity.BookStatus;
import com.xala3pa.books.exception.BooksNotFoundException;
import com.xala3pa.books.gateway.BookGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllBooksInteractorTest {

  private static final String CLEAN_ARCHITECTURE = "Clean Architecture";
  private static final String ISBN_CLEAN_ARCH = "9780134494166";
  private static final String CLEAN_CODE = "Clean code";
  private static final String ISBN_CLEAN_CODE = "1973618361543";
  private static final String ROBERT_C_MARTIN = "Robert C. Martin";

  private Book cleanArchitectureBook;
  private Book cleanCodeBook;

  @Mock
  private BookGateway bookGateway;

  @InjectMocks
  private FindAllBooksInteractor findAllBooksInteractor = new FindAllBooksInteractor(bookGateway);

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);

    cleanArchitectureBook = Book.builder()
            .title(CLEAN_ARCHITECTURE)
            .isbn(ISBN_CLEAN_ARCH)
            .author(ROBERT_C_MARTIN)
            .description("Clean Architecture, a Craftsman's Guide to Software Structure and Design")
            .bookCategory(BookCategory.TECHNICAL)
            .bookStatus(BookStatus.READING)
            .build();

    cleanCodeBook = Book.builder()
            .title(CLEAN_CODE)
            .isbn(ISBN_CLEAN_CODE)
            .author(ROBERT_C_MARTIN)
            .description("Clean Code, a handbook of Agile Software Craftsmanship")
            .bookCategory(BookCategory.TECHNICAL)
            .bookStatus(BookStatus.READ)
            .build();
  }

  @Test
  void should_return_all_books() throws BooksNotFoundException {
    Optional<List<Book>> books = Optional.of(Arrays.asList(cleanArchitectureBook, cleanCodeBook));

    when(bookGateway.findAllBooks()).thenReturn(books);

    List<Book> allBooks = findAllBooksInteractor.getBooks();

    assertThat(allBooks.size()).isEqualTo(books.get().size());
  }
}