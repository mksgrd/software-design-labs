package ru.akirakozov.sd.refactoring.dto;

import ru.akirakozov.sd.refactoring.model.Product;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public record ProductsHtmlDTO(String message,
                              List<Product> products) {

    public ProductsHtmlDTO(List<Product> products) {
        this("", products);
    }

    public ProductsHtmlDTO(String message) {
        this(message, Collections.emptyList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>\n");

        if (!message.isEmpty()) {
            sb.append(message).append("\n");
        }

        if (!products.isEmpty()) {
            sb.append(
                    products.stream()
                            .map(product -> product.name() + "\t" + product.price() + "</br>")
                            .collect(Collectors.joining("\n", "", "\n"))
            );
        }

        sb.append("</body></html>");
        return sb.toString();
    }
}
