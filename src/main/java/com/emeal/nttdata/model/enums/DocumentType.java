package com.emeal.nttdata.model.enums;

public enum DocumentType {
    DNI("DNI"),
    PASSPORT("Passport"),
    OTHER("Other");

  private final String description;

  DocumentType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
