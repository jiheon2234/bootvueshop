package org.jiheon.shop.springandvueshop.repository;

import org.jiheon.shop.springandvueshop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByMemberId(int memberId);

    Optional<Cart> findByMemberIdAndItemId(int memberId, int itemId);
}
