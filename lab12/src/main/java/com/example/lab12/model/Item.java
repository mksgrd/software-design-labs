package com.example.lab12.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Item {
    @Id
    private Long id;

    private String name;

    private String description;

    private double dollarPrice;

    public Item(String name, String description, double dollarPrice) {
        this.name = name;
        this.description = description;
        this.dollarPrice = dollarPrice;
    }
}
