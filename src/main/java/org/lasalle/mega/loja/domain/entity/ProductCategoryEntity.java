package org.lasalle.mega.loja.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(
        name = "db_product_category",
        indexes = {
                @Index(name = "idx_db_product_category__vc_name", columnList = "vc_name")
        }
)
@Data
public class ProductCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private Long id;

    @Column(name = "vc_name", length = 256, nullable = false, unique = true)
    private String name;

}
