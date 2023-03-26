package sk.umb.eshop.products.controller;


import org.springframework.web.bind.annotation.*;
import sk.umb.eshop.products.service.ProductsDetailDTO;
import sk.umb.eshop.products.service.ProductsRequestDTO;
import sk.umb.eshop.products.service.ProductsService;

import java.util.List;

@RestController
public class ProductsController {


    private ProductsService productsService;
    @GetMapping("/api/products")
    public List<ProductsDetailDTO> searchProducts() {
        System.out.println("Search products called.");

        return productsService.getAllProducts();
    }
    @GetMapping("/api/products/{productId}")
    public ProductsDetailDTO getProduct(@PathVariable Long productId) {
        System.out.println("Get product called.");
        return productsService.getProductById(productId);
    }
    @PostMapping("/api/products")
    public Long createProduct(@RequestBody ProductsRequestDTO productsRequestDTO) {
        System.out.println("Create product called:");
        return productsService.createProduct(productsRequestDTO);
    }
    @PutMapping("/api/products/{productId}")
    public void updateProduct(@PathVariable Long productId, @RequestBody ProductsRequestDTO productsRequestDTO) {
        System.out.println("Update product called: ID = " + productId);
        productsService.updateProduct(productId,productsRequestDTO);
    }
    @DeleteMapping("/api/products/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        System.out.println("Delete product called: ID = " + productId);
        productsService.deleteProduct(productId);
    }
}
