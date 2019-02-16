package com.xala3pa.books.interactor;

import com.xala3pa.books.entity.Book;
import com.xala3pa.books.entity.BookCategory;
import com.xala3pa.books.entity.BookStatus;
import com.xala3pa.books.exception.BooksNotFoundException;
import com.xala3pa.books.gateway.BookGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindBooksByAuthorInteractorTest {

  private static final String CLEAN_ARCHITECTURE = "Clean Architecture";
  private static final String ISBN_CLEAN_ARCH = "9780134494166";
  private static final String ROBERT_C_MARTIN = "Robert C. Martin";
  private static final String UNKNOWN_AUTHOR = "unkown";

  private Book cleanArchitectureBook;

  @Mock
  private BookGateway bookGateway;

  @InjectMocks
  private FindBooksByAuthorInteractor findBooksByAuthorInteractor = new FindBooksByAuthorInteractor(bookGateway);

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
  void should_return_the_book_by_author() throws BooksNotFoundException {

    Optional<List<Book>> books = Optional.of(Collections.singletonList(cleanArchitectureBook));

    when(bookGateway.findBooksByAuthor(anyString())).thenReturn(books);

    List<Book> booksByAuthor = findBooksByAuthorInteractor.getBooks(ROBERT_C_MARTIN);

    assertThat(booksByAuthor).hasSize(books.get().size())
            .contains(cleanArchitectureBook);

  }

  @Test
  void should_throw_a_BooksNotFoundException_when_we_do_not_have_books() {

    when(bookGateway.findBooksByAuthor(anyString())).thenReturn(Optional.of(new ArrayList<>()));

    Assertions.assertThrows(BooksNotFoundException.class, () -> findBooksByAuthorInteractor.getBooks(UNKNOWN_AUTHOR));
  }
}