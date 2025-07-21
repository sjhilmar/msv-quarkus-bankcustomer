package com.emeal.nttdata.repository;

import com.emeal.nttdata.domain.Customer;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerRepository implements ReactivePanacheMongoRepository<Customer> {

  public Uni<Customer> findByDocument(String document) {
    return find("document", document).firstResult();
  }

  public Uni<Customer> findByDocumentAndDocumentType(String document, String documentType) {
    return find("document = ?1 and documentType = ?2", document, documentType)
        .firstResult();
  }

  public Uni<Customer> findByClientId(String clientId) {
    return find("_id", clientId).firstResult();
  }

}
