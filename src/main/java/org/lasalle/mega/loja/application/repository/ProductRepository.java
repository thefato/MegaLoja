package org.lasalle.mega.loja.application.repository;

import org.lasalle.mega.loja.domain.entity.ProductCategoryEntity;
import org.lasalle.mega.loja.domain.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    Page<ProductEntity> findByCategoryIn(Collection<ProductCategoryEntity> categories, Pageable pageable);

}
