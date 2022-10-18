package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dto.ProductsHtmlDTO;
import ru.akirakozov.sd.refactoring.repository.ProductRepository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        response.getWriter().println(new ProductsHtmlDTO(repository.findAll()));
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
