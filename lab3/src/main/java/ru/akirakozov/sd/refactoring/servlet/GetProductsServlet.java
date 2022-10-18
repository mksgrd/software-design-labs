package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.model.Product;
import ru.akirakozov.sd.refactoring.repository.ProductRepository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    private final ProductRepository repository;

    public GetProductsServlet(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<Product> products = repository.findAll();

        response.getWriter().println(
                products.stream()
                        .map(p -> p.name() + "\t" + p.price() + "</br>")
                        .collect(Collectors.joining("\n", "<html><body>\n", "\n</body></html>"))
        );

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
