package com.xala3pa.books.exception;

public class BooksNotFoundException extends RuntimeException {
  public BooksNotFoundException(String message) {
    super(message);
  }
}
