package org.lasalle.mega.loja.application.services.products;

import org.lasalle.mega.loja.domain.dto.ProductDTO;
import org.lasalle.mega.loja.domain.request.ProductUpdateRequest;

public interface ProductUpdateService {

    ProductDTO updateProduct(Long productId, ProductUpdateRequest createRequest);

    void deleteProduct(Long productId);

}
