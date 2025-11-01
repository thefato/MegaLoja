package org.lasalle.mega.loja.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "db_groups",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_db_groups__vc_name", columnNames = "vc_name")
        }
)
@Data
public class PermissionGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_group", nullable = false, updatable = false)
    private Long id;

    @Column(name = "vc_name", length = 256, nullable = false)
    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PermissionGroupScopeEntity> groupScopes = new HashSet<>();
}