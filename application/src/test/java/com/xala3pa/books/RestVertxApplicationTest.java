package com.xala3pa.books;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.ServerSocket;

@RunWith(VertxUnitRunner.class)
public class RestVertxApplicationTest {

  private Vertx vertx;
  private Integer port;

  @Before
  public void setUp(TestContext context) throws IOException {

    vertx = Vertx.vertx();
    //Get random port
    ServerSocket socket = new ServerSocket(0);
    port = socket.getLocalPort();
    socket.close();

    DeploymentOptions options = new DeploymentOptions().setConfig(new JsonObject().put("http.port", port));
    vertx.deployVerticle(RestVertxApplication.class.getName(), options, context.asyncAssertSuccess());
  }

  @After
  public void tearDown(TestContext context) {
    vertx.close(context.asyncAssertSuccess());
  }

  @Test
  public void should_return_text_from_landing_page_in_async_mode(TestContext context) {
    Async async = context.async();

    WebClientOptions httpClientOptions = new WebClientOptions();
    httpClientOptions.setDefaultHost("Localhost");
    httpClientOptions.setDefaultPort(port);

    WebClient webClient = WebClient.create(vertx, httpClientOptions);

    webClient.get("/")
            .send(asyncResult -> {
              if (asyncResult.succeeded()) {
                HttpResponse<Buffer> response = asyncResult.result();
                context.assertTrue(response.body().toString().contains("Simple bookstore made with &#x2764 and Vert.x"));
                async.complete();
              } else {
                async.resolve(Future.failedFuture(asyncResult.cause()));
              }
            });
  }
}