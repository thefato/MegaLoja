package org.lasalle.mega.loja.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.lasalle.mega.loja.domain.entity.StoreEntity;

@Data
@Builder
@AllArgsConstructor
public class StoreDTO {

    private Long id;

    private String name;

    private String description;

    public static StoreDTO from(StoreEntity storeEntity) {
        return StoreDTO.builder()
                .id(storeEntity.getId())
                .name(storeEntity.getName())
                .description(storeEntity.getDescription())
                .build();
    }

}
