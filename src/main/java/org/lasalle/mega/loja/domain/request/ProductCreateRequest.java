package org.lasalle.mega.loja.domain.request;

import java.math.BigDecimal;

public record ProductCreateRequest(Integer categoryId, String name, long amount, BigDecimal cost) {

}
