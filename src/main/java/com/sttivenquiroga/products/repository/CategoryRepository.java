package com.sttivenquiroga.products.repository;

import com.sttivenquiroga.products.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    @Query("select c from Category c where c.name=:name")
    Optional<Category> findByName(String name);
}
