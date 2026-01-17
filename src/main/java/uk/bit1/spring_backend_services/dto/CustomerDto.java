package uk.bit1.spring_backend_services.dto;

import java.util.List;

public record CustomerDto(
        Long id,
        String lastName,
        String firstName,
        List<OrderDto> orders
) {}
