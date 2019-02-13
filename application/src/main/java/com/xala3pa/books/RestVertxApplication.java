package com.xala3pa.books;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xala3pa.books.controller.VertxBookController;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class RestVertxApplication extends AbstractVerticle {

  private static final int HTTP_PORT = 8080;
  private final VertxBookController bookController = new VertxConfig().getVertxBookController();

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    Json.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    // Create a router object.
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    router.get("/books").handler(bookController::findAllBooks);
//    router.get("/books/:isbn").handler(bookController::findBookByISBN);
//    router.get("/books/:author").handler(bookController::findBooksByAuthor);
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
