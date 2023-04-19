package sk.umb.eshop.products.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import sk.umb.eshop.inventory.persistence.entity.InventoryEntity;
import sk.umb.eshop.inventory.persistence.repository.InventoryRepository;
import sk.umb.eshop.inventory.service.InventoryService;
import sk.umb.eshop.products.persistence.entity.ProductsEntity;
import sk.umb.eshop.products.persistence.repository.ProductsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final InventoryRepository inventoryRepository;
    private final InventoryService inventoryService;

    public ProductsService(ProductsRepository productsRepository, InventoryRepository inventoryRepository, InventoryService inventoryService) {
        this.productsRepository = productsRepository;
        this.inventoryRepository = inventoryRepository;
        this.inventoryService = inventoryService;
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
            dto.setSizes(pe.getSizes());
            dto.setType(pe.getType());
            dto.setImage(pe.getImage());

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
        dto.setSizes(productsEntity.getSizes());
        dto.setType(productsEntity.getType());
        dto.setImage(productsEntity.getImage());

        return dto;
    }
    private void validateProductExist(Long productId) {
        if (! productsRepository.existsById(productId)) {
            throw new IllegalArgumentException("ProductId: " + productId + " does not exists!");
        }
    }

    public Long createProduct(ProductsRequestDTO productsRequestDTO) {
        Long id = productsRepository.save(mapToEntity(productsRequestDTO)).getId();
        fillInventory(productsRequestDTO, id);
        return id;
    }
    public void fillInventory(ProductsRequestDTO productsRequestDTO, Long id){
        for (int i = 0;i< productsRequestDTO.getSizes().size(); i++){
            InventoryEntity ie = new InventoryEntity();

            ie.setProductId(productsRepository.findById(id).get());
            ie.setSize(productsRequestDTO.getSizes().get(i));
            ie.setAvailable(true);

            inventoryRepository.save(ie);
        }
    }

    private ProductsEntity mapToEntity(ProductsRequestDTO productsRequestDTO) {
        ProductsEntity pe = new ProductsEntity();

        pe.setName(productsRequestDTO.getName());
        pe.setDescription(productsRequestDTO.getDescription());
        pe.setPrice(productsRequestDTO.getPrice());
        pe.setSizes(productsRequestDTO.getSizes());
        pe.setType(productsRequestDTO.getType());
        pe.setImage(productsRequestDTO.getImage());

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
        if ( productsRequestDTO.getSizes() != null) {
            productsEntity.setSizes(productsRequestDTO.getSizes());
        }
        if (productsRequestDTO.getType() != null) {
            productsEntity.setType(productsRequestDTO.getType());
        }
        if (! Strings.isEmpty(productsRequestDTO.getImage())) {
            productsEntity.setImage(productsRequestDTO.getImage());
        }
        productsRepository.save(productsEntity);
        inventoryRepository.deleteAllByProdId(productsEntity);
        fillInventory(productsRequestDTO, productId);
    }

    public void deleteProduct(Long productId) {
        inventoryRepository.deleteAllByProdId(inventoryService.productDtoToEntity(getProductById(productId)));
        productsRepository.deleteById(productId);
    }
}
