package com.emeal.nttdata.service.impl;

import static com.emeal.nttdata.util.Constants.DESC_CUSTOMER_NOT_EXISTS;
import static com.emeal.nttdata.util.CustomerUtil.getCurrentDateTimeFormatted;
import static com.emeal.nttdata.util.exception.dto.ExceptionCatalog.ERBS001;
import static com.emeal.nttdata.util.exception.dto.ExceptionCatalog.ERBS002;

import com.emeal.nttdata.domain.Customer;
import com.emeal.nttdata.mapper.CustomerMapper;
import com.emeal.nttdata.model.dto.CustomerDto;
import com.emeal.nttdata.repository.CustomerRepository;
import com.emeal.nttdata.service.CustomerService;
import com.emeal.nttdata.util.exception.BusinessException;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
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
        .onItem().ifNotNull().failWith(new BusinessException(ERBS001.getCode(),
        ERBS001.getDescription(), ERBS001.getHttpStatus()))
        .onItem().ifNull().continueWith( () -> {
          Customer customer = customerMapper.customerDtoToCustomer(customerDto);
          customer.setClientId(UUID.randomUUID().toString());
          customer.setRegistrationDate(getCurrentDateTimeFormatted());
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
  public Uni<CustomerDto> getCustomerByDocument(String document) {
    return customerRepository.findByDocument(document)
        .map(customerMapper::customerToCustomerDto);
  }

  @Override
  public Uni<CustomerDto> updateCustomer(String document, String documentType ,
                                         CustomerDto customerDto) {
    return customerRepository.findByDocumentAndDocumentType(document, documentType)
        .onItem().ifNull().failWith(()-> {
          log.error(DESC_CUSTOMER_NOT_EXISTS + " with document: {} and document type: {}",
              document, documentType);
          throw new BusinessException(ERBS002.getCode(),
              ERBS002.getDescription() + ", document: " + document
                  + ", documentType: " + documentType, ERBS002.getHttpStatus());
        })
        .onItem().transform(existingCustomer -> {
          existingCustomer.setFirstName(customerDto.getFirstName());
          existingCustomer.setLastName(customerDto.getLastName());
          existingCustomer.setPhoneNumber(customerDto.getPhoneNumber());
          existingCustomer.setAddress(customerDto.getAddress());
          existingCustomer.setEmail(customerDto.getEmail());
          existingCustomer.setGender(customerDto.getGender());
          existingCustomer.setState(customerDto.getState());
          existingCustomer.setDateOfBirth(customerDto.getDateOfBirth());
          existingCustomer.setLastUpdateDate(getCurrentDateTimeFormatted());
          return  existingCustomer;
        })
        .call(customerRepository::update)
        .map(customerMapper::customerToCustomerDto);
  }

  @Override
  public Uni<Void> deleteCustomer(String document, String documentType) {
    return customerRepository.findByDocument(document)
        .onItem().ifNull().failWith(new RuntimeException(DESC_CUSTOMER_NOT_EXISTS))
        .call(customerRepository::delete)
        .replaceWithVoid();
  }

  /**
   * @param clientId
   * @return
   */
  @Override
  public Uni<CustomerDto> getCustomerByClientId(String clientId) {
   return customerRepository.findByClientId(clientId)
       .onItem().ifNull().failWith(new RuntimeException(DESC_CUSTOMER_NOT_EXISTS))
       .map(customerMapper::customerToCustomerDto);
  }
}
