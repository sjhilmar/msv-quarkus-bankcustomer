package com.emeal.nttdata.mapper;

import com.emeal.nttdata.domain.Customer;
import com.emeal.nttdata.model.dto.CustomerDto;
import jakarta.enterprise.context.ApplicationScoped;
import org.mapstruct.Mapper;

@Mapper
@ApplicationScoped
public interface CustomerMapper {
  CustomerMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(CustomerMapper.class);

  CustomerDto customerToCustomerDto(Customer customer);

  Customer customerDtoToCustomer(CustomerDto customerDto);

}
