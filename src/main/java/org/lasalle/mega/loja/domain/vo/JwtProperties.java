package org.lasalle.mega.loja.domain.vo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.jwt")
public record JwtProperties(
        String issuer,
        String audience,
        Integer accessTokenMinutes,
        String secretBase
) {
}
