package org.lasalle.mega.loja.application.repository;

import org.lasalle.mega.loja.domain.entity.PermissionScopeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionScopeRepository extends JpaRepository<PermissionScopeEntity, Integer> {
}
