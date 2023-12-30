package org.jiheon.shop.springandvueshop.repository;

import org.jiheon.shop.springandvueshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {


}
