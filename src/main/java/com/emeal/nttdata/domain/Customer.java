package com.emeal.nttdata.domain;

import com.emeal.nttdata.model.enums.CustomerType;
import com.emeal.nttdata.model.enums.DocumentType;
import com.fasterxml.jackson.annotation.JsonTypeId;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MongoEntity(collection = "customer")
public class Customer {

  @JsonTypeId
  private String clientId;
  private String document;
  private DocumentType documentType;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String address;
  private char gender;
  private String state;
  private String dateOfBirth;
  private CustomerType customerType;
  private String registrationDate;
  private String lastUpdateDate;

}
