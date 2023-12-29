package org.jiheon.shop.springandvueshop.controller;

import lombok.RequiredArgsConstructor;
import org.jiheon.shop.springandvueshop.entity.Member;
import org.jiheon.shop.springandvueshop.repository.MemberRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {

    private final MemberRepository memberRepository;

    @PostMapping("/account/login")
    public int login(@RequestBody String email,  @RequestBody String password){
//        System.out.println("params = " + params);
        return memberRepository.findByEmailAndPassword(email,password)
                .map(Member::getId)
                .orElse(0);
    }


}
