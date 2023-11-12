package sk.umb.eshop.order.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.umb.eshop.config.RabbitMQConfig;
import sk.umb.eshop.order.service.OrderDetailDTO;
import sk.umb.eshop.order.service.OrderRequestDTO;
import sk.umb.eshop.order.service.OrderService;

import java.util.List;

@RestController
public class OrderController {

    private final RabbitTemplate rabbitTemplate;

    private OrderService orderService;

    public OrderController(OrderService orderService, RabbitTemplate rabbitTemplate){
        this.orderService = orderService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/api/order")
    public List <OrderDetailDTO> listOrders(@RequestParam(required = false) Long orderId){
        System.out.println("List orders called");

        return orderService.getAllOrders();
    }
    @GetMapping("/api/order/{orderId}")
    public OrderDetailDTO retrieveOrder(@PathVariable Long orderId){
        System.out.println("Details of order called + ");

        return orderService.getOrderbyId(orderId);
    }
    @PostMapping("/api/order/{orderId}")
    public Long sendConfirmationEmail(@PathVariable Long orderId){
        System.out.println("Send email of order called: "+orderId);

        orderService.sendEmail(orderId);
        return orderId;
    }
    @GetMapping("/api/order/customer/{customerId}")
    public OrderDetailDTO getOrderByCustomerId(@PathVariable Long customerId){
        System.out.println("Get order by customer id: "+customerId+" called");

        return orderService.getOrderByCustomerId(customerId);
    }
    @GetMapping("/api/order/all/{customerId}")
    public List<OrderDetailDTO> getOrdersByCustomerId(@PathVariable Long customerId){
        System.out.println("Get orders by customer id: "+customerId+" called");

        return orderService.getOrdersByCustomerId(customerId);
    }
    @PostMapping("/api/order")
    public long createOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        System.out.println("Create order called");
        rabbitTemplate.convertAndSend(RabbitMQConfig.QK_EXAMPLE_QUEUE, orderRequestDTO);
        return 1;
    }
    @PutMapping("/api/order/{orderId}")
    public void updateOrder(@PathVariable Long orderId){
        System.out.println("Update order called ID: "+ orderId);
        orderService.updateOrder(orderId);
    }
    @DeleteMapping("/api/order/{orderId}")
    public void deleteOrder(@PathVariable Long orderId){
        System.out.println("Delete order called ID: "+ orderId);
        orderService.deleteOrder(orderId);
    }
}
