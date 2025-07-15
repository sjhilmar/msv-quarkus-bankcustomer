package com.emeal.nttdata.model.enums;

public enum CustomerType {
    PERSONAL("Personal"),
    BUSINESS("Business"),
    PERSONAL_VIP("Personal VIP"),
    BUSINESS_PYME("Business PYME");

  private final String type;

  CustomerType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
