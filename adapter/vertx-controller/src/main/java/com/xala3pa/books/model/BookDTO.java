package com.xala3pa.books.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xala3pa.books.entity.Book;
import com.xala3pa.books.entity.BookCategory;
import com.xala3pa.books.entity.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDTO {

  private String ID;
  private String isbn;
  private String title;
  private String description;
  private BookCategory bookCategory;
  private BookStatus bookStatus;
  private String author;
  private Date dateAdded;
  private Date consumed;

  public Book toBook() {
    return Book.builder()
            .title(title)
            .author(author)
            .isbn(isbn)
            .description(description)
            .bookCategory(bookCategory)
            .bookStatus(bookStatus)
            .build();
  }

  public BookDTO toDTO(final Book book) {
    return BookDTO.builder()
            .ID(book.getID())
            .isbn(book.getIsbn())
            .title(book.getTitle())
            .description(book.getDescription())
            .bookCategory(book.getBookCategory())
            .bookStatus(book.getBookStatus())
            .author(book.getAuthor())
            .dateAdded(book.getDateAdded())
            .build();
  }
}