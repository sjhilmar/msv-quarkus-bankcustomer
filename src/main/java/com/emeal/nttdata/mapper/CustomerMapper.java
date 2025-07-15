package com.emeal.nttdata.mapper;

import com.emeal.nttdata.domain.Customer;
import com.emeal.nttdata.model.dto.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
  CustomerMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(CustomerMapper.class);

  CustomerDto customerToCustomerDto(Customer customer);

  Customer customerDtoToCustomer(CustomerDto customerDto);

}
