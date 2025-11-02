package org.lasalle.mega.loja.application.repository;

import org.lasalle.mega.loja.domain.entity.PermissionGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionGroupRepository extends JpaRepository<PermissionGroupEntity, Integer> {

    PermissionGroupEntity findByName(String name);

}
