package com.umb.consumer.order.service;

import com.rabbitmq.client.Channel;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sk.umb.eshop.config.RabbitMQConfig;
import sk.umb.eshop.customer.persistence.entity.CustomerEntity;
import sk.umb.eshop.customer.service.CustomerDetailDTO;
import sk.umb.eshop.customer.service.CustomerService;
import sk.umb.eshop.inventory.persistence.entity.InventoryEntity;
import sk.umb.eshop.inventory.service.InventoryDetailDTO;
import sk.umb.eshop.inventory.service.InventoryService;
import sk.umb.eshop.order.persistance.entity.OrderEntity;
import sk.umb.eshop.order.persistance.repository.OrderRepository;
import sk.umb.eshop.order.service.OrderRequestDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderConsumer {
    private OrderRepository orderRepository;
    private InventoryService inventoryService;
    @PostConstruct
    public void orderConsumer(OrderRepository orderRepository, InventoryService inventoryService, CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.inventoryService = inventoryService;
    }

    public OrderConsumer(){}

    private CustomerEntity customerDtoToEntity(CustomerDetailDTO customerDetailDTO){
        CustomerEntity ce = new CustomerEntity();

        ce.setId(customerDetailDTO.getId());
        ce.setPassword(customerDetailDTO.getPassword());
        ce.setRole(customerDetailDTO.getRole());
        ce.setZipCode(customerDetailDTO.getZipCode());
        ce.setCity(customerDetailDTO.getCity());
        ce.setAddress(customerDetailDTO.getAddress());
        ce.setState(customerDetailDTO.getState());
        ce.setFirstName(customerDetailDTO.getFirstName());
        ce.setLastName(customerDetailDTO.getLastName());
        ce.setEmail(customerDetailDTO.getEmail());
        ce.setPhone(customerDetailDTO.getPhone());


        return ce;
    }
    private List<InventoryEntity> inventoryDtoToEntity(List<InventoryDetailDTO> inventoryDetailDTOS){
        List<InventoryEntity> ies = new ArrayList<>();

        for (InventoryDetailDTO dto : inventoryDetailDTOS) {
            InventoryEntity inventoryEntity = new InventoryEntity();

            inventoryEntity.setId(dto.getId());
            inventoryEntity.setProductId(inventoryService.productDtoToEntity(dto.getProductId()));
            inventoryEntity.setSize(dto.getSize());
            inventoryEntity.setAvailable(dto.isAvailable());

            ies.add(inventoryEntity);
        }

        return ies;
    }


    @RabbitListener(queues = RabbitMQConfig.QK_EXAMPLE_QUEUE)
    public void createOrder(OrderRequestDTO orderRequestDTO, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        orderRepository.save(mapToEntity(orderRequestDTO));

        try{
            channel.basicAck(tag, false);
        }catch(Exception e){
            channel.basicNack(tag, false, true);
        }
    }
    private OrderEntity mapToEntity(OrderRequestDTO dto) {
        OrderEntity oe = new OrderEntity();

        oe.setOrderedProducts(inventoryDtoToEntity(dto.getOrderedProducts()));
        oe.setCustomer_ID(customerDtoToEntity(dto.getCustomer_ID()));
        oe.setType(dto.getType());
        oe.setOrdered(dto.isOrdered());

        return oe;
    }


}

