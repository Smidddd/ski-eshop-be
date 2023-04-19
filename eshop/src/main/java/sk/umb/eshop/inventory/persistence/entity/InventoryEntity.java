package sk.umb.eshop.inventory.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import sk.umb.eshop.products.persistence.entity.ProductsEntity;

@Entity
public class InventoryEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prod_id")
    private ProductsEntity productId;
    private Long size;
    private boolean available = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductsEntity getProductId() {
        return productId;
    }

    public void setProductId(ProductsEntity productId) {
        this.productId = productId;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
