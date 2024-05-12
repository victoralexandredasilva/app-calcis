package com.victoralexandre.appcalcis.repositories;

import com.victoralexandre.appcalcis.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findFirstByName(String name);

    List<Product> findByActiveTrue();

    List<Product> findByActiveFalse();

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    List<Product> findProductByName(@Param("name")String name);
}
