package org.lasalle.mega.loja.application.repository;

import org.lasalle.mega.loja.domain.entity.UserCredentialsEntity;
import org.lasalle.mega.loja.domain.entity.UserPermissionGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPermissionGroupRepository extends JpaRepository<UserPermissionGroupEntity, Integer> {

    Optional<UserPermissionGroupEntity> findUserPermissionGroupEntityByCredentials(UserCredentialsEntity userCredentials);

}
