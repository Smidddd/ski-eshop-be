package sk.umb.eshop.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.umb.eshop.order.service.OrderDetailDTO;
import sk.umb.eshop.order.service.OrderRequestDTO;
import sk.umb.eshop.order.service.OrderService;

import java.util.List;

@RestController
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
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
    @GetMapping("/api/order/customer/{customerId}")
    public OrderDetailDTO getOrderByCustomerId(@PathVariable Long customerId){
        System.out.println("Get order by customer id: "+customerId+" called");

        return orderService.getOrderByCustomerId(customerId);
    }
    @PostMapping("/api/order")
    public Long createOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        System.out.println("Create order called");
        return orderService.createOrder(orderRequestDTO);
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
