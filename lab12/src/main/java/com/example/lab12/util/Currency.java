package com.example.lab12.util;

public enum Currency {
    DOLLAR(1), RUBLE(75.91), EURO(0.95);

    public final double dollarRate;

    Currency(double dollarRate) {
        this.dollarRate = dollarRate;
    }
}
