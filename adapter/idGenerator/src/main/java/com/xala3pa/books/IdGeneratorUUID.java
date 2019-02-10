package com.xala3pa.books;

import com.xala3pa.books.port.IdGenerator;

import java.util.UUID;

public class IdGeneratorUUID implements IdGenerator {
  @Override
  public String generate() {
    return UUID.randomUUID().toString();
  }
}
