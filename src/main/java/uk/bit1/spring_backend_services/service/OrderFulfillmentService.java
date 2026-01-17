package uk.bit1.spring_backend_services.service;

import org.springframework.stereotype.Service;
import uk.bit1.spring_backend_services.entity.CustomerOrder;

import java.util.List;

@Service
public class OrderFulfillmentService {

    public List<CustomerOrder> getUnfulfilledOrders() {
        return null;
    }
}
