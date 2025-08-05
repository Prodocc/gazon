package com.eshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface ProductRepository extends JpaRepository<Object, Long> {
    // TODO change Object to Product

    Page<Object> findByCategory_Id(Long categoryId, Pageable pageable);
}
