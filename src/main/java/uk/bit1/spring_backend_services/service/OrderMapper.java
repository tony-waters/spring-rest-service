package uk.bit1.spring_backend_services.service;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uk.bit1.spring_backend_services.dto.OrderDto;
import uk.bit1.spring_backend_services.entity.Order;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    Order toEntity(OrderDto dto);
    OrderDto toDto(Order entity);

    List<OrderDto> map(List<Order> orders);
}
