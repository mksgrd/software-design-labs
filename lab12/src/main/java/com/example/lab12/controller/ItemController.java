package com.example.lab12.controller;

import com.example.lab12.dto.ItemDto;
import com.example.lab12.model.Item;
import com.example.lab12.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public Mono<Item> newItem(@RequestParam String name,
                              @RequestParam String description,
                              @RequestParam double dollarPrice) {
        return itemService.addItem(name, description, dollarPrice);
    }

    @GetMapping
    public Flux<ItemDto> allItems(@RequestParam long userId) {
        return itemService.getAllItems(userId);
    }
}
