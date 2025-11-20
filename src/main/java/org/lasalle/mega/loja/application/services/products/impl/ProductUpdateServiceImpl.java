package org.lasalle.mega.loja.application.services.products.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lasalle.mega.loja.application.services.products.ProductUpdateService;
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
    public ProductDTO updateProduct(Long productId, ProductUpdateRequest updateRequest) {
        Optional<ProductEntity> optional = productRepository.findById(productId);

        if (optional.isEmpty()) {
            log.warn("m=updateProduct, o produto não foi encontrado {}", productId);
            throw new ProductNotFoundException("O produto informado não existe, id " + productId);
        }


        if (Objects.nonNull(updateRequest.name()) && updateRequest.name().isBlank()) {
            log.warn("m=updateProduct, o nome do produto não pode ser vazio");
            throw new ProductInvalidNameException();
        }

        if (Objects.nonNull(updateRequest.amount()) && updateRequest.amount() < 0) {
            log.warn("m=updateProduct, a quantidade do produto recebida é menor que zero {}", updateRequest);
            throw new ProductInvalidAmountException();
        }

        if (Objects.nonNull(updateRequest.price()) && updateRequest.price().doubleValue() < 0) {
            log.warn("m=updateProduct, o valor do produto recebida é menor que zero {}", updateRequest);
            throw new ProductInvalidPriceException();
        }

        ProductEntity productEntity = optional.get();

        if (Objects.nonNull(updateRequest.category())) {
            Optional<ProductCategoryEntity> productCategory = productCategoryRepository.findById(updateRequest.category());

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

    @Override
    public void deleteProduct(Long productId) {
        if (productRepository.findById(productId).isEmpty()) {
            log.warn("m=updateProduct, o produto não foi encontrado {}", productId);
            throw new ProductNotFoundException("O produto informado não existe, id " + productId);
        }

        productRepository.deleteById(productId);
    }

}
