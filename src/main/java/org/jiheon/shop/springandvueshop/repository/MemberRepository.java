package org.jiheon.shop.springandvueshop.repository;

import org.jiheon.shop.springandvueshop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByEmailAndPassword(String email, String password);
}
