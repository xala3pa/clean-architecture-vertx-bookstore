package com.xala3pa.books.port;

import com.xala3pa.books.entity.Book;

@FunctionalInterface
public interface BookValidator {
  void validate(final Book book);
}
