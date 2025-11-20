package org.lasalle.mega.loja.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "db_cart_item",
        indexes = {
                @Index(name = "idx_db_cart_item__id_cart", columnList = "id_cart"),
                @Index(name = "idx_db_cart_item__id_product", columnList = "id_product")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_cart_item__cart_product",
                        columnNames = {"id_cart", "id_product"}
                )
        }
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cart_item")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "id_cart",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_cart_item__cart")
    )
    private CartEntity cart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "id_product",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_cart_item__product")
    )
    private ProductEntity product;

    @Column(name = "num_quantity", nullable = false)
    private Long quantity;

    @Column(name = "dt_created", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "dt_updated", nullable = false)
    private LocalDateTime updatedAt;

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