package com.xala3pa.books.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

  private Long isbn;
  private String title;
  private String description;
  private BookCategory bookCategory;
  private BookStatus bookStatus;
  private String author;
  private Date dateAdded;
  private Date consumed;

}
