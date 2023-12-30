package org.jiheon.shop.springandvueshop.service;

import io.jsonwebtoken.Claims;

import java.util.Optional;

public interface JwtService {
    public String getToken(String key, Object value);

    Optional<Claims> getClaims(String token);


}
