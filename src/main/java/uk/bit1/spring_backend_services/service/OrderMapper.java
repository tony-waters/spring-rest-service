package uk.bit1.spring_backend_services.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uk.bit1.spring_backend_services.dto.OrderDto;
import uk.bit1.spring_backend_services.entity.Order;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    // Entity → DTO
    @Mapping(source = "customer.id", target = "customerId")
    OrderDto toDto(Order entity);

    // DTO → Entity
    // customer is set in the service layer
    @Mapping(target = "customer", ignore = true)
    Order toEntity(OrderDto dto);

    List<OrderDto> map(List<Order> orders);
}
