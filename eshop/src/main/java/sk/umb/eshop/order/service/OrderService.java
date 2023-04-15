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
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderDetailDTO> getAllOrders() {
        return mapToDto(orderRepository.findAll());
    }
    public OrderDetailDTO getOrderbyId(Long orderId) {
        validateOrderExists(orderId);
        return mapToDto(orderRepository.findById(orderId).get());
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
    private OrderDetailDTO mapToDto(OrderEntity productsEntity) {
        OrderDetailDTO dto = new OrderDetailDTO();

        dto.setOrderId(productsEntity.getOrderId());
        dto.setCustomer_ID(productsEntity.getCustomer_ID());
        dto.setType(productsEntity.getType());
        dto.setOrdered(productsEntity.isOrdered());

        return dto;
    }
    private void validateOrderExists(Long orderId) {
        if (! orderRepository.existsById(orderId)) {
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
        return orderRepository.save(mapToEntity(orderRequestDTO)).getOrderId();
    }
    private OrderEntity mapToEntity(OrderRequestDTO dto) {
        OrderEntity oe = new OrderEntity();

        oe.setType(dto.getType());

        return oe;
    }
    public void updateOrder(Long orderId, OrderRequestDTO orderRequestDTO) {
        validateOrderExists(orderId);

        OrderEntity orderEntity = orderRepository.findById(orderId).get();

            orderEntity.setOrdered(orderRequestDTO.isOrdered());
        orderRepository.save(orderEntity);

    }
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

}
