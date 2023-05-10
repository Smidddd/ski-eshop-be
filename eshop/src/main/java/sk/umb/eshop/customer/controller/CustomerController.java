package sk.umb.eshop.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.umb.eshop.customer.service.CustomerDetailDTO;
import sk.umb.eshop.customer.service.CustomerRequestDTO;
import sk.umb.eshop.customer.service.CustomerService;

import java.util.List;

@RestController
public class CustomerController {
    private CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/api/customers")
    public List<CustomerDetailDTO> searchCustomer() {
        System.out.println("Search customer called.");

        return customerService.getAllCustomers();
    }

    @GetMapping("/api/customers/{customerId}")
    public CustomerDetailDTO getCustomer(@PathVariable Long customerId) {
        System.out.println("Get customer called.");
        return customerService.getCustomerById(customerId);
    }
    @GetMapping("/api/customers/email/{customerEmail}")
    public CustomerDetailDTO getCustomerByEmail(@PathVariable String customerEmail) {
        System.out.println("Get customer by email called.");
        return customerService.getCustomerByEmail(customerEmail);
    }
    @GetMapping("/api/customers/verify/{id}/{password}")
    public boolean getCustomerByEmail(@PathVariable Long id, @PathVariable String password) {
        System.out.println("Verify password called.");
        return customerService.verifyHash(password, id);
    }

    @PostMapping("/api/customers")
    public Long createCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
        System.out.println("Create customer called:");
        return customerService.createCustomer(customerRequestDTO);
    }

    @PutMapping("/api/customers/{customerId}")
    public void updateCustomer(@PathVariable Long customerId, @RequestBody CustomerRequestDTO customerRequestDTO) {
        System.out.println("Update customer called: ID = " + customerId);
        customerService.updateCustomer(customerId, customerRequestDTO);
    }

    @DeleteMapping("/api/customers/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) {
        System.out.println("Delete customer called: ID = " + customerId);
        customerService.deleteCustomer(customerId);
    }
}
