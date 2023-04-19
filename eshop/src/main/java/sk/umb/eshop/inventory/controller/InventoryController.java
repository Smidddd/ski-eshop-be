package sk.umb.eshop.inventory.controller;

import org.springframework.web.bind.annotation.*;
import sk.umb.eshop.customer.service.CustomerDetailDTO;
import sk.umb.eshop.customer.service.CustomerRequestDTO;
import sk.umb.eshop.customer.service.CustomerService;
import sk.umb.eshop.inventory.service.InventoryDetailDTO;
import sk.umb.eshop.inventory.service.InventoryRequestDTO;
import sk.umb.eshop.inventory.service.InventoryService;

import java.util.List;
@RestController
public class InventoryController {
    private InventoryService inventoryService;
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/api/inventory")
    public List<InventoryDetailDTO> searchInventory() {
        System.out.println("Search inventory called.");

        return inventoryService.getFullInventory();
    }

    @GetMapping("/api/inventory/{inventoryId}")
    public InventoryDetailDTO getItem(@PathVariable Long inventoryId) {
        System.out.println("Get item called.");
        return inventoryService.getItemById(inventoryId);
    }
    @GetMapping("/api/inventory/byId")
    public List<InventoryDetailDTO> getItemsByIds(@RequestParam(name = "ids") Long[] ids) {
        System.out.println("Get items by ids called.");
        return inventoryService.getItemsByIds(ids);
    }
    @GetMapping("/api/inventory/{productId}/{size}")
    public InventoryDetailDTO getItem(@PathVariable Long productId, @PathVariable Long size) {
        System.out.println("Get item by prodId and size called.");
        return inventoryService.getItemBySize(productId, size);
    }

    @PostMapping("/api/inventory")
    public Long createItem(@RequestBody InventoryRequestDTO inventoryRequestDTO) {
        System.out.println("Create inventory called:");
        return inventoryService.createItem(inventoryRequestDTO);
    }

    @PutMapping("/api/inventory/{inventoryId}")
    public void updateItem(@PathVariable Long inventoryId, @RequestBody InventoryRequestDTO inventoryRequestDTO) {
        System.out.println("Update inventory called: ID = " + inventoryId);
        inventoryService.updateItem(inventoryId, inventoryRequestDTO);
    }

    @DeleteMapping("/api/inventory/{inventoryId}")
    public void deleteItem(@PathVariable Long inventoryId) {
        System.out.println("Delete inventory called: ID = " + inventoryId);
        inventoryService.deleteItem(inventoryId);
    }
}
