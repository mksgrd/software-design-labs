package com.example.tradeservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TradeController {
    private final Map<Integer, Company> companies = new HashMap<>();

    private final Map<Integer, Trader> traders = new HashMap<>();

    @PostMapping("/add-company")
    public void addCompany(@RequestParam int id,
                           @RequestParam String name,
                           @RequestParam int shares,
                           @RequestParam int price) {
        if (companies.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company already exists");
        }

        companies.put(id, new Company(id, name, shares, price));
    }

    @PostMapping("/add-trader")
    public void addTrader(@RequestParam int id,
                          @RequestParam String name,
                          @RequestParam int money) {
        if (traders.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trader already exists");
        }

        traders.put(id, new Trader(id, name, money));
    }

    @PostMapping("/add-trader-money")
    public void addTraderMoney(@RequestParam int traderId, @RequestParam int amount) {
        if (!traders.containsKey(traderId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trader not exists");
        }
        Trader trader = traders.get(traderId);

        trader.setMoney(trader.getMoney() + amount);
    }

    @PostMapping("/buy-shares")
    public void buyShares(@RequestParam int traderId,
                          @RequestParam int compId,
                          @RequestParam int amount) {
        if (!companies.containsKey(compId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company not exists");
        }

        if (!traders.containsKey(traderId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trader not exists");
        }

        Trader trader = traders.get(traderId);
        Company company = companies.get(compId);

        if (company.getShares() < amount) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough shares");
        }

        if (trader.getMoney() < amount * company.getPrice()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough money");
        }

        company.setShares(company.getShares() - amount);
        trader.setMoney(trader.getMoney() - amount * company.getPrice());

        trader.getShares().put(company.getId(), trader.getShares().getOrDefault(company.getId(), 0) + amount);
    }

    @PostMapping("/sell-shares")
    public void sellShares(@RequestParam int traderId,
                           @RequestParam int compId,
                           @RequestParam int amount) {
        if (!companies.containsKey(compId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company not exists");
        }

        if (!traders.containsKey(traderId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trader not exists");
        }

        Trader trader = traders.get(traderId);
        Company company = companies.get(compId);

        if (!trader.getShares().containsKey(company.getId()) || trader.getShares().get(company.getId()) < amount) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough shares");
        }


        company.setShares(company.getShares() + amount);
        trader.setMoney(trader.getMoney() + amount * company.getPrice());

        trader.getShares().put(company.getId(), trader.getShares().getOrDefault(company.getId(), 0) - amount);

        if (trader.getShares().get(company.getId()) <= 0) {
            trader.getShares().remove(company.getId());
        }
    }

    @GetMapping("/get-price")
    public int getPrice(@RequestParam int compId) {
        if (!companies.containsKey(compId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company not exists");
        }
        return companies.get(compId).getPrice();
    }

    @GetMapping("/get-amount")
    public int getAmount(@RequestParam int compId) {
        if (!companies.containsKey(compId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company not exists");
        }
        return companies.get(compId).getShares();
    }

    @PostMapping("/change-price")
    public void changePrice(@RequestParam int compId, @RequestParam int price) {
        if (!companies.containsKey(compId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company not exists");
        }

        companies.get(compId).setPrice(price);
    }

    @GetMapping("/list-shares")
    public Map<Integer, Integer> listShares(@RequestParam int traderId) {
        if (!traders.containsKey(traderId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trader not exists");
        }

        Trader trader = traders.get(traderId);
        return trader.getShares();
    }
}
