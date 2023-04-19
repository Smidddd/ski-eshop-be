package sk.umb.eshop.order.persistance.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sk.umb.eshop.customer.persistence.entity.CustomerEntity;
import sk.umb.eshop.inventory.persistence.entity.InventoryEntity;
import sk.umb.eshop.order.persistance.entity.OrderEntity;
import sk.umb.eshop.products.persistence.entity.ProductsEntity;

import java.util.List;

@Repository

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    @Override
    List<OrderEntity> findAll();
    @Query("SELECT t FROM OrderEntity t WHERE t.customer_ID=:customerId")
    OrderEntity findOrderByCustomerId(@Param("customerId") CustomerEntity customerId);
}
