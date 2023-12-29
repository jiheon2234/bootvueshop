package org.jiheon.shop.springandvueshop.controller;

import lombok.RequiredArgsConstructor;
import org.jiheon.shop.springandvueshop.entity.Member;
import org.jiheon.shop.springandvueshop.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {

    private final MemberRepository memberRepository;

    @PostMapping("/account/login")
    public int login(@RequestBody Map<String,String> params){
        return memberRepository.findByEmailAndPassword(params.get("email"),params.get("password"))
                .map(Member::getId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


}
