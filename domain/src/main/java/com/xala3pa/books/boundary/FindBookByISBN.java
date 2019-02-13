package com.xala3pa.books.boundary;

import com.xala3pa.books.entity.Book;
import com.xala3pa.books.exception.BooksNotFoundException;

@FunctionalInterface
public interface FindBookByISBN {
  Book getBooks(String ISBN) throws BooksNotFoundException;
}
