package org.lasalle.mega.loja.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.lasalle.mega.loja.domain.entity.ProductEntity;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    private String name;

    private long category;

    private Long amount;

    private BigDecimal cost;

    public static ProductDTO from(ProductEntity entity) {
        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .amount(entity.getAmount())
                .cost(entity.getCost())
                .category(entity.getCategory().getId())
                .build();
    }

}
