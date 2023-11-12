package sk.umb.eshop.order.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import sk.umb.eshop.customer.persistence.entity.CustomerEntity;
import sk.umb.eshop.customer.service.CustomerDetailDTO;
import sk.umb.eshop.customer.service.CustomerService;
import sk.umb.eshop.inventory.persistence.entity.InventoryEntity;
import sk.umb.eshop.inventory.service.InventoryDetailDTO;
import sk.umb.eshop.inventory.service.InventoryService;
import sk.umb.eshop.order.persistance.entity.OrderEntity;
import sk.umb.eshop.order.persistance.repository.OrderRepository;



@Service
public class OrderService {
    @Autowired
    private JavaMailSender mailSender;
    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;
    private final CustomerService customerService;

    public OrderService(OrderRepository orderRepository, InventoryService inventoryService, CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.inventoryService = inventoryService;
        this.customerService = customerService;
    }

    public void sendEmail(Long orderId){
        OrderEntity order = orderRepository.findById(orderId).get();
        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                String products = "";
                int totalPrice = 0;
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                message.setFrom("eshopski16@gmail.com");
                message.setTo(order.getCustomer_ID().getEmail());
                message.setSubject("Order confirmation: "+order.getOrderId());
                for (int i=0; i<order.getOrderedProducts().size();i++){
                    products+="<div style=\"display: flex;text-align: center:\">\n" +
                            "          <img style=\"justify-content: center;\" src="+order.getOrderedProducts().get(i).getProductId().getImage()+"\n" +
                            "               alt=\"ski boots\" style=\"width: 300px; height: 250px\" />\n" +
                            "          <div style=\"justify-content: center;\">\n" +
                            "            <b class=\"fa-solid \" >"+order.getOrderedProducts().get(i).getProductId().getName()+"</b><br>\n" +
                            "            <p class=\"text\" >"+order.getOrderedProducts().get(i).getProductId().getDescription()+"</p>\n" +
                            "            <b>Price:</b>"+order.getOrderedProducts().get(i).getProductId().getPrice()+"<b style=\"margin-left: 0.5%;color: black\">€</b>\n<br> <hr>" +
                            "          </div>\n" +
                            "  </div>";
                    totalPrice+= order.getOrderedProducts().get(i).getProductId().getPrice();
                }
                message.setText("<h1>Thank you for your order !</h1><br> <b>Here are your products:</b><br>"+products+"<br>Your total price is: "+totalPrice+" €<br>Your order is being prepared, we will let you know when its ready!", true);
            }
        };

        mailSender.send(messagePreparator);
        System.out.println("Mail Sent Successfully...");
    }

    public List<OrderDetailDTO> getAllOrders() {
        return mapToDto(orderRepository.findAll());
    }
    public OrderDetailDTO getOrderbyId(Long orderId) {
        validateOrderExists(orderId);
        return mapToDto(orderRepository.findById(orderId).get());
    }
    public OrderDetailDTO getOrderByCustomerId(Long customerId) {
        CustomerDetailDTO dto = customerService.getCustomerById(customerId);
        return mapToDto(orderRepository.findOrderByCustomerId(customerDtoToEntity(dto)));
    }
    public List<OrderDetailDTO> getOrdersByCustomerId(Long customerId) {
        CustomerDetailDTO dto = customerService.getCustomerById(customerId);
        return mapToDto(orderRepository.findOrdersByCustomerId(customerDtoToEntity(dto)));
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
            dto.setDate(oe.getDate());
            dtos.add(dto);
        }

        return dtos;
    }
    private OrderDetailDTO mapToDto(OrderEntity orderEntity) {
        OrderDetailDTO dto = new OrderDetailDTO();

        dto.setOrderId(orderEntity.getOrderId());
        dto.setCustomer_ID(customerEntityToDto(orderEntity.getCustomer_ID()));
        dto.setType(orderEntity.getType());
        dto.setOrdered(orderEntity.isOrdered());
        dto.setOrderedProducts(inventoryEntityToDto(orderEntity.getOrderedProducts()));
        dto.setDate(orderEntity.getDate());

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
