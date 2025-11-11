package org.lasalle.mega.loja.application.products.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lasalle.mega.loja.application.products.services.ProductUpdateService;
import org.lasalle.mega.loja.application.repository.ProductCategoryRepository;
import org.lasalle.mega.loja.application.repository.ProductRepository;
import org.lasalle.mega.loja.domain.dto.ProductDTO;
import org.lasalle.mega.loja.domain.entity.ProductCategoryEntity;
import org.lasalle.mega.loja.domain.entity.ProductEntity;
import org.lasalle.mega.loja.domain.request.ProductUpdateRequest;
import org.lasalle.mega.loja.infrastructure.exceptions.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductUpdateServiceImpl implements ProductUpdateService {

    private final ProductRepository productRepository;

    private final ProductCategoryRepository productCategoryRepository;

    @Override
    @Transactional
    public ProductDTO updateProduct(ProductUpdateRequest updateRequest) {
        Optional<ProductEntity> optional = productRepository.findById(updateRequest.getId());

        if (optional.isEmpty()) {
            log.warn("m=updateProduct, o produto não foi encontrado {}", updateRequest.getId());
            throw new ProductNotFoundException("O produto informado não existe, id " + updateRequest.getId());
        }


        if (Objects.nonNull(updateRequest.getName()) && updateRequest.getName().isBlank()) {
            log.warn("m=updateProduct, o nome do produto não pode ser vazio");
            throw new ProductInvalidNameException();
        }

        if (Objects.nonNull(updateRequest.getAmount()) && updateRequest.getAmount() < 0) {
            log.warn("m=updateProduct, a quantidade do produto recebida é menor que zero {}", updateRequest);
            throw new ProductInvalidAmountException();
        }

        if (Objects.nonNull(updateRequest.getPrice()) && updateRequest.getPrice().doubleValue() < 0) {
            log.warn("m=updateProduct, o valor do produto recebida é menor que zero {}", updateRequest);
            throw new ProductInvalidPriceException();
        }

        ProductEntity productEntity = optional.get();

        if (Objects.nonNull(updateRequest.getCategory())) {
            Optional<ProductCategoryEntity> productCategory = productCategoryRepository.findById(updateRequest.getCategory());

            if (productCategory.isEmpty()) {
                log.warn("m=updateProduct, a categoria recebida não existe request {}", updateRequest);
                throw new ProductCategoryNotFoundException();
            }

            productEntity.setCategory(productCategory.get());
        }

        Optional.ofNullable(productEntity.getName())
                .ifPresent(productEntity::setName);

        Optional.ofNullable(productEntity.getAmount())
                .ifPresent(productEntity::setAmount);

        Optional.ofNullable(productEntity.getCost())
                .ifPresent(productEntity::setCost);

        productRepository.save(productEntity);

        log.info("m=updateProduct, produto atualizado com sucesso {}", productEntity);

        return ProductDTO.from(productEntity);
    }

}
