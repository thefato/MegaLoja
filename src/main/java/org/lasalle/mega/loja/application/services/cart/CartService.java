package org.lasalle.mega.loja.application.services.cart;

import org.lasalle.mega.loja.domain.dto.CartDTO;
import org.lasalle.mega.loja.domain.request.CartAddItemRequest;

import java.security.Principal;

public interface CartService {

    CartDTO addItemToCart(String cartToken, CartAddItemRequest request, Principal principal);

    CartDTO removeItemFromCart(String cartToken, Long productId, Long amount, Principal principal);

    CartDTO getOrCreateCart(String cartToken, Principal principal);

}
