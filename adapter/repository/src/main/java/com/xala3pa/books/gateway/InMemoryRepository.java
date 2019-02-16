package com.xala3pa.books.gateway;

import com.xala3pa.books.entity.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class InMemoryRepository implements BookGateway {

  private final ConcurrentMap<String, Book> inMemoryDB = new ConcurrentHashMap<>();

  @Override
  public Book save(Book book) {
    return inMemoryDB.put(book.getID(), book);
  }

  @Override
  public Optional<List<Book>> findAllBooks() {
    return Optional.of(new ArrayList<>(inMemoryDB.values()));
  }

  @Override
  public Optional<List<Book>> findBooksByAuthor(String author) {
    return Optional.of(inMemoryDB.values()
            .stream()
            .filter(book -> book.getAuthor().equalsIgnoreCase(author))
            .collect(Collectors.toList()));
  }

  @Override
  public Optional<Book> findBookByISBN(String isbn) {
    return inMemoryDB.values()
            .stream()
            .filter(book -> book.getIsbn().equalsIgnoreCase(isbn))
            .findAny();
  }
}
