package org.jiheon.shop.springandvueshop.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
@Slf4j

public class JwtServiceImpl implements JwtService{

//    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    private  final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode("rxupzgthyyhkwxyoiljgqxgqxxdnioyyrxupzgthyyhkwxyoiljgqxgqxxdnioyy"));


    @Override
    public String getToken(String key, Object value) {

        HashMap<String, String> headerMap = new HashMap<>();

        Date expTime = new Date();
        expTime.setTime(expTime.getTime()+ 1000*60*200);
        return  Jwts.builder()
                .header()
                .add("typ", "JWT")
                .add("alg", "HS256")
                .and()
                .claim(key, value)
                .expiration(expTime)
                .signWith(SECRET_KEY)
                .compact();
    }

    @Override
    public Optional<Claims> getClaims(String token) {
        if(token != null  && !token.isEmpty()){
            try{
                Claims payload = Jwts.parser().verifyWith(SECRET_KEY)
                        .build().parseSignedClaims(token)
                        .getPayload();
                return Optional.of(payload);
            }catch (ExpiredJwtException e){
                log.info("만료됨");
            }catch (JwtException e){
                log.info("유효X");
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean isValid(String token) {
        return this.getClaims(token).isPresent();
    }

    @Override
    public int getId(String token) {
        Object id = getClaims(token)
                .map(claims -> claims.get("id"))
                .orElse("0");
        return Integer.parseInt(id.toString());
    }
}
