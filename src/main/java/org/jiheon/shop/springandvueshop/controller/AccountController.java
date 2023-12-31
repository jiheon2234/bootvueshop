package org.jiheon.shop.springandvueshop.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jiheon.shop.springandvueshop.entity.Member;
import org.jiheon.shop.springandvueshop.repository.MemberRepository;
import org.jiheon.shop.springandvueshop.service.JwtService;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<Integer> login(@RequestBody Map<String,String> params, HttpServletResponse res){
//        String token = memberRepository.findByEmailAndPassword(params.get("email"), params.get("password"))
//                .map((member) -> jwtService.getToken("id", member.getId()))
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Member member = memberRepository.findByEmailAndPassword(params.get("email"),params.get("password"))
                .orElseThrow(RuntimeException::new);
        String token = jwtService.getToken("id", member.getId());


        Cookie cookie = new Cookie("token",token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        res.addCookie(cookie);

        return  new ResponseEntity<>(member.getId(), OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> login(HttpServletResponse res){
        ResponseCookie token = ResponseCookie.from("token", "")
                .path("/")
                .maxAge(0)
                .build();


        return ResponseEntity.ok().header("Set-cookie",token.toString()).build();
    }


    @GetMapping("/check")
    public ResponseEntity<Integer> check(@CookieValue(value = "token", required = false) String token) {
try {
    String stringId = jwtService.getClaims(token)
            .map(claims -> claims.get("id").toString())
            .orElse("0");
    return new ResponseEntity<>(Integer.parseInt(stringId), OK);
}catch (Exception e){
    System.out.println("e = " + e);
    return new ResponseEntity<>(null, OK);
}
    }
}
