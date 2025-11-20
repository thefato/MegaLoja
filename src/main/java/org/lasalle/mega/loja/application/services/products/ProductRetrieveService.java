package org.lasalle.mega.loja.application.services.products;

import org.lasalle.mega.loja.domain.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRetrieveService {

    Page<ProductDTO> getAllProducts(Pageable pageable);

    Page<ProductDTO> getAllByFilters(List<Integer> categories, String name, Pageable pageable);

    ProductDTO getProductById(Long id);

}
