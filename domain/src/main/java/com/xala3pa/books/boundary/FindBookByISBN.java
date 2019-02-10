package com.xala3pa.books.boundary;

import com.xala3pa.books.entity.Book;

@FunctionalInterface
public interface FindBookByISBN {
  Book getBooks(String ISBN);
}
