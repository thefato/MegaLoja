package org.lasalle.mega.loja.application.products.services;

import org.lasalle.mega.loja.domain.dto.ProductDTO;
import org.lasalle.mega.loja.domain.request.ProductUpdateRequest;

public interface ProductUpdateService {

    ProductDTO updateProduct(ProductUpdateRequest createRequest);

}
