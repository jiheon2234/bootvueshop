package org.jiheon.shop.springandvueshop.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jiheon.shop.springandvueshop.entity.Member;
import org.jiheon.shop.springandvueshop.repository.MemberRepository;
import org.jiheon.shop.springandvueshop.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    @PostMapping("/account/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> params, HttpServletResponse res){
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

        return  new ResponseEntity<>(member.getId(),HttpStatus.OK);
    }


}
