package org.lasalle.mega.loja.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(
        name = "db_group_scopes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_db_group_scopes__group_scope",
                        columnNames = { "id_group", "id_scope" }
                )
        },
        indexes = {
                @Index(name = "idx_db_group_scopes__id_group", columnList = "id_group"),
                @Index(name = "idx_db_group_scopes__id_scope", columnList = "id_scope")
        }
)
@Data
public class PermissionGroupScopeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_group_scope", nullable = false, updatable = false)
    private Long id;

    @Column(name = "id_group")
    private Long group;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_scope",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_group_scopes__scope")
    )
    private PermissionScopeEntity scope;
}

