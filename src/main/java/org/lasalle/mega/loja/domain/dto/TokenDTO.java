package org.lasalle.mega.loja.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TokenDTO {

    private final String token;

    private final List<String> scopes;

}
