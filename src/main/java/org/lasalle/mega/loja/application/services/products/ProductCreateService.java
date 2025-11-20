package org.lasalle.mega.loja.application.services.products;

import org.lasalle.mega.loja.domain.dto.ProductDTO;
import org.lasalle.mega.loja.domain.request.ProductCreateRequest;

public interface ProductCreateService {

    ProductDTO createProduct(ProductCreateRequest createRequest);

}
