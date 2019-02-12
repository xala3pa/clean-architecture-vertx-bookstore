package com.xala3pa.books.controller;

import com.xala3pa.books.boundary.CreateBook;
import com.xala3pa.books.model.BookDTO;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

public class VertxBookController {

  private static final int CREATED = 201;
  private final CreateBook createBook;

  public VertxBookController(CreateBook createBook) {
    this.createBook = createBook;
  }

  public void createBook(final RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();

    final BookDTO bookDTO = Json.decodeValue(routingContext.getBodyAsString(), BookDTO.class);
    createBook.save(bookDTO.toBook());

    response
            .setStatusCode(CREATED)
            .putHeader("content-type", "application/json; charset=utf-8")
            .end(Json.encodePrettily(bookDTO));
  }
}
