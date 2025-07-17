package com.emeal.nttdata.domain;

import com.emeal.nttdata.model.enums.CustomerType;
import com.emeal.nttdata.model.enums.DocumentType;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MongoEntity(collection = "QuarkusCustomer")
public class Customer {

  @BsonId
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
