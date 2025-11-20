package org.lasalle.mega.loja.application.repository;

import org.lasalle.mega.loja.domain.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("""
           SELECT p FROM ProductEntity p
           WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
             AND (:categories IS NULL OR p.category.id IN (:categories))
           """)
    Page<ProductEntity> findByFilters(@Param("categories") List<Integer> categories,
                                      @Param("name") String name,
                                      Pageable pageable);

}
