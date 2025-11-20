package org.lasalle.mega.loja.application.stores.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lasalle.mega.loja.application.repository.StoreRepository;
import org.lasalle.mega.loja.application.stores.services.StoreCreateService;
import org.lasalle.mega.loja.domain.dto.StoreDTO;
import org.lasalle.mega.loja.domain.entity.StoreEntity;
import org.lasalle.mega.loja.domain.request.StoreCreateRequest;
import org.lasalle.mega.loja.infrastructure.exceptions.StoreInvalidNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreCreateServiceImpl implements StoreCreateService {

    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public StoreDTO createStore(StoreCreateRequest createRequest) {

        if (Objects.isNull(createRequest.name()) || createRequest.name().isBlank()) {
            log.warn("m=createStore, o nome da loja não pode ser vazio request={}", createRequest);
            throw new StoreInvalidNameException();
        }

        StoreEntity storeEntity = StoreEntity.builder()
                .name(createRequest.name())
                .description(createRequest.description())
                .build();

        storeRepository.save(storeEntity);

        log.info("m=createStore, nova loja salva {}", storeEntity);

        return StoreDTO.from(storeEntity);
    }
}
