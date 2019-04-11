package ua.com.shop.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.shop.shop.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


}
