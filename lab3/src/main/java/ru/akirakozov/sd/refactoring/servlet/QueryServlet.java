package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dto.ProductsHtmlDTO;
import ru.akirakozov.sd.refactoring.repository.ProductRepository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {

    private final ProductRepository repository;

    public QueryServlet(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        ProductsHtmlDTO dto = switch (command) {
            case "max" ->
                    new ProductsHtmlDTO("<h1>Product with max price: </h1>", List.of(repository.findByMaxPrice()));
            case "min" ->
                    new ProductsHtmlDTO("<h1>Product with min price: </h1>", List.of(repository.findByMinPrice()));
            case "sum" -> new ProductsHtmlDTO("Summary price: \n" + repository.totalPrice());
            case "count" -> new ProductsHtmlDTO("Number of products: \n" + repository.count());
            default -> new ProductsHtmlDTO("Unknown command: " + command);
        };

        response.getWriter().println(dto);

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
