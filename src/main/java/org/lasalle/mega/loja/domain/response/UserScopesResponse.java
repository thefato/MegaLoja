package org.lasalle.mega.loja.domain.response;

import lombok.Builder;

import java.util.List;

@Builder
public record UserScopesResponse(List<String> scopes) {

}
