package org.lasalle.mega.loja.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "db_user_details",
        indexes = {
                @Index(name = "idx_db_user_details__vc_name", columnList = "vc_name")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_user_details__id_user_credentials", columnNames = "id_user_credentials")
        }
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "id_user_credentials",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_user_details__credentials")
    )
    private UserCredentialsEntity userCredentials;

    @Column(name = "vc_name", length = 256, nullable = false)
    private String name;

    @Column(name = "vc_document", length = 256)
    private String document;

}
