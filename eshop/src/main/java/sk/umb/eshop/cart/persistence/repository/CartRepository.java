package sk.umb.eshop.cart.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sk.umb.eshop.cart.persistence.entity.CartEntity;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<CartEntity, Long> {
    @Override
    List<CartEntity> findAll();
}
