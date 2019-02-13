package com.xala3pa.books.boundary;

import com.xala3pa.books.entity.Book;
import com.xala3pa.books.exception.BooksNotFoundException;

import java.util.List;

@FunctionalInterface
public interface FindAllBooks {

  List<Book> getBooks() throws BooksNotFoundException;
}
