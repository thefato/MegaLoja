package org.lasalle.mega.loja.application.services.stores.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lasalle.mega.loja.application.repository.StoreRepository;
import org.lasalle.mega.loja.application.services.stores.StoreUpdateService;
import org.lasalle.mega.loja.domain.dto.StoreDTO;
import org.lasalle.mega.loja.domain.entity.StoreEntity;
import org.lasalle.mega.loja.domain.request.StoreUpdateRequest;
import org.lasalle.mega.loja.infrastructure.exceptions.StoreInvalidNameException;
import org.lasalle.mega.loja.infrastructure.exceptions.StoreNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreUpdateServiceImpl implements StoreUpdateService {

    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public StoreDTO updateStore(Integer storeId, StoreUpdateRequest updateRequest) {
        if (Objects.isNull(updateRequest.name()) || updateRequest.name().isBlank()) {
            log.warn("m=updateStore, nome inválido para loja id={} request={}", storeId, updateRequest);
            throw new StoreInvalidNameException();
        }

        StoreEntity storeEntity = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException(storeId));

        storeEntity.setName(updateRequest.name());
        storeEntity.setDescription(updateRequest.description());

        storeRepository.save(storeEntity);

        log.info("m=updateStore, loja atualizada id={} entity={}", storeId, storeEntity);

        return StoreDTO.from(storeEntity);
    }

    @Override
    @Transactional
    public void deleteStore(Integer storeId) {
        if (storeRepository.findById(storeId).isEmpty()) {
            log.warn("m=deleteStore, loja não encontrada {}", storeId);
            throw new StoreNotFoundException(storeId);
        }

        storeRepository.deleteById(storeId);
        log.info("m=deleteStore, loja removida id={}", storeId);
    }
}
