package org.lasalle.mega.loja.domain.request;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class ProductUpdateRequest {

    @Nonnull
    private final Integer id;

    private final String name;

    private final BigDecimal price;

    private final Integer category;

    private final Integer amount;

}
