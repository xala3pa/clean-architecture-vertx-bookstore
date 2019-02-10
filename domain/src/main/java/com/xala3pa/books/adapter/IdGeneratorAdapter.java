package com.xala3pa.books.adapter;

import com.xala3pa.books.port.IdGenerator;

import java.util.UUID;

public class IdGeneratorAdapter implements IdGenerator {
  @Override
  public String generate() {
    return UUID.randomUUID().toString();
  }
}
