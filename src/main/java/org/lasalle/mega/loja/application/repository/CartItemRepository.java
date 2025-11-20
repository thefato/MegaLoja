package org.lasalle.mega.loja.application.repository;

import org.lasalle.mega.loja.domain.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {

    Optional<CartItemEntity> findByCart_IdAndProduct_Id(Long cartId, Long productId);

}
