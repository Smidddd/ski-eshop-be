package sk.umb.eshop.products.service;

import java.util.List;
import java.util.Set;

public class ProductsRequestDTO {
    private String name;
    private String description;
    private Long price;
    private List<Long> sizes;
    private Type type;
    private String image;

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

    public List<Long> getSizes() {
        return sizes;
    }

    public void setSizes(List<Long> sizes) {
        this.sizes = sizes;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
