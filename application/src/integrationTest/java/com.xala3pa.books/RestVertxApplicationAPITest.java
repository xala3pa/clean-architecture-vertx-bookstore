package com.xala3pa.books;

import com.xala3pa.books.entity.BookCategory;
import com.xala3pa.books.entity.BookStatus;
import com.xala3pa.books.model.BookDTO;
import io.restassured.RestAssured;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(JUnitParamsRunner.class)
public class RestVertxApplicationAPITest {

  private static final int HTTP_STATUS_CODE_OK = 200;
  private static final int HTTP_STATUS_CODE_NOT_FOUND = 404;
  private static final int HTTP_STATUS_CODE_CREATED = 201;

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
  public void should_return_all_books_in_the_bookstore() {
    given().
            header("Accept-Encoding", "application/json")
            .when()
            .get("/books")
            .then()
            .statusCode(HTTP_STATUS_CODE_OK)
            .body("size()", greaterThan(0));
  }

  @Test
  @Parameters
  public void should_return_specific_book_by_isbn_or_404_if_does_not_exist(String isbn, int status) {
    given().
            header("Accept-Encoding", "application/json")
            .when()
            .get("books/{isbn}", isbn).
            then()
            .statusCode(status);
  }

  private Object[] parametersForShould_return_specific_book_by_isbn_or_404_if_does_not_exist() {
    return new Object[]{
            new Object[]{"978-1-60774-605-8", HTTP_STATUS_CODE_OK},
            new Object[]{"000-0-00000-000-0", HTTP_STATUS_CODE_NOT_FOUND}
    };
  }

  @Test
  @Parameters
  public void should_return_specific_book_by_author_or_404_if_does_not_exist(String author, int status) {
    given().
            header("Accept-Encoding", "application/json")
            .when()
            .get("/books/author/{author}", author)
            .then()
            .statusCode(status);
  }

  private Object[] parametersForShould_return_specific_book_by_author_or_404_if_does_not_exist() {
    return new Object[]{
            new Object[]{"Tony Gemignani", HTTP_STATUS_CODE_OK},
            new Object[]{"Alvaro Salazar", HTTP_STATUS_CODE_NOT_FOUND}
    };
  }

  @Test
  public void should_save_the_book_in_the_bookstore() {
    BookDTO newBook = BookDTO.builder()
            .isbn("979-1-123-52767-9")
            .title("New Book")
            .author("New author")
            .bookStatus(BookStatus.READ)
            .bookCategory(BookCategory.TECHNICAL)
            .description("New Description")
            .build();

    given().
            contentType("application/json").
            body(newBook).
            when().
            post("/books").
            then().
            statusCode(HTTP_STATUS_CODE_CREATED);
  }
}