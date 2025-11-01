package org.lasalle.mega.loja.application.repository;

import org.lasalle.mega.loja.domain.entity.UserCredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentialsEntity, Integer> {

    Optional<UserCredentialsEntity> findByEmail(String email);

}
