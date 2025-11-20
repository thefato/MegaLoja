package org.lasalle.mega.loja.application.services.products.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lasalle.mega.loja.application.services.products.ProductRetrieveService;
import org.lasalle.mega.loja.application.repository.ProductRepository;
import org.lasalle.mega.loja.domain.dto.ProductDTO;
import org.lasalle.mega.loja.domain.entity.ProductEntity;
import org.lasalle.mega.loja.infrastructure.exceptions.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductRetrieveServiceImpl implements ProductRetrieveService {

    private final ProductRepository productRepository;

    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductDTO::from);
    }

    @Override
    public Page<ProductDTO> getAllByFilters(List<Integer> categories, String name, Pageable pageable) {
        boolean noCategories = Objects.requireNonNullElse(categories, List.of()).isEmpty();
        boolean noName = Objects.requireNonNullElse(name, "").isBlank();

        if (noCategories && noName) {
            return getAllProducts(pageable);
        }

        Page<ProductEntity> page = productRepository.findByFilters(
                noCategories ? null : categories,
                noName ? null : name,
                pageable
        );

        log.info("m=getAllByFilters, categories={}, name={}, totalElements={}",
                categories, name, page.getTotalElements());

        return page.map(ProductDTO::from);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Optional<ProductEntity> optional = productRepository.findById(id);

        if (optional.isEmpty()) {
            log.warn("m=getProductById, o produto não foi encontrado id={}", id);
            throw new ProductNotFoundException("O produto informado não foi encontrado " + id);
        }

        return ProductDTO.from(optional.get());
    }

}
