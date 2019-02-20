package com.xala3pa.books;

import io.restassured.RestAssured;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class RestVertxApplicationAPITest {

  public static final int HTTP_STATUS_CODE_OK = 200;

  @BeforeClass
  public static void setUp() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = Integer.getInteger("http.port", 8080);
  }

  @AfterClass
  public static void tearDown() {
    RestAssured.reset();
  }

  @Test
  public void shouldReturnUserForGetWithCorrectId() {

    given().
            header("Accept-Encoding", "application/json")
            .when()
            .get("/books")
            .then()
            .assertThat()
            .statusCode(HTTP_STATUS_CODE_OK);
  }
}