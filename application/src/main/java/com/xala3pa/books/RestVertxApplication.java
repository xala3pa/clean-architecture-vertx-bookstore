package com.xala3pa.books;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xala3pa.books.controller.VertxBookController;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class RestVertxApplication extends AbstractVerticle {

  private static final int HTTP_PORT = 8080;
  private final VertxConfig vertxConfig =  new VertxConfig();
  private final VertxBookController bookController = vertxConfig.getVertxBookController();

  @Override
  public void init(Vertx vertx, Context context) {
    super.init(vertx, context);
    vertxConfig.initializeData();
  }

  @Override
  public void start(Future<Void> startFuture) {
    Json.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    // Create a router object.
    Router router = Router.router(vertx);

    router.route().handler(BodyHandler.create());
    router.route("/").handler(bookController::landingPage);
    router.get("/books").handler(bookController::findAllBooks);
    router.get("/books/:isbn").handler(bookController::findBookByISBN);
    router.get("/books/author/:author").handler(bookController::findBooksByAuthor);
    router.post("/books").handler(bookController::createBook);

    // Create the HTTP server and pass the "accept" method to the request handler.
    vertx
            .createHttpServer()
            .requestHandler(router)
            .listen(config().getInteger("http.port", HTTP_PORT),
                    result -> {
                      if (result.succeeded()) {
                        startFuture.complete();
                      } else {
                        startFuture.fail(result.cause());
                      }
                    }
            );
  }
}
