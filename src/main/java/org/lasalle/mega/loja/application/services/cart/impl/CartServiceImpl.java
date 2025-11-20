package org.lasalle.mega.loja.application.services.cart.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lasalle.mega.loja.application.repository.CartItemRepository;
import org.lasalle.mega.loja.application.repository.CartRepository;
import org.lasalle.mega.loja.application.repository.ProductRepository;
import org.lasalle.mega.loja.application.repository.UserDetailsRepository;
import org.lasalle.mega.loja.application.services.cart.CartService;
import org.lasalle.mega.loja.application.services.products.ProductRetrieveService;
import org.lasalle.mega.loja.domain.dto.CartDTO;
import org.lasalle.mega.loja.domain.dto.ProductDTO;
import org.lasalle.mega.loja.domain.entity.CartEntity;
import org.lasalle.mega.loja.domain.entity.CartItemEntity;
import org.lasalle.mega.loja.domain.entity.UserDetailsEntity;
import org.lasalle.mega.loja.domain.request.CartAddItemRequest;
import org.lasalle.mega.loja.infrastructure.exceptions.CartItemNotFoundException;
import org.lasalle.mega.loja.infrastructure.exceptions.CartNotFoundException;
import org.lasalle.mega.loja.infrastructure.exceptions.CartUserMismatchException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserDetailsRepository userDetailsRepository;

    private final ProductRetrieveService productRetrieveService;

    @Override
    @Transactional
    public CartDTO addItemToCart(String cartToken, CartAddItemRequest request, Principal principal) {
        Optional<UserDetailsEntity> user = getUserDetailsEntity(principal);
        CartEntity cart = getCartEntity(cartToken);

        updateUserCart(user, cart);
        updateCartItem(request, cart);

        return CartDTO.from(cartRepository.findById(cart.getId()).orElse(cart));
    }


    @Override
    @Transactional
    public CartDTO removeItemFromCart(String cartToken, Long productId, Long amount, Principal principal) {
        if (Objects.requireNonNullElse(cartToken, "").isBlank()) {
            log.warn("m=removeItemFromCart, cartToken ausente");
            throw new CartNotFoundException("null");
        }

        Optional<UserDetailsEntity> user = getUserDetailsEntity(principal);
        CartEntity cart = cartRepository.findByCartKey(cartToken).orElseThrow(() -> new CartNotFoundException(cartToken));

        updateUserCart(user, cart);

        Optional<CartItemEntity> optionalItem = cartItemRepository.findByCart_IdAndProduct_Id(cart.getId(), productId);
        CartItemEntity item = optionalItem.orElseThrow(() -> new CartItemNotFoundException(productId));

        if (Objects.nonNull(amount) && amount < item.getQuantity()) {
            item.setQuantity(item.getQuantity() - amount);
            cartItemRepository.save(item);

            log.info("m=removeItemFromCart, quantidade de itens diminuidas do carrinho, cartKey={}, productId={}, newAmount={}",
                    cart.getCartKey(), productId, item.getQuantity());
        } else {
            if (Objects.nonNull(cart.getItems())) {
                cart.getItems().removeIf(other -> Objects.equals(other.getId(), item.getId()));
            }

            cartItemRepository.delete(item);
            log.info("m=removeItemFromCart, item removido, cartKey={}, productId={}", cart.getCartKey(), productId);
        }

        return CartDTO.from(cartRepository.findById(cart.getId()).orElse(cart));
    }

    @Override
    @Transactional
    public CartDTO getOrCreateCart(String cartToken, Principal principal) {
        Optional<UserDetailsEntity> user = getUserDetailsEntity(principal);
        CartEntity cart = getCartEntity(cartToken);

        updateUserCart(user, cart);

        return CartDTO.from(cart);
    }

    private Optional<UserDetailsEntity> getUserDetailsEntity(Principal principal) {
        if (Objects.isNull(principal))
            return Optional.empty();

        String email = principal.getName();

        return Optional.of(userDetailsRepository.findByUserCredentials_Email(email)
                .orElseThrow(() -> new UsernameNotFoundException(email)));
    }

    private CartEntity getCartEntity(String cartToken) {
        boolean hasCartToken = Objects.nonNull(cartToken) && !cartToken.isBlank();

        if (hasCartToken) {
            CartEntity cartEntity = cartRepository.findByCartKey(cartToken).orElse(null);

            if (Objects.nonNull(cartEntity)) {
                return cartEntity;
            }
        }

        CartEntity cart = CartEntity.builder()
                .cartKey(hasCartToken ? cartToken : UUID.randomUUID().toString())
                .build();

        log.info("m=getCartEntity, novo carrinho criado, cartKey={}", cart.getCartKey());

        return cartRepository.save(cart);
    }

    private void updateUserCart(Optional<UserDetailsEntity> optional, CartEntity cart) {
        if (optional.isEmpty() && Objects.nonNull(cart.getUser())) {
            log.warn("m=addItemToCart, tentativa de usuario anonimo usar carrinho de outro usuário, cartKey={}, owner={}",
                    cart.getCartKey(), cart.getUser().getUserCredentials().getEmail());

            throw new CartUserMismatchException();
        }

        if (optional.isPresent() && Objects.nonNull(cart.getUser())) {
            if (!Objects.equals(optional.get().getUserCredentials().getEmail(), cart.getUser().getUserCredentials().getEmail())) {
                log.warn("m=addItemToCart, tentativa de usar carrinho de outro usuário, cartKey={}, owner={}, current={}",
                        cart.getCartKey(), cart.getUser().getUserCredentials().getEmail(), optional.get().getUserCredentials().getEmail());

                throw new CartUserMismatchException();
            }

            return;
        }

        optional.ifPresent(user -> {
            cart.setUser(user);
            cartRepository.save(cart);

            log.info("m=addItemToCart, usuário associado ao carrinho, cartKey={}, user={}",
                    cart.getCartKey(), user.getUserCredentials().getEmail());
        });
    }

    private void updateCartItem(CartAddItemRequest request, CartEntity cart) {
        ProductDTO product = productRetrieveService.getProductById(request.productId());
        Optional<CartItemEntity> optionalItem = cartItemRepository.findByCart_IdAndProduct_Id(cart.getId(), product.getId());
        CartItemEntity item;

        if (optionalItem.isPresent()) {
            item = optionalItem.get();
            item.setQuantity(Math.min(item.getQuantity() + request.quantity(), product.getAmount()));

            log.info("m=updateCartItem, quantidade atualizada, cartKey={}, productId={}, quantity={}",
                    cart.getCartKey(), product.getId(), item.getQuantity());
        } else {
            item = CartItemEntity.builder()
                    .cart(cart)
                    .product(productRepository.findById(product.getId()).get())
                    .quantity(request.quantity())
                    .build();

            log.info("m=updateCartItem, novo item no carrinho, cartKey={}, productId={}, quantity={}",
                    cart.getCartKey(), product.getId(), request.quantity());
        }

        cartItemRepository.save(item);
    }
}
