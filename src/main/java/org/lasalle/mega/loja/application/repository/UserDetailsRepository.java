package org.lasalle.mega.loja.application.repository;

import org.lasalle.mega.loja.domain.entity.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Integer> {

    Optional<UserDetailsEntity> findByDocument(String document);

    Optional<UserDetailsEntity> findByUserCredentials_Email(String userCredentialsEmail);

}
