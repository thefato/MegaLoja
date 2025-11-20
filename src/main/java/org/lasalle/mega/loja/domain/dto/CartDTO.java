package org.lasalle.mega.loja.domain.dto;

import lombok.Builder;
import org.lasalle.mega.loja.domain.entity.CartEntity;
import org.lasalle.mega.loja.domain.entity.UserCredentialsEntity;
import org.lasalle.mega.loja.domain.entity.UserDetailsEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Builder
public record CartDTO(
        Long id,
        String cartKey,
        String userEmail,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<CartItemDTO> items
) {

    public static CartDTO from(CartEntity entity) {
        String email = Optional.ofNullable(entity.getUser())
                .map(UserDetailsEntity::getUserCredentials)
                .map(UserCredentialsEntity::getEmail)
                .orElse(null);

        List<CartItemDTO> itemDTOs = Optional.ofNullable(entity.getItems())
                .orElse(List.of())
                .stream()
                .map(CartItemDTO::from)
                .toList();

        return CartDTO.builder()
                .id(entity.getId())
                .cartKey(entity.getCartKey())
                .userEmail(email)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .items(itemDTOs)
                .build();
    }
}
