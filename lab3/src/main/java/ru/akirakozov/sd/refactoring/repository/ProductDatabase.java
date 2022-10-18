package ru.akirakozov.sd.refactoring.repository;

import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDatabase implements ProductRepository {

    private final String url;

    public ProductDatabase(String url) {
        this.url = url;

        try (Connection c = DriverManager.getConnection(this.url)) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Product product) {
        try (Connection c = DriverManager.getConnection(url)) {
            String sql = "INSERT INTO PRODUCT " +
                    "(NAME, PRICE) VALUES (\"" + product.name() + "\"," + product.price() + ")";
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findAll() {
        try (Connection c = DriverManager.getConnection(url)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");

            List<Product> products = new ArrayList<>();

            while (rs.next()) {
                products.add(new Product(rs.getString("name"), rs.getInt("price")));
            }

            rs.close();
            stmt.close();

            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product findByMaxPrice() {
        try (Connection c = DriverManager.getConnection(url)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");

            List<Product> products = new ArrayList<>();

            while (rs.next()) {
                products.add(new Product(rs.getString("name"), rs.getInt("price")));
            }

            rs.close();
            stmt.close();

            return products.get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product findByMinPrice() {
        try (Connection c = DriverManager.getConnection(url)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");

            List<Product> products = new ArrayList<>();

            while (rs.next()) {
                products.add(new Product(rs.getString("name"), rs.getInt("price")));
            }

            rs.close();
            stmt.close();

            return products.get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int totalPrice() {
        try (Connection c = DriverManager.getConnection(url)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT SUM(price) FROM PRODUCT");

            int res = rs.getInt(1);

            rs.close();
            stmt.close();

            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int count() {
        try (Connection c = DriverManager.getConnection(url)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRODUCT");

            int res = rs.getInt(1);

            rs.close();
            stmt.close();

            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
