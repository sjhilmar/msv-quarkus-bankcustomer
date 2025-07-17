package com.emeal.nttdata.service.impl;

import static com.emeal.nttdata.util.CustomerUtil.getDateTimeNow;

import com.emeal.nttdata.domain.Customer;
import com.emeal.nttdata.mapper.CustomerMapper;
import com.emeal.nttdata.model.dto.CustomerDto;
import com.emeal.nttdata.repository.CustomerRepository;
import com.emeal.nttdata.service.CustomerService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.util.UUID;

@ApplicationScoped
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private static final CustomerMapper customerMapper = CustomerMapper.INSTANCE;

  public CustomerServiceImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public Uni<CustomerDto> createCustomer(CustomerDto customerDto) {
    return customerRepository.findByDocumentAndDocumentType(customerDto.getDocument(),
            customerDto.getDocumentType().toString())
        .onItem().ifNotNull().failWith(new RuntimeException("El cliente ya existe"))
        .onItem().ifNull().continueWith( () -> {
          Customer customer = customerMapper.customerDtoToCustomer(customerDto);
          customer.setClientId(UUID.randomUUID().toString());
          customer.setRegistrationDate(getDateTimeNow());
          return customer;
        }).call(customerRepository::persist)
        .map(customerMapper::customerToCustomerDto);
  }

  @Override
  public Multi<CustomerDto> getAllCustomers() {
    return customerRepository.findAll()
        .stream()
        .map(customerMapper::customerToCustomerDto);
  }

  @Override
  public Uni<CustomerDto> getCustomerById(String id) {
    return customerRepository.findById(new ObjectId(id))
        .map(customerMapper::customerToCustomerDto);
  }

  @Override
  public Uni<CustomerDto> getCustomerByDocument(String document) {
    return customerRepository.findByDocument(document)
        .map(customerMapper::customerToCustomerDto);
  }

  @Override
  public Uni<CustomerDto> updateCustomer(String id, CustomerDto customerDto) {
    return customerRepository.findById(new ObjectId(id))
        .onItem().ifNull().failWith(new RuntimeException("El cliente no existe"))
        .onItem().transform(existingCustomer -> Customer.builder()
              .firstName(customerDto.getFirstName())
              .lastName(customerDto.getLastName())
              .phoneNumber(customerDto.getPhoneNumber())
              .address(customerDto.getAddress())
              .email(customerDto.getEmail())
              .gender(customerDto.getGender())
              .state(customerDto.getState())
              .dateOfBirth(customerDto.getDateOfBirth())
              .lastUpdateDate(getDateTimeNow())
              .build())
        .call(customerRepository::persist)
        .map(customerMapper::customerToCustomerDto);
  }

  @Override
  public Uni<Void> deleteCustomer(String id) {
    return customerRepository.findById(new ObjectId(id))
        .onItem().ifNull().failWith(new RuntimeException("El cliente no existe"))
        .call(customerRepository::delete)
        .replaceWithVoid();
  }
}
