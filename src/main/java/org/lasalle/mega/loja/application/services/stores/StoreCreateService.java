package org.lasalle.mega.loja.application.services.stores;

import org.lasalle.mega.loja.domain.dto.StoreDTO;
import org.lasalle.mega.loja.domain.request.StoreCreateRequest;

public interface StoreCreateService {

    StoreDTO createStore(StoreCreateRequest createRequest);

}
