package com.ecommerce.repository;

import com.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    Product findById(long id);

    @Query("select s from Product s where s.name like %:keyword% or s.category like %:keyword%")
    List<Product> findByKeyword(@Param("keyword") String keyword);

}