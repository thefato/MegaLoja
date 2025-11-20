package org.lasalle.mega.loja.application.services.security.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lasalle.mega.loja.application.repository.UserCredentialsRepository;
import org.lasalle.mega.loja.application.repository.UserPermissionGroupRepository;
import org.lasalle.mega.loja.domain.dto.AuthUserDetailsDTO;
import org.lasalle.mega.loja.domain.entity.PermissionGroupScopeEntity;
import org.lasalle.mega.loja.domain.entity.PermissionScopeEntity;
import org.lasalle.mega.loja.domain.entity.UserCredentialsEntity;
import org.lasalle.mega.loja.domain.entity.UserPermissionGroupEntity;
import org.lasalle.mega.loja.infrastructure.exceptions.UserCredentialsNotFoundException;
import org.lasalle.mega.loja.infrastructure.exceptions.UserGroupNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserCredentialsRepository credentialsRepository;

    private final UserPermissionGroupRepository userPermissionGroupRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<UserCredentialsEntity> optionalCredentials = credentialsRepository.findByEmail(email);

        if (optionalCredentials.isEmpty()) {
            log.warn("m=loadUserByUsername o usuario não foi encontrado email={}", email);
            throw new UserCredentialsNotFoundException(email);
        }

        UserCredentialsEntity credentials = optionalCredentials.get();
        Optional<UserPermissionGroupEntity> userCredentials = userPermissionGroupRepository.findUserPermissionGroupEntityByCredentials(credentials);

        if (userCredentials.isEmpty()) {
            log.warn("m=loadUserByUsername o gropo de permissões não foi encontrado email={}", email);
            throw new UserGroupNotFoundException(email);
        }

        UserPermissionGroupEntity groupEntity = userCredentials.get();
        Set<PermissionGroupScopeEntity> scopes = groupEntity.getGroup().getGroupScopes();

        try {
            List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(scopes.stream()
                    .map(PermissionGroupScopeEntity::getScope)
                    .map(PermissionScopeEntity::getName)
                    .collect(Collectors.toSet()));

            return AuthUserDetailsDTO.builder()
                    .authorities(authorityList)
                    .username(credentials.getEmail())
                    .password(credentials.getPasswordHash())
                    .build();
        } catch (Exception e) {
            log.error("Erro", e);
            throw e;
        }

    }

}
