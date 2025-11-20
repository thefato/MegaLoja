package org.lasalle.mega.loja.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
        name = "db_cart",
        indexes = {
                @Index(name = "idx_db_cart__id_user", columnList = "id_user"),
                @Index(name = "idx_db_cart__dt_created", columnList = "dt_created"),
                @Index(name = "idx_db_cart__dt_updated", columnList = "dt_updated")
        }
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cart")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_user",
            foreignKey = @ForeignKey(name = "fk_cart__user_details")
    )
    private UserDetailsEntity user;

    @Column(name = "vc_cart_key", length = 256, nullable = false, unique = true)
    private String cartKey;

    @Column(name = "dt_created", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "dt_updated", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CartItemEntity> items;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
