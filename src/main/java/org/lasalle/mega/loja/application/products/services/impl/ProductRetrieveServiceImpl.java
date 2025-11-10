package org.lasalle.mega.loja.application.products.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lasalle.mega.loja.application.products.services.ProductRetrieveService;
import org.lasalle.mega.loja.application.repository.ProductCategoryRepository;
import org.lasalle.mega.loja.application.repository.ProductRepository;
import org.lasalle.mega.loja.domain.dto.ProductDTO;
import org.lasalle.mega.loja.domain.entity.ProductCategoryEntity;
import org.lasalle.mega.loja.domain.entity.ProductEntity;
import org.lasalle.mega.loja.infrastructure.exceptions.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductRetrieveServiceImpl implements ProductRetrieveService {

    private final ProductRepository productRepository;

    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductDTO::from);
    }

    @Override
    public Page<ProductDTO> getAllInCategory(List<Integer> categoriesIds, Pageable pageable) {
        List<ProductCategoryEntity> categories = productCategoryRepository.findAllById(categoriesIds);

        return productRepository.findByCategoryIn(categories, pageable)
                .map(ProductDTO::from);
    }

    @Override
    public ProductDTO getProductById(Integer id) {
        Optional<ProductEntity> optional = productRepository.findById(id);

        if (optional.isEmpty()) {
            log.warn("m=getProductById, o produto não foi encontrado id={}", id);
            throw new ProductNotFoundException("O produto informado não foi encontrado " + id);
        }

        return ProductDTO.from(optional.get());
    }

}
