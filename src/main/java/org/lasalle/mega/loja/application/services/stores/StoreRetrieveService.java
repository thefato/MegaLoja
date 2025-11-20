package org.lasalle.mega.loja.application.services.stores;

import org.lasalle.mega.loja.domain.dto.StoreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreRetrieveService {

    Page<StoreDTO> getAllStores(Pageable pageable);

    StoreDTO getStoreById(Integer storeId);

}
