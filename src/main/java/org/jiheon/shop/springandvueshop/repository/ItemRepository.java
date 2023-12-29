package org.jiheon.shop.springandvueshop.repository;

import org.jiheon.shop.springandvueshop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
