package org.jiheon.shop.springandvueshop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class OrderDto {

    private String name;
    private String address;
    private String payment;
    private String cardNumber;
    private String items;
}
