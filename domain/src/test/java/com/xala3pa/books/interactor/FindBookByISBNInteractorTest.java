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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindBookByISBNInteractorTest {

  private static final String CLEAN_ARCHITECTURE = "Clean Architecture";
  private static final String ISBN_CLEAN_ARCH = "9780134494166";
  private static final String ROBERT_C_MARTIN = "Robert C. Martin";
  private static final String UNKNOWN_ISBN = "121212121212";

  private Book cleanArchitectureBook;

  @Mock
  private BookGateway bookGateway;

  @InjectMocks
  private FindBookByISBNInteractor findBookByISBNInteractor = new FindBookByISBNInteractor(bookGateway);

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
  void should_return_the_book_by_ISBN() throws BooksNotFoundException {

    Optional<Book> cleanArchitectureBook = Optional.of(this.cleanArchitectureBook);

    when(bookGateway.findBookByISBN(anyString())).thenReturn(cleanArchitectureBook);

    final Book book = findBookByISBNInteractor.getBooks(ISBN_CLEAN_ARCH);

    assertThat(book.getIsbn()).isEqualTo(ISBN_CLEAN_ARCH);
    assertThat(book.getTitle()).isEqualTo(CLEAN_ARCHITECTURE);
  }

  @Test
  void should_throw_a_BookNotFoundException_when_there_is_not_book_by_ISBN() {

    when(bookGateway.findBookByISBN(anyString())).thenReturn(Optional.empty());

    Assertions.assertThrows(BooksNotFoundException.class, () -> findBookByISBNInteractor.getBooks(UNKNOWN_ISBN));
  }
}
