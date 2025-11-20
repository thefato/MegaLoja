package org.lasalle.mega.loja.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(
        name = "db_product",
        indexes = {
                @Index(name = "idx_db_product__id_category", columnList = "id_category"),
                @Index(name = "idx_db_product__vc_name", columnList = "vc_name")
        }
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "id_category",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_product__category")
    )
    private ProductCategoryEntity category;

    @Column(name = "vc_name", length = 256, nullable = false)
    private String name;

    @Column(name = "vc_description", length = 2048)
    private String description;

    @Column(name = "num_amount", nullable = false)
    private Long amount;

    @Column(name = "num_cost", precision = 12, scale = 2, nullable = false)
    private BigDecimal cost;

}