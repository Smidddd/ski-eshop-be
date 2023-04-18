package sk.umb.eshop.inventory.service;

import sk.umb.eshop.products.persistence.entity.ProductsEntity;
import sk.umb.eshop.products.service.ProductsDetailDTO;

public class InventoryDetailDTO {
    private Long id;
    private ProductsDetailDTO productId;
    private Long size;
    private boolean available = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductsDetailDTO getProductId() {
        return productId;
    }

    public void setProductId(ProductsDetailDTO productId) {
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
