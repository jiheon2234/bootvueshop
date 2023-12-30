package org.jiheon.shop.springandvueshop.controller;

import lombok.RequiredArgsConstructor;
import org.jiheon.shop.springandvueshop.dto.OrderDto;
import org.jiheon.shop.springandvueshop.entity.Order;
import org.jiheon.shop.springandvueshop.repository.OrderRepository;
import org.jiheon.shop.springandvueshop.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/api/orders")
public class OrderController {

    private final JwtService jwtService;

    private final OrderRepository orderRepository;


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

        Order newOrder = Order.builder()
                .memberId(jwtService.getId(token))
                .name(dto.getName())
                .address(dto.getAddress())
                .payment(dto.getPayment())
                .cardNumber(dto.getCardNumber())
                .items(dto.getItems())
                .build();

        orderRepository.save(newOrder);
        return new ResponseEntity<>(OK);
    }


}
