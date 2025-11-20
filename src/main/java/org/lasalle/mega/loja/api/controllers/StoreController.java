package org.lasalle.mega.loja.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.lasalle.mega.loja.application.stores.services.StoreCreateService;
import org.lasalle.mega.loja.application.stores.services.StoreRetrieveService;
import org.lasalle.mega.loja.application.stores.services.StoreUpdateService;
import org.lasalle.mega.loja.domain.dto.StoreDTO;
import org.lasalle.mega.loja.domain.request.StoreCreateRequest;
import org.lasalle.mega.loja.domain.request.StoreUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreCreateService storeCreateService;

    private final StoreUpdateService storeUpdateService;

    private final StoreRetrieveService storeRetrieveService;

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_CREATE_STORE')")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Loja criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação de loja")
    })
    @Operation(summary = "Realiza o cadastro de uma nova loja no sistema")
    public ResponseEntity<StoreDTO> save(@RequestBody StoreCreateRequest createRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(storeCreateService.createStore(createRequest));
    }

    @PatchMapping("/{storeId}")
    @PreAuthorize("hasAuthority('SCOPE_CREATE_STORE')")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Loja atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização de loja"),
            @ApiResponse(responseCode = "404", description = "Loja não encontrada")
    })
    @Operation(summary = "Realiza a edição de uma loja existente")
    public ResponseEntity<StoreDTO> update(
            @PathVariable @NotNull Integer storeId,
            @RequestBody StoreUpdateRequest updateRequest
    ) {
        return ResponseEntity.ok(storeUpdateService.updateStore(storeId, updateRequest));
    }

    @DeleteMapping("/{storeId}")
    @PreAuthorize("hasAuthority('SCOPE_CREATE_STORE')")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Loja removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Loja não encontrada")
    })
    @Operation(summary = "Remove uma loja do sistema")
    public ResponseEntity<Void> delete(@PathVariable Integer storeId) {
        storeUpdateService.deleteStore(storeId);
        return ResponseEntity.ok().build();
    }

    @PermitAll
    @GetMapping("/all")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @Operation(summary = "Busca todas as lojas cadastradas no sistema de forma paginada")
    public ResponseEntity<Page<StoreDTO>> findAllStores(Pageable pageable) {
        return ResponseEntity.ok(storeRetrieveService.getAllStores(pageable));
    }

    @PermitAll
    @GetMapping("/{storeId}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Loja não encontrada")
    })
    @Operation(summary = "Busca uma loja específica por ID")
    public ResponseEntity<StoreDTO> findStoreById(@PathVariable Integer storeId) {
        return ResponseEntity.ok(storeRetrieveService.getStoreById(storeId));
    }
}
