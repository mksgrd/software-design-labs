package ru.akirakozov.sd.refactoring.repository;

import ru.akirakozov.sd.refactoring.model.Product;

import java.util.List;

public interface ProductRepository {
    void save(Product product);

    List<Product> findAll();

    Product findByMaxPrice();

    Product findByMinPrice();

    int totalPrice();

    int count();
}
