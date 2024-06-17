package com.sttivenquiroga.products.repository;

import com.sttivenquiroga.products.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    Optional<Product> getProductById(Integer id);

    @Query("select p from Product p where p.category.name=:categoryName")
    Optional<List<Product>> getProductsByCategory_Name(String categoryName);

    @Query("select p from Product p")
    Optional<List<Product>> getAllProducts();
}
