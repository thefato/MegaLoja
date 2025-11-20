package org.lasalle.mega.loja.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "db_store",
        indexes = {
                @Index(name = "idx_db_store__vc_name", columnList = "vc_name")
        }
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_store")
    private Long id;

    @Column(name = "vc_name", length = 256, nullable = false, unique = true)
    private String name;

    @Column(name = "vc_description", length = 2048)
    private String description;

}