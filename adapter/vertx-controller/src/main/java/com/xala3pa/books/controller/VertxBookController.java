package com.xala3pa.books.controller;

import com.xala3pa.books.boundary.CreateBook;
import com.xala3pa.books.boundary.FindAllBooks;
import com.xala3pa.books.boundary.FindBookByISBN;
import com.xala3pa.books.boundary.FindBooksByAuthor;
import com.xala3pa.books.entity.Book;
import com.xala3pa.books.exception.BooksNotFoundException;
import com.xala3pa.books.model.BookDTO;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

public class VertxBookController {

  private static final int CREATED = 201;
  private static final int BAD_REQUEST = 400;
  private static final int OK = 200;
  private static final int NOT_FOUND = 404;
  private final CreateBook createBook;
  private final FindAllBooks findAllBooks;
  private final FindBookByISBN findBookByISBN;
  private final FindBooksByAuthor findBooksByAuthor;

  public VertxBookController(CreateBook createBook, FindAllBooks findAllBooks, FindBookByISBN findBookByISBN, FindBooksByAuthor findBooksByAuthor) {
    this.createBook = createBook;
    this.findAllBooks = findAllBooks;
    this.findBookByISBN = findBookByISBN;
    this.findBooksByAuthor = findBooksByAuthor;
  }

  public void createBook(final RoutingContext routingContext) {

    HttpServerResponse response = routingContext.response();

    try {
      final BookDTO bookDTO = Json.decodeValue(routingContext.getBodyAsString(), BookDTO.class);
      Book book = createBook.save(bookDTO.toBook());
      String responseBody = Json.encodePrettily(JsonObject.mapFrom(BookDTO.toDTO(book)));
      sendSuccess(responseBody, response, CREATED);
    } catch (DecodeException ex) {
      sendError(BAD_REQUEST, response);
    }
  }

  public void landingPage(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();

    response.putHeader("content-type", "text/html")
            .putHeader("charset", "UTF-8")
            .end("Simple bookstore made with &#x2764 and Vert.x");
  }

  public void findAllBooks(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();
    try {
      List<Book> books = findAllBooks.getBooks();
      sendSuccess(new JsonArray(books).encodePrettily(), response, OK);
    } catch (BooksNotFoundException ex) {
      sendError(NOT_FOUND, response);
    }
  }

  public void findBookByISBN(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();
    HttpServerRequest request = routingContext.request();
    String isbn = request.getParam("isbn");
    if (isbn == null) {
      sendError(BAD_REQUEST, response);
    } else {
      try {
        Book book = findBookByISBN.getBooks(isbn);
        String responseBody = Json.encodePrettily(JsonObject.mapFrom(BookDTO.toDTO(book)));
        sendSuccess(responseBody, response, OK);
      } catch (BooksNotFoundException ex) {
        sendError(NOT_FOUND, response);
      }
    }
  }

  public void findBooksByAuthor(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();
    HttpServerRequest request = routingContext.request();
    String author = request.getParam("author");
    if (author == null) {
      sendError(BAD_REQUEST, response);
    } else {
      try {
        List<Book> books = findBooksByAuthor.getBooks(author);
        sendSuccess(new JsonArray(books).encodePrettily(), response, OK);
      } catch (BooksNotFoundException ex) {
        sendError(NOT_FOUND, response);
      }
    }
  }

  private void sendError(int statusCode, HttpServerResponse response) {
    response
            .putHeader("content-type", "application/json; charset=utf-8")
            .setStatusCode(statusCode)
            .end();
  }

  private void sendSuccess(String responseBody, HttpServerResponse response, int statusCode) {
    response
            .putHeader("content-type", "application/json; charset=utf-8")
            .setStatusCode(statusCode)
            .end(responseBody);
  }
}
