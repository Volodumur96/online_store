package ua.com.shop.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.shop.shop.entity.OrderForProduct;

@Repository
public interface OrderForProductRepository extends JpaRepository<OrderForProduct, Long> {


}
