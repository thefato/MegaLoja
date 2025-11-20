package org.lasalle.mega.loja.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.lasalle.mega.loja.application.products.services.ProductCreateService;
import org.lasalle.mega.loja.application.products.services.ProductRetrieveService;
import org.lasalle.mega.loja.application.products.services.ProductUpdateService;
import org.lasalle.mega.loja.domain.dto.ProductDTO;
import org.lasalle.mega.loja.domain.request.ProductCreateRequest;
import org.lasalle.mega.loja.domain.request.ProductUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductCreateService productCreateService;

    private final ProductRetrieveService productRetrieveService;

    private final ProductUpdateService productUpdateService;

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_CREATE_PRODUCT')")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "O preço precisa ser maior ou igual a zero"),
            @ApiResponse(responseCode = "400", description = "A quantidade precisa ser maior ou igual a zero"),
            @ApiResponse(responseCode = "400", description = "A categoria vinculada precisa existir"),
    })
    @Operation(summary = "Realiza o cadastro de um novo produto no sistema")
    public ResponseEntity<ProductDTO> create(@RequestBody @Validated ProductCreateRequest createRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productCreateService.createProduct(createRequest));
    }

    @PatchMapping("/{productId}")
    @PreAuthorize("hasAuthority('SCOPE_CREATE_PRODUCT')")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "O nome informado não pode ser vazio"),
            @ApiResponse(responseCode = "400", description = "O preço precisa ser maior ou igual a zero"),
            @ApiResponse(responseCode = "400", description = "A quantidade precisa ser maior ou igual a zero"),
            @ApiResponse(responseCode = "400", description = "A categoria informada precisa existir"),
            @ApiResponse(responseCode = "404", description = "O produto não existe")
    })
    @Operation(summary = "Realiza a atualização de um produto no sistema")
    public ResponseEntity<ProductDTO> update(@PathVariable @NotNull Integer productId,
                                             @RequestBody @Validated ProductUpdateRequest updateRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productUpdateService.updateProduct(productId, updateRequest));
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAuthority('SCOPE_CREATE_PRODUCT')")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "O produto não existe"),
    })
    @Operation(summary = "Remove um produto do sistema")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Integer productId) {
        productUpdateService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @PermitAll
    @GetMapping("/{productId}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "O produto não existe"),
    })
    @Operation(summary = "Busca um produto por ID")
    public ResponseEntity<ProductDTO> findAllProducts(@PathVariable @NotNull Integer productId) {
        return ResponseEntity.ok(productRetrieveService.getProductById(productId));
    }

    @PermitAll
    @GetMapping("/all")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @Operation(summary = "Busca todos os produtos cadastrados no sistema de forma paginada")
    public ResponseEntity<Page<ProductDTO>> findAllProducts(Pageable pageable) {
        return ResponseEntity.ok(productRetrieveService.getAllProducts(pageable));
    }

    @PermitAll
    @GetMapping("/search")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @Operation(summary = "Busca produtos filtrando por categoria e/ou nome (LIKE)")
    public ResponseEntity<Page<ProductDTO>> findProductsByFilters(
            @RequestParam(required = false) List<Integer> categories,
            @RequestParam(required = false) String name,
            Pageable pageable) {

        return ResponseEntity.ok(productRetrieveService.getAllByFilters(categories, name, pageable));
    }

}
