package org.lasalle.mega.loja.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@Data
@AllArgsConstructor
public class AuthUserDetailsDTO implements UserDetails {

    private final Collection<? extends GrantedAuthority> authorities;

    private final String password;

    private final String username;

}
