package org.lasalle.mega.loja.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.lasalle.mega.loja.application.services.cart.CartService;
import org.lasalle.mega.loja.domain.dto.CartDTO;
import org.lasalle.mega.loja.domain.request.CartAddItemRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/carts/v1")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PermitAll
    @PostMapping("/items")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item adicionado ao carrinho com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Carrinho pertence a outro usuário"),
            @ApiResponse(responseCode = "404", description = "Produto ou usuário não encontrado")
    })
    @Operation(summary = "Adiciona um item ao carrinho. Pode usar token do carrinho e/ou usuário autenticado.")
    public ResponseEntity<CartDTO> addItemToCart(
            @RequestParam(required = false) String cartToken,
            @RequestBody @Valid CartAddItemRequest request,
            Principal principal
    ) {
        return ResponseEntity.ok(cartService.addItemToCart(cartToken, request, principal));
    }

    @PermitAll
    @DeleteMapping("/items/{productId}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item removido do carrinho com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Carrinho pertence a outro usuário"),
            @ApiResponse(responseCode = "404", description = "Carrinho ou item não encontrado")
    })
    @Operation(summary = "Remove um item (produto) do carrinho")
    public ResponseEntity<CartDTO> removeItemFromCart(
            @RequestParam String cartToken,
            @RequestParam(required = false) Long amount,
            @PathVariable Long productId,
            Principal principal
    ) {
        return ResponseEntity.ok(cartService.removeItemFromCart(cartToken, productId, amount, principal));
    }

    @PermitAll
    @GetMapping
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Carrinho retornado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Carrinho pertence a outro usuário"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @Operation(summary = "Busca um carrinho específico pelo token. Se não existir, cria um novo. " +
            "Se o carrinho não tiver usuário e houver usuário autenticado, vincula o usuário ao carrinho.")
    public ResponseEntity<CartDTO> getCart(
            @RequestParam(required = false) String cartToken,
            Principal principal
    ) {
        return ResponseEntity.ok(cartService.getOrCreateCart(cartToken, principal));
    }
}
