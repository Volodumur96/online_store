package ua.com.shop.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ua.com.shop.shop.entity.Characteristic;

@Repository
public interface CharacteristicRepository extends JpaRepository<Characteristic, Long>, JpaSpecificationExecutor<Characteristic> {
}
