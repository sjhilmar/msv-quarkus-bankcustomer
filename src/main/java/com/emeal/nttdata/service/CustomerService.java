package com.emeal.nttdata.service;

import com.emeal.nttdata.domain.Customer;
import com.emeal.nttdata.model.dto.CustomerDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.bson.types.ObjectId;

public interface CustomerService {

  Uni<CustomerDto> createCustomer(CustomerDto customerDto);

  Multi<CustomerDto> getAllCustomers();

  Uni<CustomerDto> getCustomerById(String id);

  Uni<CustomerDto> getCustomerByDocument(String document);

  Uni<CustomerDto> updateCustomer(String id, CustomerDto customerDto);

  Uni<Void> deleteCustomer(String id);

}
