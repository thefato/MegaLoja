package org.lasalle.mega.loja.application.repository;

import org.lasalle.mega.loja.domain.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<StoreEntity, Integer> {
}
