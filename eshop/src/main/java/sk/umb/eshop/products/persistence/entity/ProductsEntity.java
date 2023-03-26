package sk.umb.eshop.products.persistence.entity;

import org.springframework.data.annotation.Id;
import sk.umb.eshop.products.service.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
public class ProductsEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private Long price;
    private Long size;
    private Type type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
