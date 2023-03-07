package com.example.tradeservice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Company {
    private final int id;
    private final String name;
    private int shares;

    private int price;
}
