package org.lasalle.mega.loja.domain.dto;

import lombok.Builder;
import org.lasalle.mega.loja.domain.entity.CartItemEntity;

@Builder
public record CartItemDTO(
        Long id,
        Long productId,
        String productName,
        Long quantity
) {

    public static CartItemDTO from(CartItemEntity entity) {
        return CartItemDTO.builder()
                .id(entity.getId())
                .productId(entity.getProduct().getId())
                .productName(entity.getProduct().getName())
                .quantity(entity.getQuantity())
                .build();
    }
}
