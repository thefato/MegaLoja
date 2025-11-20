package org.lasalle.mega.loja.domain.request;

import java.math.BigDecimal;

public record ProductCreateRequest(Integer categoryId, String name, String description, long amount, BigDecimal cost) {

}
