package sk.umb.eshop.order.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import sk.umb.eshop.order.persistance.entity.OrderEntity;
import sk.umb.eshop.order.persistance.repository.OrderRepository;

@Service
public class OrderService {
    private final AtomicLong lastIndex = new AtomicLong(0);
    private final Map<Long, OrderDetailDTO> orderDatabase = new HashMap();
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderDetailDTO> getAllOrders() {
        return mapToDto((List<OrderEntity>) orderRepository.findAll());
    }
    private List<OrderDetailDTO> mapToDto(List<OrderEntity> orderEntities) {
        List<OrderDetailDTO> dtos = new ArrayList<>();
        for (OrderEntity oe : orderEntities) {
            OrderDetailDTO dto = new OrderDetailDTO();

            dto.setOrderId(oe.getOrderId());
            dto.setCustomer_ID(oe.getCustomer_ID());
            dto.setType(oe.getType());
            dto.setOrdered(oe.isOrdered());
            dtos.add(dto);
        }

        return dtos;
    }



    public List<OrderDetailDTO> searchOrderById(Long orderId) {
        return orderDatabase.values().stream()
                .filter(dto -> orderId.equals(dto.getOrderId()))
                .toList();
    }
    public OrderDetailDTO retrieveOrder(Long orderId) {
        validateOrderExists(orderId);

        return orderDatabase.get(orderId);
    }
    private void validateOrderExists(Long orderId) {
        if (! orderDatabase.containsKey(orderId)) {
            throw new IllegalArgumentException("OrderId: " + orderId + " does not exists!");
        }

    }
    private static OrderDetailDTO mapToOrderDetailDTO(Long index, OrderRequestDTO orderRequestDTO) {
        OrderDetailDTO dto = new OrderDetailDTO();

        dto.setOrderId(index);
        dto.setCustomer_ID(orderRequestDTO.getCustomer_ID());
        dto.setType(orderRequestDTO.getType());
        dto.setOrdered(orderRequestDTO.isOrdered());
        return dto;
    }

    public Long createOrder(OrderRequestDTO orderRequestDTO) {
        OrderDetailDTO orderDetailDTO = mapToOrderDetailDTO(lastIndex.getAndIncrement(),
                orderRequestDTO);

        orderDatabase.put(orderDetailDTO.getOrderId(), orderDetailDTO);

        return orderDetailDTO.getOrderId();
    }
    public void updateOrder(Long orderId, OrderRequestDTO orderRequestDTO) {
        validateOrderExists(orderId);

        OrderDetailDTO orderDetailDTO = orderDatabase.get(orderId);

            orderDetailDTO.setOrdered(orderRequestDTO.isOrdered());

    }
    public void deleteOrder(Long orderId) {
        orderDatabase.remove(orderId);
    }

}
