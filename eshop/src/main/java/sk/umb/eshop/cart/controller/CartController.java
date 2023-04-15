package sk.umb.eshop.cart.controller;

import org.springframework.web.bind.annotation.*;
import sk.umb.eshop.cart.service.CartDetailDTO;
import sk.umb.eshop.cart.service.CartRequestDTO;
import sk.umb.eshop.cart.service.CartService;

import java.util.List;
@RestController
public class CartController {
    private CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/api/cart")
    public List<CartDetailDTO> searchCart() {
        System.out.println("Search customer called.");

        return cartService.getAllCarts();
    }

    @GetMapping("/api/cart/{cartId}")
    public CartDetailDTO getCart(@PathVariable Long cartId) {
        System.out.println("Get customer called.");
        return cartService.getCartById(cartId);
    }

    @PostMapping("/api/cart")
    public Long createCustomer(@RequestBody CartRequestDTO cartRequestDTO) {
        System.out.println("Create customer called:");
        return cartService.createCart(cartRequestDTO);
    }

    @PutMapping("/api/cart/{cartId}")
    public void updateCart(@PathVariable Long cartId, @RequestBody CartRequestDTO cartRequestDTO) {
        System.out.println("Update customer called: ID = " + cartId);
        cartService.updateCart(cartId, cartRequestDTO);
    }

    @DeleteMapping("/api/cart/{cartId}")
    public void deleteCart(@PathVariable Long cartId) {
        System.out.println("Delete customer called: ID = " + cartId);
        cartService.deleteCart(cartId);
    }
}
