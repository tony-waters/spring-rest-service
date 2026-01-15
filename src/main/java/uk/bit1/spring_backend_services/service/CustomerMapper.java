package uk.bit1.spring_backend_services.service;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uk.bit1.spring_backend_services.dto.CustomerDto;
import uk.bit1.spring_backend_services.entity.Customer;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    Customer toEntity(CustomerDto dto);
    CustomerDto toDto(Customer entity);

    List<CustomerDto> map(List<Customer> customers);
//    List<CustomerDto> map(List<CustomerDto> customers);
}
