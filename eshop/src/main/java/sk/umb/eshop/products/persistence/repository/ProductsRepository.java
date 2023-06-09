package sk.umb.eshop.products.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sk.umb.eshop.products.persistence.entity.ProductsEntity;


import java.util.List;
@Repository
public interface ProductsRepository extends CrudRepository<ProductsEntity, Long> {
    @Override
    List<ProductsEntity> findAll();
}
