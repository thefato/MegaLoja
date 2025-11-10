package org.lasalle.mega.loja.application.products.services;

import org.lasalle.mega.loja.domain.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRetrieveService {

    Page<ProductDTO> getAllProducts(Pageable pageable);

    Page<ProductDTO> getAllInCategory(List<Integer> categories, Pageable pageable);

    ProductDTO getProductById(Integer id);

}
