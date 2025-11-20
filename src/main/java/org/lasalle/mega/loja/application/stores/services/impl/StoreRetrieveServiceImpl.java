package org.lasalle.mega.loja.application.stores.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lasalle.mega.loja.application.repository.StoreRepository;
import org.lasalle.mega.loja.application.stores.services.StoreRetrieveService;
import org.lasalle.mega.loja.domain.dto.StoreDTO;
import org.lasalle.mega.loja.domain.entity.StoreEntity;
import org.lasalle.mega.loja.infrastructure.exceptions.StoreNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreRetrieveServiceImpl implements StoreRetrieveService {

    private final StoreRepository storeRepository;

    @Override
    public Page<StoreDTO> getAllStores(Pageable pageable) {
        Page<StoreEntity> page = storeRepository.findAll(pageable);

        log.info("m=getAllStores, totalElements={}, pageNumber={}, pageSize={}",
                page.getTotalElements(), page.getNumber(), page.getSize());

        return page.map(StoreDTO::from);
    }

    @Override
    public StoreDTO getStoreById(Integer storeId) {
        StoreEntity entity = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException(storeId));

        log.info("m=getStoreById, storeId={}", storeId);

        return StoreDTO.from(entity);
    }
}
