package com.xala3pa.books.boundary;

import com.xala3pa.books.entity.Book;

@FunctionalInterface
public interface CreateBook {
  Book save(Book book);
}
