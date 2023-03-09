package com.example.lab12.service;

import com.example.lab12.model.User;
import com.example.lab12.repository.UserRepository;
import com.example.lab12.util.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public Mono<User> addUser(String name, Currency preferredCurrency) {
        return repository.save(new User(name, preferredCurrency));
    }

    public Mono<Currency> getPreferredCurrency(long userId) {
        return repository.findById(userId).map(User::getPreferredCurrency);
    }
}
