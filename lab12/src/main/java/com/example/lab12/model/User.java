package com.example.lab12.model;

import com.example.lab12.util.Currency;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("users")
public class User {
    @Id
    private Long id;

    private String name;

    private Currency preferredCurrency;

    public User(String name, Currency preferredCurrency) {
        this.name = name;
        this.preferredCurrency = preferredCurrency;
    }
}
