package sk.umb.eshop.inventory.service;

import org.springframework.stereotype.Service;
import sk.umb.eshop.inventory.persistence.entity.InventoryEntity;
import sk.umb.eshop.inventory.persistence.repository.InventoryRepository;
import sk.umb.eshop.products.persistence.entity.ProductsEntity;
import sk.umb.eshop.products.persistence.repository.ProductsRepository;
import sk.umb.eshop.products.service.ProductsDetailDTO;

import java.util.ArrayList;
import java.util.List;
@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    private final ProductsRepository productsRepository;

    public InventoryService(InventoryRepository inventoryRepository, ProductsRepository productsRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productsRepository = productsRepository;
    }

    public List<InventoryDetailDTO> getFullInventory() {
        return mapToDto(inventoryRepository.findAll());
    }


    public InventoryDetailDTO getItemById(Long inventoryId) {
        validateItemExists(inventoryId);
        return mapToDto(inventoryRepository.findById(inventoryId).get());
    }
    public InventoryDetailDTO getItemBySize(Long productId, Long size){
        return mapToDto(inventoryRepository.findItemBySize(productsRepository.findById(productId).get(), size));
    }

    public Long createItem(InventoryRequestDTO inventoryRequestDTO) {
        return inventoryRepository.save(mapToEntity(inventoryRequestDTO)).getId();
    }

    private List<InventoryDetailDTO> mapToDto(List<InventoryEntity> inventoryEntities) {
        List<InventoryDetailDTO> dtos = new ArrayList<>();

        for (InventoryEntity ie : inventoryEntities) {
            InventoryDetailDTO dto = new InventoryDetailDTO();

            dto.setId(ie.getId());
            dto.setProductId(productEntityToDto(ie.getProductId()));
            dto.setSize(ie.getSize());
            dto.setAvailable(ie.isAvailable());

            dtos.add(dto);
        }

        return dtos;
    }
    private InventoryDetailDTO mapToDto(InventoryEntity inventoryEntity) {
        InventoryDetailDTO dto = new InventoryDetailDTO();

        dto.setId(inventoryEntity.getId());
        dto.setProductId(productEntityToDto(inventoryEntity.getProductId()));
        dto.setSize(inventoryEntity.getSize());
        dto.setAvailable(inventoryEntity.isAvailable());


        return dto;
    }

    private InventoryEntity mapToEntity(InventoryRequestDTO dto) {
        InventoryEntity ie = new InventoryEntity();

        ie.setProductId(productDtoToEntity(dto.getProductId()));
        ie.setSize(dto.getSize());
        ie.setAvailable(dto.isAvailable());


        return ie;
    }

    public ProductsEntity productDtoToEntity(ProductsDetailDTO productsDetailDTO){
        ProductsEntity pe = new ProductsEntity();

        pe.setId(productsDetailDTO.getId());
        pe.setImage(productsDetailDTO.getImage());
        pe.setDescription(productsDetailDTO.getDescription());
        pe.setName(productsDetailDTO.getName());
        pe.setType(productsDetailDTO.getType());
        pe.setPrice(productsDetailDTO.getPrice());
        pe.setSizes(productsDetailDTO.getSizes());


        return pe;
    }
    public ProductsDetailDTO productEntityToDto(ProductsEntity productsEntity){
        ProductsDetailDTO dto = new ProductsDetailDTO();

        dto.setId(productsEntity.getId());
        dto.setName(productsEntity.getName());
        dto.setDescription(productsEntity.getDescription());
        dto.setImage(productsEntity.getImage());
        dto.setPrice(productsEntity.getPrice());
        dto.setType(productsEntity.getType());
        dto.setSizes(productsEntity.getSizes());

        return dto;
    }

    public void updateItem(Long inventoryId, InventoryRequestDTO inventoryRequestDTO) {
        validateItemExists(inventoryId);

        InventoryEntity inventoryEntity = inventoryRepository.findById(inventoryId).get();

        if (inventoryRequestDTO.getProductId() != null) {
            inventoryEntity.setProductId(productDtoToEntity(inventoryRequestDTO.getProductId()));
        }

        if (inventoryRequestDTO.getSize() != null) {
            inventoryEntity.setSize(inventoryRequestDTO.getSize());
        }

        inventoryEntity.setAvailable(inventoryRequestDTO.isAvailable());


        inventoryRepository.save(inventoryEntity);
    }

    private void validateItemExists(Long inventoryId) {
        if (! inventoryRepository.existsById(inventoryId)) {
            throw new IllegalArgumentException("CustomerId: " + inventoryId + " does not exists!");
        }
    }

    public void deleteItem(Long inventoryId) {
        inventoryRepository.deleteById(inventoryId);
    }
}
