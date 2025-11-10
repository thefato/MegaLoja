package org.lasalle.mega.loja.application.repository;

import org.lasalle.mega.loja.domain.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Integer> {

}
