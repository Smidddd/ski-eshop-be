package sk.umb.eshop.customer.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sk.umb.eshop.customer.persistence.entity.CustomerEntity;

import java.util.List;
@Repository
public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {
    @Override
    List<CustomerEntity> findAll();
}
