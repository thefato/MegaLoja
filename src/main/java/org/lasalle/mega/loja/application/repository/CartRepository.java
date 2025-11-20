package org.lasalle.mega.loja.application.repository;

import org.lasalle.mega.loja.domain.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    Optional<CartEntity> findByCartKey(String cartKey);

}
