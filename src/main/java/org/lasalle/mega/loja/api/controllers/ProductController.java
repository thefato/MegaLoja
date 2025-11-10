package org.lasalle.mega.loja.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.lasalle.mega.loja.application.products.services.ProductCreateService;
import org.lasalle.mega.loja.application.products.services.ProductRetrieveService;
import org.lasalle.mega.loja.domain.dto.ProductDTO;
import org.lasalle.mega.loja.domain.request.ProductCreateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductCreateService productCreateService;

    private final ProductRetrieveService productRetrieveService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('SCOPE_CREATE_PRODUCT')")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "OK"),
            @ApiResponse(responseCode = "401", description = "O preço precisa ser maior ou igual a zero"),
            @ApiResponse(responseCode = "401", description = "A quantidade precisa ser maior ou igual a zero"),
            @ApiResponse(responseCode = "401", description = "A categoria vinculada precisa existir"),
    })
    @Operation(summary = "Realiza o cadastro de um novo produto no sistema")
    public ResponseEntity<ProductDTO> save(@RequestBody ProductCreateRequest createRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productCreateService.createProduct(createRequest));
    }

    @PermitAll
    @PostMapping("/all")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @Operation(summary = "Busca todos os produtos cadastrados no sistema de forma paginada")
    public ResponseEntity<Page<ProductDTO>> findAllProducts(Pageable pageable) {
        return ResponseEntity.ok(productRetrieveService.getAllProducts(pageable));
    }

    @PostMapping("/categories")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @Operation(summary = "Busca todos os produtos em uma categoria especifica de forma paginada")
    public ResponseEntity<Page<ProductDTO>> findAllProducts(@RequestParam List<Integer> categories,
                                                            Pageable pageable) {
        return ResponseEntity.ok(productRetrieveService.getAllInCategory(categories, pageable));
    }


}
