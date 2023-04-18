package sk.umb.eshop.order.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import sk.umb.eshop.customer.persistence.entity.CustomerEntity;
import sk.umb.eshop.customer.service.CustomerDetailDTO;
import sk.umb.eshop.inventory.persistence.entity.InventoryEntity;
import sk.umb.eshop.inventory.service.InventoryDetailDTO;
import sk.umb.eshop.inventory.service.InventoryService;
import sk.umb.eshop.order.persistance.entity.OrderEntity;
import sk.umb.eshop.order.persistance.repository.OrderRepository;
import sk.umb.eshop.products.persistence.entity.ProductsEntity;
import sk.umb.eshop.products.service.ProductsDetailDTO;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;

    public OrderService(OrderRepository orderRepository, InventoryService inventoryService) {
        this.orderRepository = orderRepository;
        this.inventoryService = inventoryService;
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
            dto.setCustomer_ID(customerEntityToDto(oe.getCustomer_ID()));
            dto.setType(oe.getType());
            dto.setOrdered(oe.isOrdered());
            dto.setOrderedProducts(inventoryEntityToDto(oe.getOrderedProducts()));
            dtos.add(dto);
        }

        return dtos;
    }
    private OrderDetailDTO mapToDto(OrderEntity productsEntity) {
        OrderDetailDTO dto = new OrderDetailDTO();

        dto.setOrderId(productsEntity.getOrderId());
        dto.setCustomer_ID(customerEntityToDto(productsEntity.getCustomer_ID()));
        dto.setType(productsEntity.getType());
        dto.setOrdered(productsEntity.isOrdered());
        dto.setOrderedProducts(inventoryEntityToDto(productsEntity.getOrderedProducts()));

        return dto;
    }

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
    private CustomerDetailDTO customerEntityToDto(CustomerEntity customerEntity){
        CustomerDetailDTO dto = new CustomerDetailDTO();

        dto.setId(customerEntity.getId());
        dto.setPassword(customerEntity.getPassword());
        dto.setRole(customerEntity.getRole());
        dto.setZipCode(customerEntity.getZipCode());
        dto.setCity(customerEntity.getCity());
        dto.setAddress(customerEntity.getAddress());
        dto.setState(customerEntity.getState());
        dto.setFirstName(customerEntity.getFirstName());
        dto.setLastName(customerEntity.getLastName());
        dto.setEmail(customerEntity.getEmail());
        dto.setPhone(customerEntity.getPhone());

        return dto;
    }
    private List<InventoryDetailDTO> inventoryEntityToDto(List<InventoryEntity> inventoryEntities){
        List<InventoryDetailDTO> dtos = new ArrayList<>();

        for (InventoryEntity ie : inventoryEntities) {
            InventoryDetailDTO dto = new InventoryDetailDTO();

            dto.setId(ie.getId());
            dto.setProductId(inventoryService.productEntityToDto(ie.getProductId()));
            dto.setSize(ie.getSize());
            dto.setAvailable(ie.isAvailable());

            dtos.add(dto);
        }

        return dtos;
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

    private void validateOrderExists(Long orderId) {
        if (! orderRepository.existsById(orderId)) {
            throw new IllegalArgumentException("OrderId: " + orderId + " does not exists!");
        }

    }

    public Long createOrder(OrderRequestDTO orderRequestDTO) {
        return orderRepository.save(mapToEntity(orderRequestDTO)).getOrderId();
    }
    private OrderEntity mapToEntity(OrderRequestDTO dto) {
        OrderEntity oe = new OrderEntity();

        oe.setOrderedProducts(inventoryDtoToEntity(dto.getOrderedProducts()));
        oe.setCustomer_ID(customerDtoToEntity(dto.getCustomer_ID()));
        oe.setType(dto.getType());
        oe.setOrdered(dto.isOrdered());

        return oe;
    }
    public void updateOrder(Long orderId) {
        validateOrderExists(orderId);

        OrderEntity orderEntity = orderRepository.findById(orderId).get();
        if (orderEntity.isOrdered()){
            orderEntity.setOrdered(false);
        } else {
            orderEntity.setOrdered(true);
        }
        orderRepository.save(orderEntity);

    }
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

}
