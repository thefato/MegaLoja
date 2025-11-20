package org.lasalle.mega.loja.application.services.products.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lasalle.mega.loja.application.services.products.ProductCreateService;
import org.lasalle.mega.loja.application.repository.ProductCategoryRepository;
import org.lasalle.mega.loja.application.repository.ProductRepository;
import org.lasalle.mega.loja.domain.dto.ProductDTO;
import org.lasalle.mega.loja.domain.entity.ProductCategoryEntity;
import org.lasalle.mega.loja.domain.entity.ProductEntity;
import org.lasalle.mega.loja.domain.request.ProductCreateRequest;
import org.lasalle.mega.loja.infrastructure.exceptions.ProductCategoryNotFoundException;
import org.lasalle.mega.loja.infrastructure.exceptions.ProductInvalidAmountException;
import org.lasalle.mega.loja.infrastructure.exceptions.ProductInvalidPriceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCreateServiceImpl implements ProductCreateService {

    private final ProductRepository productRepository;

    private final ProductCategoryRepository productCategoryRepository;

    @Override
    @Transactional
    public ProductDTO createProduct(ProductCreateRequest createRequest) {
        Optional<ProductCategoryEntity> productCategory = productCategoryRepository.findById(createRequest.categoryId());

        if (productCategory.isEmpty()) {
            log.warn("m=createProduct, a categoria recebida não existe request {}", createRequest);
            throw new ProductCategoryNotFoundException();
        }

        if (createRequest.amount() < 0) {
            log.warn("m=createProduct, a quantidade do produto recebida é menor que zero {}", createRequest);
            throw new ProductInvalidAmountException();
        }

        if (Objects.isNull(createRequest.cost()) || createRequest.cost().doubleValue() < 0) {
            log.warn("m=createProduct, o valor do produto recebida é menor que zero {}", createRequest);
            throw new ProductInvalidPriceException();
        }

        ProductEntity productEntity = ProductEntity.builder()
                .category(productCategory.get())
                .name(createRequest.name())
                .cost(createRequest.cost())
                .amount(createRequest.amount())
                .build();

        productRepository.save(productEntity);

        log.info("m=createProduct, novo produto salvo {}", productEntity);

        return ProductDTO.from(productEntity);
    }
}
