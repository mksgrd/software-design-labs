package com.example.lab12.controller;

import com.example.lab12.model.User;
import com.example.lab12.service.UserService;
import com.example.lab12.util.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public Mono<User> newUser(@RequestParam String name, @RequestParam Currency preferredCurrency) {
        return userService.addUser(name, preferredCurrency);
    }
}
