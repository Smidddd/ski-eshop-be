package sk.umb.eshop.order.service;

import sk.umb.eshop.customer.persistence.entity.CustomerEntity;
import sk.umb.eshop.customer.service.CustomerDetailDTO;
import sk.umb.eshop.inventory.service.InventoryDetailDTO;

import java.util.List;

public class OrderRequestDTO {

    private CustomerDetailDTO customer_ID;
    private Type type;
    private boolean ordered;

    private List<InventoryDetailDTO> orderedProducts;

    public CustomerDetailDTO getCustomer_ID() {
        return customer_ID;
    }

    public void setCustomer_ID(CustomerDetailDTO customer_ID) {
        this.customer_ID = customer_ID;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isOrdered() {
        return ordered;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    public List<InventoryDetailDTO> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<InventoryDetailDTO> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }
}
