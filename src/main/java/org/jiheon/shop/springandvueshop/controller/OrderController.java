package org.jiheon.shop.springandvueshop.controller;

import lombok.RequiredArgsConstructor;
import org.jiheon.shop.springandvueshop.dto.OrderDto;
import org.jiheon.shop.springandvueshop.entity.Order;
import org.jiheon.shop.springandvueshop.repository.CartRepository;
import org.jiheon.shop.springandvueshop.repository.OrderRepository;
import org.jiheon.shop.springandvueshop.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/api/orders")
@Transactional
public class OrderController {

    private final JwtService jwtService;

    private final OrderRepository orderRepository;

    private final CartRepository cartRepository;


    @GetMapping("/api/orders")
    public ResponseEntity<List<Order>> getOrder(
            @CookieValue(value = "token", required = false) String token
    ) {
        if(!jwtService.isValid(token)){
            throw new ResponseStatusException(UNAUTHORIZED);
        }

        List<Order> orders = orderRepository.findAll();

        return new ResponseEntity<>(orders, OK);
    }

    @PostMapping("/api/orders")
    public ResponseEntity<Void> pushOrder(
            @RequestBody OrderDto dto,
           @CookieValue(value = "token", required = false) String token
    ){
        if(!jwtService.isValid(token)){
            throw new ResponseStatusException(UNAUTHORIZED);
        }

        int id = jwtService.getId(token);

        Order newOrder = Order.builder()
                .memberId(id)
                .name(dto.getName())
                .address(dto.getAddress())
                .payment(dto.getPayment())
                .cardNumber(dto.getCardNumber())
                .items(dto.getItems())
                .build();

        orderRepository.save(newOrder);
        cartRepository.deleteByMemberId(id); //장바구니 비우기
        return new ResponseEntity<>(OK);
    }


}
