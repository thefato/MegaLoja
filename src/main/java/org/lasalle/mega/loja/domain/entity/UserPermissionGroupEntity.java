package org.lasalle.mega.loja.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(
        name = "db_user_groups",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_db_user_groups__user_group",
                        columnNames = { "id_user", "id_group" }
                )
        },
        indexes = {
                @Index(name = "idx_db_user_groups__id_user", columnList = "id_user"),
                @Index(name = "idx_db_user_groups__id_group", columnList = "id_group")
        }
)
@Data
public class UserPermissionGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_group", nullable = false, updatable = false)
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_user",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_groups__user")
    )
    private UserCredentialsEntity credentials;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_group",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_groups__group")
    )
    private PermissionGroupEntity group;
}

