package org.lasalle.mega.loja.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "db_users_credentials",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_db_users__vc_email", columnNames = "vc_email")
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false, updatable = false)
    private Long id;

    @Column(name = "vc_email", length = 256, nullable = false)
    private String email;

    @Column(name = "vc_password_hash", length = 256, nullable = false)
    private String passwordHash;

    @OneToOne(mappedBy = "credentials", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserPermissionGroupEntity membership;
}
