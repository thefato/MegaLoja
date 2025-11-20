package org.lasalle.mega.loja.application.services.security.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lasalle.mega.loja.application.repository.PermissionGroupRepository;
import org.lasalle.mega.loja.application.repository.UserCredentialsRepository;
import org.lasalle.mega.loja.application.repository.UserDetailsRepository;
import org.lasalle.mega.loja.application.repository.UserPermissionGroupRepository;
import org.lasalle.mega.loja.application.services.security.AuthLoginService;
import org.lasalle.mega.loja.application.services.security.AuthRegisterService;
import org.lasalle.mega.loja.domain.entity.PermissionGroupEntity;
import org.lasalle.mega.loja.domain.entity.UserCredentialsEntity;
import org.lasalle.mega.loja.domain.entity.UserDetailsEntity;
import org.lasalle.mega.loja.domain.entity.UserPermissionGroupEntity;
import org.lasalle.mega.loja.domain.request.RegisterAuthRequest;
import org.lasalle.mega.loja.domain.response.UserAuthResponse;
import org.lasalle.mega.loja.infrastructure.exceptions.UserAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthRegisterServiceImpl implements AuthRegisterService {

    private final AuthLoginService authLoginService;

    private final PasswordEncoder passwordEncoder;

    private final UserCredentialsRepository credentialsRepository;

    private final PermissionGroupRepository groupRepository;

    private final UserPermissionGroupRepository permissionGroupRepository;

    private final UserDetailsRepository userDetailsRepository;

    @Override
    @Transactional
    public UserAuthResponse executeUserRegister(RegisterAuthRequest authRequest) {
        if (credentialsRepository.findByEmail(authRequest.email()).isPresent()) {
            log.warn("m=executeUserRegister, o usuario {} já está registrado", authRequest.email());
            throw new UserAlreadyExistsException(authRequest.email());
        }

        if (userDetailsRepository.findByDocument(authRequest.email()).isPresent()) {
            log.warn("m=executeUserRegister, o usuario {} já está registrado", authRequest.document());
            throw new UserAlreadyExistsException(authRequest.document());
        }

        PermissionGroupEntity groupEntity = groupRepository.findByName("default");
        UserCredentialsEntity credentials = UserCredentialsEntity.builder()
                .email(authRequest.email())
                .passwordHash(passwordEncoder.encode(authRequest.password()))
                .build();

        UserCredentialsEntity savedEntity = credentialsRepository.save(credentials);
        UserPermissionGroupEntity permissionGroup = UserPermissionGroupEntity.builder()
                .credentials(savedEntity)
                .group(groupEntity)
                .build();

        permissionGroupRepository.save(permissionGroup);

        UserDetailsEntity userDetails = UserDetailsEntity.builder()
                .name(authRequest.name())
                .document(authRequest.document())
                .userCredentials(credentials)
                .build();

        userDetailsRepository.save(userDetails);

        return authLoginService.executeUserLogin(authRequest.toLoginRequest());
    }
}
