package com.xala3pa.books.boundary;

import com.xala3pa.books.entity.Book;
import java.util.List;

@FunctionalInterface
public interface FindAllBooks {

  List<Book> getBooks();
}
