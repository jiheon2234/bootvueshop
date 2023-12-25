package org.jiheon.shop.springandvueshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ItemController {
    @GetMapping("/api/items")
    public List<String> getItems(){
        ArrayList<String> items = new ArrayList<>();
        items.add("test1");
        items.add("test2");
        items.add("test3");

        return items;
    }

}
