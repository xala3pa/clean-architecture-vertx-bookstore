package com.xala3pa.books.controller;

import com.xala3pa.books.boundary.CreateBook;
import com.xala3pa.books.boundary.FindAllBooks;
import com.xala3pa.books.entity.Book;
import com.xala3pa.books.model.BookDTO;
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
  private final CreateBook createBook;
  private final FindAllBooks findAllBooks;

  public VertxBookController(CreateBook createBook, FindAllBooks findAllBooks) {

    this.createBook = createBook;
    this.findAllBooks = findAllBooks;
  }

  public void createBook(final RoutingContext routingContext) {

    HttpServerResponse response = routingContext.response();

    try {
      final BookDTO bookDTO = Json.decodeValue(routingContext.getBodyAsString(), BookDTO.class);
      Book book = createBook.save(bookDTO.toBook());
      String responseBody = Json.encodePrettily(JsonObject.mapFrom(bookDTO.toDTO(book)));
      sendSuccess(responseBody, response, CREATED);
    } catch (DecodeException ex) {
      sendError(BAD_REQUEST, response);
    }
  }

  public void findAllBooks(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();
    try {
      List<Book> books = findAllBooks.getBooks();
      sendSuccess(new JsonArray(books).encodePrettily(),response, OK);
    } catch (Exception e) {
      sendError(404, response);
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
