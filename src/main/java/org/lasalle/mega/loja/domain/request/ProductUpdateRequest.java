package org.lasalle.mega.loja.domain.request;

import java.math.BigDecimal;

public record ProductUpdateRequest(String name, String description, BigDecimal price,
                                   Integer category, Integer amount) {

}
