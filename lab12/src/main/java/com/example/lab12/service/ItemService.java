package com.example.lab12.service;

import com.example.lab12.dto.ItemDto;
import com.example.lab12.model.Item;
import com.example.lab12.repository.ItemRepository;
import com.example.lab12.util.Currency;
import com.example.lab12.util.CurrencyConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final UserService userService;
    private final ItemRepository itemRepository;

    public Mono<Item> addItem(String name, String description, double dollarPrice) {
        return itemRepository.save(new Item(name, description, dollarPrice));
    }

    public Flux<ItemDto> getAllItems(long userId) {
        Mono<Currency> preferredCurrency = userService.getPreferredCurrency(userId).cache();
        return itemRepository
                .findAll()
                .flatMap(item -> Mono.just(item).zipWith(preferredCurrency, (item1, currency) ->
                        new ItemDto(item1.getId(),
                                item1.getName(),
                                item1.getDescription(),
                                CurrencyConverter.convert(Currency.DOLLAR, currency, item1.getDollarPrice()))));
    }

}
