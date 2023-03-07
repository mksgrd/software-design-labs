package com.example.tradeservice;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Trader {
    private final int id;
    private final String name;
    private int money;
    private Map<Integer, Integer> shares;

    public Trader(int id, String name, int money) {
        this.id = id;
        this.name = name;
        this.money = money;
        this.shares = new HashMap<>();
    }
}
