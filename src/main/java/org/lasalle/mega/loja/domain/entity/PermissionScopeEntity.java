package org.lasalle.mega.loja.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "db_scopes",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_db_scopes__vc_name", columnNames = "vc_name")
        }
)
@Data
public class PermissionScopeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_scope", nullable = false, updatable = false)
    private Long id;

    @Column(name = "vc_name", length = 256, nullable = false)
    private String name;

}

