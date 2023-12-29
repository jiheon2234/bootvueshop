package org.jiheon.shop.springandvueshop.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtServiceImpl implements JwtService{

    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    private static final MacAlgorithm ALGORITHM = Jwts.SIG.HS256;

    @Override
    public String getToken(String key, Object value) {

        HashMap<String, String> headerMap = new HashMap<>();

        Date expTime = new Date();
        expTime.setTime(expTime.getTime()+ 1000*60*5);
        return  Jwts.builder()
                .header()
                .add("typ", "JWT")
                .add("alg", "HS256")
                .and()
                .claim(key, value)
                .expiration(expTime)
                .signWith(SECRET_KEY, ALGORITHM)
                .compact();
    }
}
