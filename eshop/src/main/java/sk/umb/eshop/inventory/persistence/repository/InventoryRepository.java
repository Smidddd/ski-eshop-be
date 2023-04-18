package sk.umb.eshop.inventory.persistence.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sk.umb.eshop.inventory.persistence.entity.InventoryEntity;
import sk.umb.eshop.products.persistence.entity.ProductsEntity;

import java.util.List;

@Repository
public interface InventoryRepository extends CrudRepository<InventoryEntity, Long> {
    @Override
    List<InventoryEntity> findAll();
    @Transactional
    @Modifying
    @Query("DELETE FROM InventoryEntity WHERE productId=:product_id")
    void deleteAllByProdId(@Param("product_id") ProductsEntity product_id);

    @Query("SELECT t FROM InventoryEntity t WHERE t.productId=:product_id AND t.size=:size")
    InventoryEntity findItemBySize(@Param("product_id") ProductsEntity product_id, @Param("size") Long size);
}
