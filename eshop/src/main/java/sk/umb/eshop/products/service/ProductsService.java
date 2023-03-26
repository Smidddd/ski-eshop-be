package sk.umb.eshop.products.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import sk.umb.eshop.products.persistence.entity.ProductsEntity;
import sk.umb.eshop.products.persistence.repository.ProductsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsService {

    private final ProductsRepository productsRepository;

    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List<ProductsDetailDTO> getAllProducts() {
        return mapToDto(productsRepository.findAll());
    }




    private List<ProductsDetailDTO> mapToDto(List<ProductsEntity> productEntities) {
        List<ProductsDetailDTO> dtos = new ArrayList<>();
        for (ProductsEntity pe : productEntities) {
            ProductsDetailDTO dto = new ProductsDetailDTO();

            dto.setId(pe.getId());
            dto.setName(pe.getName());
            dto.setDescription(pe.getDescription());
            dto.setPrice(pe.getPrice());
            dto.setSize(pe.getSize());
            dto.setType(pe.getType());

            dtos.add(dto);
        }

        return dtos;
    }

    public ProductsDetailDTO getProductById(Long productId) {
        validateProductExist(productId);
        return mapToDto(productsRepository.findById(productId).get());
    }
    private ProductsDetailDTO mapToDto(ProductsEntity productsEntity) {
        ProductsDetailDTO dto = new ProductsDetailDTO();

        dto.setId(productsEntity.getId());
        dto.setName(productsEntity.getName());
        dto.setDescription(productsEntity.getDescription());
        dto.setPrice(productsEntity.getPrice());
        dto.setSize(productsEntity.getSize());
        dto.setType(productsEntity.getType());

        return dto;
    }
    private void validateProductExist(Long productId) {
        if (! productsRepository.existsById(productId)) {
            throw new IllegalArgumentException("ProductId: " + productId + " does not exists!");
        }
    }

    public Long createProduct(ProductsRequestDTO productsRequestDTO) {
        return productsRepository.save(mapToEntity(productsRequestDTO)).getId();
    }

    private ProductsEntity mapToEntity(ProductsRequestDTO productsRequestDTO) {
        ProductsEntity pe = new ProductsEntity();

        pe.setName(productsRequestDTO.getName());
        pe.setDescription(productsRequestDTO.getDescription());
        pe.setPrice(productsRequestDTO.getPrice());
        pe.setSize(productsRequestDTO.getSize());
        pe.setType(productsRequestDTO.getType());

        return pe;
    }

    public void updateProduct(Long productId, ProductsRequestDTO productsRequestDTO) {
        validateProductExist(productId);

        ProductsEntity productsEntity = productsRepository.findById(productId).get();

        if (! Strings.isEmpty(productsRequestDTO.getName())) {
            productsEntity.setName(productsRequestDTO.getName());
        }

        if (! Strings.isEmpty(productsRequestDTO.getDescription())) {
            productsEntity.setDescription(productsRequestDTO.getDescription());
        }

        if ( productsRequestDTO.getPrice() != null) {
            productsEntity.setPrice(productsRequestDTO.getPrice());
        }
        if ( productsRequestDTO.getSize() != null) {
            productsEntity.setSize(productsRequestDTO.getSize());
        }
        if (productsRequestDTO.getType() != null) {
            productsEntity.setType(productsRequestDTO.getType());
        }
        productsRepository.save(productsEntity);
    }

    public void deleteProduct(Long productId) {
        productsRepository.deleteById(productId);
    }
}
