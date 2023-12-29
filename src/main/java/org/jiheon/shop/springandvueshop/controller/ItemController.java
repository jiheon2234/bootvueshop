package org.jiheon.shop.springandvueshop.controller;

import lombok.RequiredArgsConstructor;
import org.jiheon.shop.springandvueshop.entity.Item;
import org.jiheon.shop.springandvueshop.repository.ItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    @GetMapping("/api/items")
    public List<Item> getItems(){
        return itemRepository.findAll();
    }

}
