package sk.umb.eshop.order.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sk.umb.eshop.order.persistance.entity.OrderEntity;

@Repository

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {

}
