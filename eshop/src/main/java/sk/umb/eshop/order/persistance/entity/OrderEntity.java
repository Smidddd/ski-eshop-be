package sk.umb.eshop.order.persistance.entity;

import jakarta.persistence.*;
import sk.umb.eshop.customer.persistence.entity.CustomerEntity;
import sk.umb.eshop.customer.service.CustomerDetailDTO;
import sk.umb.eshop.inventory.persistence.entity.InventoryEntity;
import sk.umb.eshop.order.service.Type;

import java.util.List;

@Entity

public class OrderEntity {
    @Id
    @GeneratedValue
    private Long orderId;
    @OneToOne
    @JoinColumn()
    private CustomerEntity customer_ID;
    private Type type;
    private boolean ordered;
    @ManyToMany
    @JoinTable(name="order_products",
            joinColumns=@JoinColumn(name="order_id"),
            inverseJoinColumns=@JoinColumn(name="item_id"))
    private List<InventoryEntity> orderedProducts;


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public CustomerEntity getCustomer_ID() {
        return customer_ID;
    }

    public void setCustomer_ID(CustomerEntity customer_ID) {
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

    public List<InventoryEntity> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<InventoryEntity> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }
}
