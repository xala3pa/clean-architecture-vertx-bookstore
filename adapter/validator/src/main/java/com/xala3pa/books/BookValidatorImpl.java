package com.xala3pa.books;

import com.xala3pa.books.entity.Book;
import com.xala3pa.books.exception.BookValidationException;
import com.xala3pa.books.port.BookValidator;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class BookValidatorImpl implements BookValidator {
  @Override
  public void validate(Book book) {
    if (book == null) throw new BookValidationException("Book should not be null");
    if (isBlank(book.getIsbn())) throw new BookValidationException("Book ISBN should not be blank");
    if (isBlank(book.getAuthor())) throw new BookValidationException("Author should not be blank");
    if (isBlank(book.getTitle())) throw new BookValidationException("Title should not be blank");
  }
}
