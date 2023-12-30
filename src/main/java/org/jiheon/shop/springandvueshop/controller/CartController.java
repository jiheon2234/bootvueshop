package org.jiheon.shop.springandvueshop.controller;

import lombok.RequiredArgsConstructor;
import org.jiheon.shop.springandvueshop.entity.Cart;
import org.jiheon.shop.springandvueshop.entity.Item;
import org.jiheon.shop.springandvueshop.repository.CartRepository;
import org.jiheon.shop.springandvueshop.repository.ItemRepository;
import org.jiheon.shop.springandvueshop.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final JwtService jwtService;

    private final CartRepository cartRepository;

    private final ItemRepository itemRepository;

    @GetMapping("/items")
    public ResponseEntity<List<Item>> getCartItems(@CookieValue(value = "token",required = false) String token ){
        if(!jwtService.isValid(token)){
            throw new ResponseStatusException(UNAUTHORIZED);
        }
        int memberId = jwtService.getId(token);
        List<Cart> carts = cartRepository.findByMemberId(memberId);
        List<Integer> itemIds = carts.stream().map(Cart::getItemId).toList();

        List<Item> items = itemRepository.findByIdIn(itemIds);

        return new ResponseEntity<>(items, OK);

    }

    @PostMapping("/items/{itemId}")
    public ResponseEntity<Void> pushCartItem(
            @PathVariable int itemId, @CookieValue(value = "token", required = false) String token
    ){
        if(!jwtService.isValid(token)){
            throw new ResponseStatusException(UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
         cartRepository.findByMemberIdAndItemId(memberId, itemId)
                 .ifPresentOrElse((cart)->{},() ->{
                     Cart newcart = new Cart();
                     newcart.setMemberId(memberId);
                     newcart.setItemId(itemId);
                     cartRepository.save(newcart);
                 });
         return new ResponseEntity<>(OK);
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeCartItem(
            @PathVariable int itemId, @CookieValue(value = "token", required = false) String token)
    {
        if(!jwtService.isValid(token)){
            throw new ResponseStatusException(UNAUTHORIZED);
        }
        int memberId = jwtService.getId(token);

        Cart cart = cartRepository.findByMemberIdAndItemId(memberId,itemId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        cartRepository.delete(cart);
        return new ResponseEntity<>(OK);
    }

}
