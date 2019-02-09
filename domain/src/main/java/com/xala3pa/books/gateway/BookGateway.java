package com.xala3pa.books.gateway;

import com.xala3pa.books.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookGateway {

  Book save(Book book);

  Optional<List<Book>> findAllBooks();

  Optional<List<Book>> findBooksByAuthor(String Author);

  Optional<Book> getBookByIsbn(Long isbn);
}
