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

        if ("max".equals(command)) {
            response.getWriter().println(new ProductsHtmlDTO(
                    "<h1>Product with max price: </h1>", List.of(repository.findByMaxPrice())
            ));
        } else if ("min".equals(command)) {
            response.getWriter().println(new ProductsHtmlDTO(
                    "<h1>Product with min price: </h1>", List.of(repository.findByMinPrice())
            ));

        } else if ("sum".equals(command)) {
            response.getWriter().println(new ProductsHtmlDTO(
                    "Summary price: \n" + repository.totalPrice()
            ));

        } else if ("count".equals(command)) {
            response.getWriter().println(new ProductsHtmlDTO(
                    "Number of products: \n" + repository.count()
            ));
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
