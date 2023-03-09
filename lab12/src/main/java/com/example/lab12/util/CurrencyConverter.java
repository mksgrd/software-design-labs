package com.example.lab12.util;

public class CurrencyConverter {
    public static double convert(Currency from, Currency to, double amount) {
        return amount / from.dollarRate * to.dollarRate;
    }
}
