package com.emeal.nttdata.service;

import com.emeal.nttdata.model.dto.CustomerDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface CustomerService {

  Uni<CustomerDto> createCustomer(CustomerDto customerDto);

  Multi<CustomerDto> getAllCustomers();

  Uni<CustomerDto> getCustomerByDocument(String document);

  Uni<CustomerDto> updateCustomer(String document, String documentType, CustomerDto customerDto);

  Uni<Void> deleteCustomer(String document, String documentType);

  Uni<CustomerDto> getCustomerByClientId(String clientId);

}
