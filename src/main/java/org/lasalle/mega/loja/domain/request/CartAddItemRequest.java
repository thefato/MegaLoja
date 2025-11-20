package org.lasalle.mega.loja.domain.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartAddItemRequest(@NotNull Long productId,
                                 @Min(1) Long quantity) {
}
