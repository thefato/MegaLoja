package org.lasalle.mega.loja.application.stores.services;

import org.lasalle.mega.loja.domain.dto.StoreDTO;
import org.lasalle.mega.loja.domain.request.StoreUpdateRequest;

public interface StoreUpdateService {

    StoreDTO updateStore(Integer storeId, StoreUpdateRequest updateRequest);

    void deleteStore(Integer storeId);

}
